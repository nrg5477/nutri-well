(function ($) {
    "use strict";

    const foodId = $("#foodContainer").data("food");
    const baseUrl = '/api/food/detail';
    const urlWithParams = `${baseUrl}?foodId=${encodeURIComponent(foodId)}`;
    var servingSize = 0;//기준데이터
    var foodWeight = 0;//1회 총제공량
    var baselMetabolism = 2000;
    var userWeight = 60;
    var nutritionChart;
    var userId;
    var nutrientAmounts = {};
    window.setSessionUser = function(user) {
        if (user != null) {
            userId = user.userId;
        }
    };
    $('#toggleSize').click(function() {
        const isSize = $(this).toggleClass('active').hasClass('active');
        setServingSize(!isSize);
        $(this).text(isSize ?  "100g기준량 보기" : "총제공량 보기");
    });
    function setServingSize(isSize){
        if(isSize){
            servingSize = 1;
        }else{
            servingSize = foodWeight/100;
        }
        loadNutriTable(servingSize);
    }
    function loadNutriTable(servingSize) {
        fetch(urlWithParams)
            .then(response => response.json())
            .then(foodData => {
                if (!foodData || !foodData.nutrientlist) {
                    console.error('Food 객체나 nutrientlist 배열이 정의되지 않았습니다.');
                    return;
                }
                foodWeight = foodData.weight;
                const kcalAmount = (foodData.nutrientlist[0].amount * servingSize).toFixed(1);
                $('#nutriKcal').text(`${kcalAmount} kcal`);

                nutrientAmounts = {};

                foodData.nutrientlist.forEach(nutrient => {
                    nutrientAmounts[nutrient.name] = nutrient.amount;
                });

                updateChart();
                var dailyIntake = {
                    '탄수화물': baselMetabolism * 0.5 / 4 , //총 칼로리의 45-65% 탄수화물 1g 당 4 칼로리
                    '단백질': userWeight*1, //체중(kg)당 0.8~1.0g
                    '지방': baselMetabolism * 0.3 / 9, //총 칼로리의 20-35% 지방은 1g당 9칼로리
                    '당류': baselMetabolism * 0.1 /4 , // 총 칼로리의 10% 당류 1g 당 4 칼로리
                    '나트륨': 2300, // 나트륨 제한
                    '비타민 C': 70, //
                    '칼슘': 1000,
                    '포화지방산': baselMetabolism * 0.1 /9, //총 칼로리의 10% 이하로 제한 지방은 1g당 9칼로리
                    '콜레스테롤': 300, //미국심장협회(American Heart Association, AHA) 참고 심장약한사람은 200
                    '트랜스지방산': 2 // 트랜스지방산 제한
                };

                const nutrientTables = [{
                        tableId: 'nutrientTable',
                        nutrients: ['탄수화물', '단백질', '지방', '당류', '나트륨'],
                        values: ['g', 'g', 'g', 'g', 'mg']
                    },{
                        tableId: 'nutrientTable2',
                        nutrients: ['비타민 C', '칼슘', '포화지방산','콜레스테롤', '트랜스지방산'],
                        values: ['mg', 'mg', 'g', 'mg', 'g']
                    }
                ];
                nutrientTables.forEach(({ tableId }) => {//넣기전 초기화
                    $(`#${tableId} tbody`).empty();
                });
                let maxNutrient;
                let maxPercentage = 0;
                nutrientTables.forEach(({ tableId, nutrients, values }) => {
                    nutrients.forEach((nutrient, index) => {
                        const unit = values[index];
                        const nutrientData = foodData.nutrientlist.find(n => n.name === nutrient) || { name: nutrient, amount: 0, value: '' };
                        const amount = (nutrientData.amount * servingSize).toFixed(1);
                        const percentage = (amount / dailyIntake[nutrientData.name] * 100).toFixed(1);// 일일권장량 계산
                        // 가장 높은 퍼센트 값 추출
                        if (percentage > maxPercentage) {
                            maxNutrient = nutrientData.name;
                        }
                        const row = $('<tr></tr>');
                        row.append($('<td></td>').addClass('nutrientname').text(nutrientData.name));
                        row.append($('<td></td>').addClass('amount').text(`${amount} ${unit}`));

                        const progressBar =
                            $('<div></div>').addClass('progress-bar').css('width', `${percentage}%`);
                        const progressDiv =
                            $('<div></div>').addClass('progress').append(progressBar);

                        row.append($('<td></td>').append(progressDiv));
                        $(`#${tableId} tbody`).append(row);
                    });
                });
                $("#nutriText").text(maxNutrient+" 함유량이 높은 제품입니다.")
                //sessionStorage.setItem('nutrilist', );
            })
            .catch(error => {
                console.error('데이터를 가져오는 데 실패했습니다:', error);
        });
    }

    //즐겨찾기 이벤트
    function updatePreferredState() {
        const foodId = $("#foodContainer").data("food");
        let querydata = {
            "userId": userId,
            "foodId": foodId,
            "preferredState": $('#favorite-button').hasClass('favorited'),
        };
        $.ajax({
            url: "/bookmark/favorited",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(querydata),
            success: function(result) {
                if(result.preferredState){
                    $("#favorite-button").addClass('favorited')
                    alert("즐겨찾기 추가되었습니다!");
                }else{
                    $("#favorite-button").removeClass('favorited')
                    alert("즐겨찾기에서 삭제되었습니다.");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
                if (typeof error_run === 'function') {
                    error_run(xhr, status, error);
                }
            }
        });
    }
    //제외식품이벤트
    function updateExcludedState() {
        const foodId = $("#foodContainer").data("food");
        let querydata = {
            "userId": userId,
            "foodId": foodId,
            "excludedState": $('#exclude-button').hasClass('excluded')
        };
        $.ajax({
            url: "/bookmark/excluded",
            type: "POST",
            contentType: "application/json",
            data: JSON.stringify(querydata),
            success: function(result) {
                if(result.excludedState){
                    $('#exclude-button').addClass('excluded')
                    alert("제외식품으로 추가되었습니다!");
                }else{
                    $('#exclude-button').removeClass('excluded')
                   alert("제외식품에서 제거되었습니다!");
                }
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
                if (typeof error_run === 'function') {
                    error_run(xhr, status, error);
                }
            }
        });
    }
    function loadBookmark() {
        const foodId = $("#foodContainer").data("food");
        let querydata = {
           "userId": userId,
           "foodId": foodId,
        }
        $.ajax({
            url: "/bookmark/check",
            type: "POST",
            dataType: "json",
            data: querydata,
            success: function(result) {
                if(result.preferredState){
                    const favoriteButton = $('#favorite-button')
                                .addClass('favorited')
                }
                if(result.excludedState){
                    const excludeButton = $('#exclude-button')
                                .addClass('excluded')
                }
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
                if (typeof error_run === 'function') {
                    error_run(xhr, status, error);
                }
            },
            complete: function(xhr, status) {
                try {
                    if (!xhr.responseText) {
                        throw new Error("Empty response from server");
                    }
                    const response = JSON.parse(xhr.responseText);
                    if (!response) {
                        throw new Error("Response is null");
                    }
                } catch (error) {
                    console.error("Error occurred during JSON parsing or empty response: " + error);
                }
            }
        });
    }
    // 즐겨찾기
    window.toggleFavorite = function () {
        if (userId == null) {
            alert("로그인 해주세요!");
            return;
        }else{
            updatePreferredState();
        }
    };

    // 제외식품
    window.toggleExcludeFood = function () {
        if (userId == null) {
            alert("로그인 해주세요!");
            return;
        }else{
            updateExcludedState();
        }

    };
    function loadPreferredFood() {
        $.ajax({
            url: "/bookmark/preferredlist",
            type: "POST",
            dataType: "json",
            success: function(result) {
                const $container = $('#preferrerd-food-list');
                $container.empty(); // 기존 내용을 지우기
               for (let i = 0; i < result.length; i++) {
                   const food = result[i];
                   const $foodItem = $('<div class="d-flex align-items-center justify-content-start"></div>');

                   const $imgDiv = $('<div class="rounded me-4" style="width: 100px; height: 100px;"></div>');

                   if (food.name === food.categoryName) {
                       const $img = $('<img class="img-fluid rounded" style="width: 100px; height: 100px;" alt="Image">').attr('src', `/common/img/category/${food.categoryName}.jpg`);
                       $imgDiv.append($img);
                   } else {
                       const $img = $('<img class="img-fluid rounded" style="width: 100px; height: 100px;" alt="Image">').attr('src', `/common/img/category/${food.categoryName}_${food.name}.jpg`);
                       $imgDiv.append($img);
                   }

                   const $infoDiv = $('<div></div>');
                   const $name = $('<h6 class="mb-2"></h6>').text(food.name);

                   const $energyDiv = $('<div class="d-flex mb-2"></div>');
                   const $energy = $('<h5 class="fw-bold me-2"></h5>').html(`100g/ml당 <br/> ${food.nutrientlist[0].amount} kcal`);
                   $energyDiv.append($energy);

                   $infoDiv.append($name).append($energyDiv);
                   $foodItem.append($imgDiv).append($infoDiv);
                   $container.append($foodItem);
               }
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
                if (typeof error_run === 'function') {
                    error_run(xhr, status, error);
                }
            }
        });
    }
    function updateChart(){
        if (!$('#nutritionChart-detail').data('chartInitialized')) {
            if (nutritionChart) {
                nutritionChart.destroy();
            }
            var ctx = $('#nutritionChart-detail')[0].getContext('2d');
            nutritionChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['탄수화물', '단백질', '지방', '당류', '나트륨'],
                    datasets: [{
                        label: '기초대사량 구성',
                        data: [nutrientAmounts['탄수화물'],
                               nutrientAmounts['단백질'],
                               nutrientAmounts['지방'],
                               nutrientAmounts['당류'],
                               nutrientAmounts['나트륨']/1000], // 예시 데이터, 실제 데이터로 변경 가능
                        backgroundColor: [
                            'rgba(150, 250, 50, 0.2)',
                            'rgba(54, 162, 235, 0.2)',
                            'rgba(255, 206, 86, 0.2)',
                            'rgba(255, 99, 132, 0.2)',
                            'rgba(153, 102, 255, 0.2)'
                        ],
                        borderColor: [
                            'rgba(150, 250, 50, 1)',
                            'rgba(54, 162, 235, 1)',
                            'rgba(255, 206, 86, 1)',
                            'rgba(255, 99, 132, 1)',
                            'rgba(153, 102, 255, 1)'
                        ],
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        tooltip: {
                            callbacks: {
                                label: function(context) {
                                    let label = context.label || '';
                                    if (label) {
                                        label += ': ';
                                    }
                                    if (context.parsed !== null) {
                                        label += context.parsed + '%';
                                    }
                                    return label;
                                }
                            }
                        }
                    }
                }
            });
        }
    }
    $(document).ready(function() {
        window.setSessionUser = function(user) {
            if (user != null) {
                baselMetabolism = user.baselMetabolism === 0 ? 2000 : user.baselMetabolism;
                userWeight = user.weight=== null ? 60 : user.weight;
                userId = user.userId;
            }
        };
        //로드 시 데이터표시
        setSessionUser(sessionUser);
        loadBookmark();
        loadPreferredFood();
        loadNutriTable(1);

    });
})(jQuery);
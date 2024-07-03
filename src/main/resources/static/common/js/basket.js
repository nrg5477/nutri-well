(function($) {
"use strict";

    var baselMetabolism = 2000;
    var userWeight = 60;
    var nutritionChart;
    var userId;
    $('#initBasket').click(function() {
        sessionStorage.removeItem('foodNames');
        sessionStorage.removeItem('nutrientData');
        updateTable({});
        updateFoodTable([]);
        nutritionChart.destroy();
        deleteTable();
        $('#nutritionChart').data('chartInitialized', false);
    });
    //Local Storage
    function getStoredNutrients() {
        var storedNutrient = sessionStorage.getItem('nutrientData');
        return storedNutrient ? JSON.parse(storedNutrient) : {};
    }
    //영양소저장
    function storeNutrients(nutrientData) {
        sessionStorage.setItem('nutrientData', JSON.stringify(nutrientData));
    }
    //음식이름 가져오기
    function getStoredFoods() {
        var storedFood = JSON.parse(sessionStorage.getItem('foodNames')) || [];
        return storedFood;
    }
    //음식이름 저장
    function storeFoods(foodData) {
        let foodNames = getStoredFoods();
        if (foodNames.length > 20) {
            alert('그만드세요;;');
            return false;
        }
        foodNames.push(foodData)
        sessionStorage.setItem('foodNames', JSON.stringify(foodNames));
        return true;
    }
    //영양소 데이터 누적
    function accumulateNutrients(newNutrients, foodWeight) {
        var storedNutrients = getStoredNutrients();

        newNutrients.forEach(function(nutrient) {
            if (!storedNutrients[nutrient.name]) {
                storedNutrients[nutrient.name] = 0;
            }
            storedNutrients[nutrient.name] += nutrient.amount * (foodWeight/100);
        });
        storeNutrients(storedNutrients);
        return storedNutrients;
    }
    //테이블 업데이트
    function updateTable(nutrientData) {
        var energy = nutrientData['에너지'] || 0;
        var kcalPercentage = (energy / baselMetabolism * 100).toFixed(1);
        var isOver = kcalPercentage > 100;
        var progressBarHtml = '<span class="totalKcal' + (isOver ? ' over' : '') + '">' +  kcalPercentage + ' % (' + energy.toFixed(0) + ' / ' + baselMetabolism + ')</span>'
                               + '<progress value="' + energy.toFixed(0) + '" max="' + baselMetabolism + '" class="' + (isOver ? 'over' : '') + '"></progress>'
                               + '</div>';
        var tableHtml = '<table>';
        tableHtml += '<thead>';
        if (nutrientData['에너지']) {
            tableHtml += '<tr>';
            tableHtml += '<th>총 내용량</th>';
            tableHtml += '<th><span class="kcal" id="todayKcal">' + nutrientData['에너지'].toFixed(1) + ' kcal</span></th>';
            tableHtml += '</tr>';
        }
        tableHtml += '</thead>';
        tableHtml += '<tbody>';

        var nutrients = ['탄수화물', '단백질', '지방', '당류', '나트륨', '트랜스지방산', '포화지방산', '콜레스테롤'];
        var values = ['g', 'g', 'g', 'g', 'mg', 'g', 'g', 'mg'];
        //각영양소에 대한 일일권장량 설정
        var dailyIntake = {
            '탄수화물': baselMetabolism * 0.5 / 4 , //총 칼로리의 45-65% 탄수화물 1g 당 4 칼로리
            '단백질': userWeight*1, //체중(kg)당 0.8~1.0g
            '지방': baselMetabolism * 0.3 / 9, //총 칼로리의 20-35% 지방은 1g당 9칼로리
            '당류': baselMetabolism * 0.1 /4 , // 총 칼로리의 10% 당류 1g 당 4 칼로리
            '나트륨': 2300, // 나트륨 제한
            '트랜스지방산': 2, // 트랜스지방산 제한
            '포화지방산': baselMetabolism * 0.1 /9, //총 칼로리의 10% 이하로 제한 지방은 1g당 9칼로리
            '콜레스테롤': 300 //미국심장협회(American Heart Association, AHA) 참고 심장약한사람은 200
        };
        nutrients.forEach(function(nutrientName, index) {
            var amount = (nutrientData[nutrientName] || 0).toFixed(1);
            var unit = values[index];
            var percentage = (amount / dailyIntake[nutrientName] * 100).toFixed(1);
            var percentClass = percentage > 100 ? 'over-limit' : '';

            tableHtml += '<tr>';
            tableHtml += '<td>' + nutrientName + '<div class="input-wrapper"><input class="amount" value="' + amount + '"><span>' + unit + '</span></div></td>';
            tableHtml += '<td><input class="percent ' + percentClass + '" value="' + percentage + '" readonly>%</td>';
            tableHtml += '<td><div class="bar-container"><div class="bar" style="width: ' + percentage + '%;"></div></div></td>';
            tableHtml += '</tr>';
        });
        tableHtml += '</tbody>';
        tableHtml += '</table>';

        $('.nutrition-info').html(progressBarHtml + tableHtml);
    }
    //음식이름 업데이트
    function updateFoodTable(foodData) {
        var tableHtml = '<table>';
        tableHtml += '<thead>';
        tableHtml += '<tr>';
        tableHtml += '<th>오늘먹은 음식 리스트</th>';
        tableHtml += '</tr>';
        tableHtml += '</thead>';
        tableHtml += '<tbody>';
        foodData.forEach(function(name) {
            tableHtml += '<tr>';
            tableHtml += '<td>' + name + '</td>';
            tableHtml += '</tr>';
        });

        tableHtml += '</tbody>';
        tableHtml += '</table>';

        $('.basketFood').html(tableHtml);
    }
    //클릭 이벤트
    window.addBasket = function(foodId) {
        let data = {
            "foodId": foodId,
            "userId": userId,
        };
        $.ajax({
            url: "basket/insert",
            type: "POST",
            dataType: "json",
            data: data,
            success: function(response) {
                console.log(response)
                if(storeFoods(response.name)){
                    //새 영양소 데이터누적 들어갈땐 중량포함해서 누적한다.
                    var accumulatedNutrients = accumulateNutrients(response.nutrientlist,response.weight);
                    //테이블업데이트
                    updateTable(accumulatedNutrients);
                    updateFoodTable(getStoredFoods());
                    updateChart();
                };
            },
            error: function(xhr, status, error) {
                console.error("Error occurred: " + error);
            }
        });
    };
    $('#bmr-tab').on('shown.bs.tab', function () {
        updateChart();
        //$('#nutritionChart').data('chartInitialized', true);
    });
    function updateChart(){
        if (!$('#nutritionChart').data('chartInitialized')) {
            if (nutritionChart) {
                nutritionChart.destroy();
            }
            var ctx = $('#nutritionChart')[0].getContext('2d');
            let nutri = getStoredNutrients();
            nutritionChart = new Chart(ctx, {
                type: 'pie',
                data: {
                    labels: ['탄수화물', '단백질', '지방', '당류', '나트륨'],
                    datasets: [{
                        label: '기초대사량 구성',
                        data: [nutri['탄수화물'],
                               nutri['단백질'],
                               nutri['지방'],
                               nutri['당류'],
                               nutri['나트륨']/1000], // 예시 데이터, 실제 데이터로 변경 가능
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
    function saveCalender(){
        if(userId === null){
            alert('로그인 해주세요');
        }else{
            let data = {
                "foodId": foodId,
                "userId": userId,
            };
            $.ajax({
                url: "basket/insert",
                type: "POST",
                dataType: "json",
                data: data,
                success: function(response) {
                    console.log(response)
                    if(storeFoods(response.name)){
                        //새 영양소 데이터누적 들어갈땐 중량포함해서 누적한다.
                        var accumulatedNutrients = accumulateNutrients(response.nutrientlist,response.weight);
                        //테이블업데이트
                        updateTable(accumulatedNutrients);
                        updateFoodTable(getStoredFoods());
                        updateChart();
                    };
                },
                error: function(xhr, status, error) {
                    console.error("Error occurred: " + error);
                }
            });
        }
    }
    //즐겨찾기
    function loadBookMark(){
        if(userId === null){
            return;
        }
        const baseUrl = 'http://localhost:9079';
        $.ajax({
            url: baseUrl + '/basket/getBookMark',
            type: 'POST',
            dataType: "json",
            data:  { userId: userId },
            success: function(foodlist) {
                const select = $('#bookmark-select');
                foodlist.forEach(function(food) {
                    const option = $('<option></option>')
                        .attr('value', food.id)
                        .text(food.name);
                    select.append(option);
                });
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
    }
    function deleteTable(){
        const baseUrl = 'http://localhost:9079';

        $.ajax({
            url: baseUrl + '/basket/delete',
            type: 'POST',
            data:  { userId: userId },
            success: function() {
                alert('초기화 완료');
            },
            error: function(error) {
                console.error('Error:', error);
            }
        });
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
        updateTable(getStoredNutrients());
        updateFoodTable(getStoredFoods());
        loadBookMark();
    });
})(jQuery);
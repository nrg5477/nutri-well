(function ($) {
    "use strict";

    let userid = null;
    window.setSessionUser = function(user) {
        if (user != null) {
            userid = user.userId;
        }
    };
    //추천상품목록 load
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
   //요청 url세팅
   function buildUrl(base, params) {
       return base + '?' + new URLSearchParams(params).toString();
   }
   //세션 초기화
   function resetSession() {
       sessionStorage.removeItem('nutrients');
       sessionStorage.removeItem('min');
       sessionStorage.removeItem('max');
   }
   //체크박스 영양소 배열 저장
   function updateNutrients() {
       const nutrients = JSON.parse(sessionStorage.getItem('nutrients') || '[]');
       $('input[type="checkbox"]').each(function() {
           $(this).prop('checked', nutrients.includes($(this).val()));
       });
   }
   //검색어 입력시 버튼이벤트
   $('#searchButton').on('click', function() {
       var queryValue = $('#query').val().trim();
       if (!queryValue) {
           alert('검색어를 입력하세요');
           return;
       }

       resetSession();

       location.href = buildUrl('/search', {
           query: queryValue,
           page: 0,
           size: 12
       });
   });
   //카테고리별 검색
   $('.searchCategory').on('click', function() {
       resetSession();

       location.href = buildUrl('/searchCategory', {
           category: $(this).data("filter-value"),
           page: 0,
           size: 12
       });
   });
   //영양소 범위 검색
   $('#detailSearch').on('click', function() {
       var queryValue = $('#queryContainer').data("query");
       var category = $('#categoryContainer').data("category") || 0;
       var nutrients = JSON.parse(sessionStorage.getItem('nutrients') || '[]');
       let min = $inputLeft.val();
       let max = $inputRight.val();

       var params = {
           page: 0,
           size: 12,
           nutrients: nutrients.join(',')
       };

       if (queryValue) {
           params.query = queryValue;
       } else if (category != 0) {
           params.category = category;
       }

       if (min) params.min = min;
       if (max) params.max = max;

       sessionStorage.setItem('min', min);
       sessionStorage.setItem('max', max);

       location.href = buildUrl(queryValue ? '/search' : '/searchCategory', params);
   });
   //checkBox 변경시 load 및 save
   $('input[type="checkbox"]').change(function() {
       const nutrient = $(this).val();
       const isChecked = $(this).is(':checked');
       var nutrients = JSON.parse(sessionStorage.getItem('nutrients') || '[]');

       if (isChecked) {
           if (!nutrients.includes(nutrient)) {
               nutrients.push(nutrient);
           }
       } else {
           nutrients = nutrients.filter(item => item !== nutrient);
       }

       sessionStorage.setItem('nutrients', JSON.stringify(nutrients));
   });
/*=============================search.html pagination=============================*/
    const totalPage = $('#pageContainer').data("pages");
    const maxPagesToShow = 10;
    let currentPageGroup = 0;
    let currentPage = parseInt(new URLSearchParams(window.location.search).get('page')) || 0;

    function renderPagination() {
        $('#page-container').empty();
        const startPage = currentPageGroup * maxPagesToShow;
        const endPage = Math.min(startPage + maxPagesToShow, totalPage);

        for (let i = startPage; i < endPage; i++) {
            const $pageLink = $('<a href="#" class="rounded"></a>');
            $pageLink.attr('data-filter-type', 'itemPage');
            $pageLink.attr('data-filter-value', i);
            $pageLink.text(i + 1);

            if (i === currentPage) {
                $pageLink.addClass('active');
            }

            $('#page-container').append($pageLink);
        }
    }

    $('#prev').on('click', function(event) {
        event.preventDefault();
        if (currentPageGroup > 0) {
            currentPageGroup--;
            renderPagination();
        }
    });

    $('#next').on('click', function(event) {
        event.preventDefault();
        if ((currentPageGroup + 1) * maxPagesToShow < totalPage) {
            currentPageGroup++;
            renderPagination();
        }
    });

    $(document).on('click', '#page-container .rounded', function(event) {
        event.preventDefault();
        var queryValue = $('#queryContainer').data("query") || "";
        var category = $('#categoryContainer').data("category") || 0;
        var nutrients = JSON.parse(sessionStorage.getItem('nutrients') || '[]');
        var page = $(this).data("filter-value");
        let min = sessionStorage.getItem('min');
        let max = sessionStorage.getItem('max');

        var params = new URLSearchParams();
        params.append("page", page);
        params.append("size", 12);

        if (queryValue) {
            params.append("query", queryValue);
        } else if (category != 0) {
            params.append("category", category);
        }

        if (nutrients.length > 0 && min >= 0 && max > 0) {
            params.append("nutrients", nutrients.join(','));
            if (min) params.append("min", min);
            if (max) params.append("max", max);
        }

        var url = (queryValue) ? '/search?' : '/searchCategory?';
        url += params.toString();

        currentPage = page;
        $(this).addClass('active');
        location.href = url;
    });

    /*=============================slider 효과=============================*/
    const $inputLeft = $("#input-left");
    const $inputRight = $("#input-right");

    const $thumbLeft = $(".slider .thumb.left");
    const $thumbRight = $(".slider .thumb.right");
    const $range = $(".slider .range");

    $inputLeft.on("input", function() { setLeftValue(true); });
    $inputRight.on("input", function() { setRightValue(true); });

    function setInitValue(){
        const min = parseInt($inputLeft.attr("min"));
        const max = parseInt($inputLeft.attr("max"));
        let leftvalue = 0;
        let rightvalue = 100;

        $inputLeft.val(leftvalue);
        $("#leftValue").val(leftvalue);

        let percent = ((leftvalue - min) / (max - min)) * 100;
        $thumbLeft.css("left", percent + "%");
        $range.css("left", percent + "%");

        $inputRight.val(rightvalue);
        $("#rightValue").val(rightvalue);

        percent = ((rightvalue - min) / (max - min)) * 100;
        $thumbRight.css("right", (100 - percent) + "%");
        $range.css("right", (100 - percent) + "%");
    }

    function setLeftValue(isInputTag) {
        const min = parseInt($inputLeft.attr("min"));
        const max = parseInt($inputLeft.attr("max"));
        let value = 0;

        if(!isInputTag){
            value = $('#leftValue').val();
        }else{
            value = $inputLeft.val();
        }

        value = Math.min(value, parseInt($inputRight.val())); // 초기 왼쪽 input값
        $inputLeft.val(value);
        $("#leftValue").val(value);

        const percent = ((value - min) / (max - min)) * 100;
        $thumbLeft.css("left", percent + "%");
        $range.css("left", percent + "%");
    }

    function setRightValue(isInputTag) {
        const min = parseInt($inputRight.attr("min"));
        const max = parseInt($inputRight.attr("max"));
        let value = 0;

        if(!isInputTag){
            value = $('#rightValue').val();
        }else{
            value = $inputRight.val();
        }

        value = Math.max(value, parseInt($inputLeft.val()) + 1); // 초기 오른쪽 input값

        $inputRight.val(value);
        $("#rightValue").val(value);

        const percent = ((value - min) / (max - min)) * 100;
        $thumbRight.css("right", (100 - percent) + "%");
        $range.css("right", (100 - percent) + "%");
    }

    $("#setRange").on("click", function() {
        let left = parseInt($('#leftValue').val());
        let right = parseInt($('#rightValue').val());

        if(left > right ){
            alert('최소값은 최댓값보다 클 수 없습니다.');
            return;
        }else if(left > 499 || right > 500){
            alert('최댓값은'+ $('#rightValue').attr('max') +'이하입니다.');
            return;
        }else{
            setRightValue(false);
            setLeftValue(false);
        }
    });

    $('.dropdown-toggle').on('click', function() {
        var $container = $('.scrollable');
        var $this = $(this);
        // 컨테이너 안에서 클릭된 버튼의 위치를 계산
        var containerOffset = $container.offset().top;
        var elementOffset = $this.offset().top;
        var scrollTop = $container.scrollTop();
        var offset = elementOffset - containerOffset + scrollTop;

        // 부드러운 스크롤 애니메이션
        $container.animate({
            scrollTop: offset
        }, 500);
    });
    renderPagination();
    updateNutrients();
    setLeftValue(true);
    setRightValue(true);
    // 초기 값 설정
    loadPreferredFood();
    setInitValue();
})(jQuery);
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
                   const $img = $('<img class="img-fluid rounded" alt="">').attr('src', "/common/img/featur-1.jpg");
                   $imgDiv.append($img);

                   const $infoDiv = $('<div></div>');
                   const $name = $('<h6 class="mb-2"></h6>').text(food.name);

                   const $energyDiv = $('<div class="d-flex mb-2"></div>');
                   const $energy = $('<h5 class="fw-bold me-2"></h5>').text(`${food.nutrientlist[0].amount} kcal`);
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
   function buildUrl(base, params) {
       return base + '?' + new URLSearchParams(params).toString();
   }

   function resetSession() {
       sessionStorage.removeItem('nutrients');
       sessionStorage.removeItem('min');
       sessionStorage.removeItem('max');
   }

   function updateNutrients() {
       const nutrients = JSON.parse(sessionStorage.getItem('nutrients') || '[]');
       $('input[type="checkbox"]').each(function() {
           $(this).prop('checked', nutrients.includes($(this).val()));
       });
   }

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

   $('.searchCategory').on('click', function() {
       resetSession();

       location.href = buildUrl('/searchCategory', {
           category: $(this).data("filter-value"),
           page: 0,
           size: 12
       });
   });

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
   updateNutrients();
/*=============================shop.html pagination=============================*/
    const totalPage = $('#pageContainer').data("pages");
    const maxPagesToShow = 10;
    let currentPageGroup = 0;

    function renderPagination() {
        $('#page-container').empty();
        const startPage = currentPageGroup * maxPagesToShow;
        const endPage = Math.min(startPage + maxPagesToShow, totalPage);

        for (let i = startPage; i < endPage; i++) {
            const $pageLink = $('<a href="#" class="rounded"></a>');
            $pageLink.attr('data-filter-type', 'itemPage');
            $pageLink.attr('data-filter-value', i);
            $pageLink.text(i + 1);

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

        $(this).addClass('active');
        location.href = url;
    });
    renderPagination();
    /*=============================slider 효과=============================*/
    const $inputLeft = $("#input-left");
    const $inputRight = $("#input-right");

    const $thumbLeft = $(".slider .thumb.left");
    const $thumbRight = $(".slider .thumb.right");
    const $range = $(".slider .range");

    function setLeftValue() {
        const min = parseInt($inputLeft.attr("min"));
        const max = parseInt($inputLeft.attr("max"));
        let value = $inputLeft.val();
        //let value = parseInt(sessionStorage.getItem('min'));

        value = Math.min(value, parseInt($inputRight.val()) - 1);
        $inputLeft.val(value);

        const percent = ((value - min) / (max - min)) * 100;
        $thumbLeft.css("left", percent + "%");
        $range.css("left", percent + "%");

        updateRangeValue();
    }

    function setRightValue() {
        const min = parseInt($inputRight.attr("min"));
        const max = parseInt($inputRight.attr("max"));
        let value = $inputRight.val();

        value = Math.max(value, parseInt($inputLeft.val()) + 1);
        $inputRight.val(value);

        const percent = ((value - min) / (max - min)) * 100;
        $thumbRight.css("right", (100 - percent) + "%");
        $range.css("right", (100 - percent) + "%");

        updateRangeValue();
    }

    function updateRangeValue() {
        $("#rangeValue").text($inputLeft.val() + " - " + $inputRight.val());
    }
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
        }, 500); // 500ms 동안 부드러운 스크롤링
    });
    $inputLeft.on("input", setLeftValue);
    $inputRight.on("input", setRightValue);
    // 초기 값 설정
    setLeftValue();
    setRightValue();
    loadPreferredFood();
})(jQuery);


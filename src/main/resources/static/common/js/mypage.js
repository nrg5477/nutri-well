<!-- FullCalendar Initialization -->
document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        events: '/calendar/api/calendars', // URL to fetch events
        displayEventTime: false,
        eventDisplay: 'block',
        eventClick: function(info) {
            fetchDailyNutrition(info.event.id); // 이벤트 클릭 시 호출
        }
    });
    calendar.render();
});

<!-- 캘린더 캡쳐 -->
document.getElementById('capture-btn').addEventListener('click', function () {
    html2canvas(document.getElementById('calendar'), {
        onrendered: function (canvas) {
            var img = canvas.toDataURL('image/png');
            document.getElementById('captured-image').src = img;
            document.getElementById('captured-image').style.display = 'block';

            var downloadLink = document.getElementById('download-link');
            downloadLink.href = img;
            downloadLink.style.display = 'inline'; // 다운로드 링크 표시
        }
    });
});

<!--수정성공-->
function showSuccessAlert() {
    alert("회원수정이 완료되었습니다");
    return true;
}

var chartInstance;

function fetchDailyNutrition(calendarId) {
    $.ajax({
        url: '/calendar/api/dailyNutrition',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({ calendarId: calendarId }),
        success: function(response) {
            showNutritionModal(response);
        },
        error: function(xhr, status, error) {
            console.error('Error occurred:', error);
            alert('데이터를 가져오는 데 실패했습니다.');
        }
    });
}

function showNutritionModal(data) {
    const modal = $('#nutritionModal');
    const chartEl = $('#nutritionChart');
    const ctx = chartEl[0].getContext('2d');

    // 이전에 생성된 차트가 있으면 파괴
    if (chartInstance) {
        chartInstance.destroy();
    }

    // 권장 섭취량 설정
    const recommendedIntake = {
        '탄수화물': baselMetabolism * 0.5 / 4,
        '단백질': userWeight * 1,
        '지방': baselMetabolism * 0.3 / 9,
        '당류': baselMetabolism * 0.1 / 4,
        '나트륨': 2300,
        '트랜스지방산': 2,
        '포화지방산': baselMetabolism * 0.1 / 9,
        '콜레스테롤': 300
    };

    // 섭취량 데이터 생성
    const intakeData = Object.keys(data.nutrients).map(nutrient => data.nutrients[nutrient]);
    // const intakeData = Object.keys(data.nutrients).map(nutrient => {
    //     return data.nutrients[nutrient] * (data.weight / data.servingSize);
    // });

    // 권장 섭취량 데이터 생성
    const maxIntakeData = Object.keys(data.nutrients).map(nutrient => recommendedIntake[nutrient]);

    // 동적으로 캔버스 높이 설정
    const numNutrients = Object.keys(data.nutrients).length;
    const canvasHeight = numNutrients * 50; // 각 영양소에 대해 50px 높이 할당
    chartEl.height(canvasHeight);

    chartInstance = new Chart(ctx, {
        type: 'bar',
        data: {
            labels: Object.keys(data.nutrients),
            datasets: [
                {
                    label: '섭취량',
                    data: intakeData,
                    backgroundColor: 'rgba(144, 238, 144, 0.2)',
                    borderColor: 'rgba(144, 238, 144, 1)',
                    borderWidth: 1,
                    barThickness: 15, // 막대 두께 조정
                },
                {
                    label: '권장 섭취량',
                    data: maxIntakeData,
                    backgroundColor: 'rgba(255, 99, 132, 0.2)',
                    borderColor: 'rgba(255, 99, 132, 1)',
                    borderWidth: 1,
                    barThickness: 15, // 막대 두께 조정
                }
            ]
        },
        options: {
            indexAxis: 'y',
            scales: {
                x: {
                    beginAtZero: true,
                    max: Math.max(...Object.values(recommendedIntake)) * 1.1 // 최대 권장 섭취량의 110%로 설정
                }
            }
        }
    });

    modal.modal('show');
}

$(document).ready(function() {
    $.ajax({
        url: '/checkSession',
        type: 'GET',
        success: function (response) {
            if (response.loggedIn) {
                setSessionUser(response);
            } else {
                alert('로그인이 필요합니다.');
            }
        },
        error: function (xhr, status, error) {
            console.error('Error occurred:', error);
            alert('사용자 정보를 가져오는 데 실패했습니다.');
        }
    });
});

function setSessionUser(user) {
    if (user != null) {
        baselMetabolism = user.baselMetabolism === 0 ? 2000 : user.baselMetabolism;
        userWeight = user.weight === null ? 60 : user.weight;
        userId = user.userId;

        recommendedIntake = {
            '탄수화물': baselMetabolism * 0.5 / 4,
            '단백질': userWeight * 1,
            '지방': baselMetabolism * 0.3 / 9,
            '당류': baselMetabolism * 0.1 / 4,
            '나트륨': 2300,
            '트랜스지방산': 2,
            '포화지방산': baselMetabolism * 0.1 / 9,
            '콜레스테롤': 300
        };
    }
}
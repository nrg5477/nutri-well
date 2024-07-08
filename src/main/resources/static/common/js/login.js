$(document).ready(function() {
    $('#login-button').click(function() {
        var email = $('#login-id').val();
        var password = $('#login-password').val();

        $.ajax({
            url: '/login',
            method: 'POST',
            data: {
                email: email,
                password: password
            },
            success: function(response) {
                alert('로그인 성공');
                $('#login-text').text(response.username);
                $('#login-link').addClass('d-none');
                $('#logout-link').removeClass('d-none');
                $('#username-display').text(response.username).show();
                $('#login-separator').removeClass('d-none');
                $('#loginModal').modal('hide');
            },
            error: function(response) {
                alert('로그인 실패: ' + response.responseText);
            }
        });
    });

    $('#logout-link').click(function() {
        $.ajax({
            url: '/logout',
            method: 'POST',
            success: function(response) {
                alert('로그아웃 성공');
                $('#login-text').text('로그인');
                $('#login-link').removeClass('d-none');
                $('#logout-link').addClass('d-none');
                $('#username-display').hide();
                $('#login-separator').addClass('d-none');
            },
            error: function(response) {
                alert('로그아웃 실패: ' + response.responseText);
            }
        });
    });

    // 초기 상태 설정
    $.ajax({
        url: '/checkSession',
        method: 'GET',
        success: function(response) {
            if (response.loggedIn) {
                $('#login-text').text(response.username);
                $('#login-link').addClass('d-none');
                $('#logout-link').removeClass('d-none');
                $('#username-display').text(response.username).show();
                $('#login-separator').removeClass('d-none');
            } else {
                $('#login-text').text('로그인');
                $('#login-link').removeClass('d-none');
                $('#logout-link').addClass('d-none');
                $('#username-display').hide();
                $('#login-separator').addClass('d-none');
            }
        }
    });
});
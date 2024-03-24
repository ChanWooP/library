function home() {
    location.href = '/';
}

function login() {
    location.href = '/sign-in/login';
}

function logout() {
    location.href = '/logout';
}

function myPage(userId) {
    location.href = '/user/mypage/' + userId;
}

function join() {
    location.href = '/sign-in/join';
}

let login = new Vue({
    el: '#login-outer',
    data: {
        isDisplay: true,
        isBtnNormal: true,
        normalLogin: true,
        mouseDown: false,
        left: (document.documentElement.clientWidth - 360) / 2,
        top: (document.documentElement.clientHeight - 430) * 0.2,
        mouseDownOffsetLeft: 0,
        mouseDownOffsetTop: 0,
        secondCnt: '',
        errorHint: '',
        phone: '',
        password: '',
        smsCode: '',
        verifyCode: '',
        imgUrl: 'url(' + webUrl + '/imgVerify?' + Math.random() + ')',
        forgetPasswordUrl: '暂时未写好',
        registerUrl: './register.html',
        loginSuccessUrl: './rental.html'
    },
    mounted: function () {
        window.addEventListener("resize", function () {
            // console.log("窗口大小改变");
            login.left = (document.documentElement.clientWidth - 360) / 2;
            login.top = (document.documentElement.clientHeight - 430) * 0.2;
        })
    },
    methods: {
        closeLoginForm: function () {
            console.log("关闭");
            this.isDisplay = false;
            popLoginBtn.isShow = true;
        },
        changeVerifyCode: function () {
            this.imgUrl = 'url(' + webUrl + '/imgVerify?' + Math.random() + ')';
        },
        changeLoginType: function () {
            this.errorHint = '';
            this.normalLogin = !this.normalLogin;
        },
        phonePasswordLogin: function () {
            this.errorHint = '';
            if (this.phone.length <= 0) {
                this.errorHint = '请输入用户名!';
                return;
            }
            if (this.password.length <= 0) {
                this.errorHint = '请输入密码!';
                return;
            }
            // if (this.verifyCode.trim().length !== 4) {
            //     this.errorHint = '验证码有误，请重新输入!';
            //     return;
            // }
            axios({
                method: 'post',
                url: webUrl + '/user/login',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                    username: this.phone,
                    password: this.password
                }
            }).then(response => {
                if (isSuccess(response.data)) {
                    localStorage.setItem("token", response.data.data.token);
                    localStorage.setItem("user_id", response.data.data.id);
                    localStorage.setItem("username", response.data.data.username);
                    console.log(response.data.data.token);
                    window.open(this.loginSuccessUrl, '_self');
                } else {
                    this.errorHint = response.data.message;
                    // this.imgUrl = 'url(' + webUrl + '/imgVerify?' + Math.random() + ')';
                }
            }).catch(error => console.log(error));
        },
        smsLogin: function () {
            this.errorHint = '';
            if (!(/^1([34578])\d{9}$/.test(this.phone))) {
                this.errorHint = '请输入正确的11位手机号码!';
                return;
            }
            if (this.smsCode.trim().length !== 4) {
                this.errorHint = '请输入4位短信验证码!';
                return;
            }
            axios({
                method: 'post',
                url: webUrl + '/phoneLogin',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                    phone: this.phone,
                    phoneCode: this.smsCode
                }
            }).then(response => {
                if (isSuccess(response.data)) {
                    window.open(this.loginSuccessUrl, '_self');
                } else {
                    this.errorHint = response.data.responseInfo;
                    this.imgUrl = 'url(' + webUrl + '/imgVerify?' + Math.random() + ')';
                }
            }).catch(error => console.log(error));

        },
        getSmsCode: function () {
            console.log("获取短信验证码");
            this.errorHint = '';
            if (!(/^1([34578])\d{9}$/.test(this.phone))) {
                this.errorHint = '请输入正确的11位手机号码!';
                return;
            }
            axios({
                method: 'post',
                url: webUrl + '/phoneVerify',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                    phone: this.phone,
                }
            }).then(response => {
                if (isSuccess(response.data)) {
                    startSecondCnt(60, this);
                } else {
                    this.errorHint = response.data.responseInfo;
                }
            }).catch(error => console.log(error));
        },
        mouseDownEvn: function (event) {
            console.log("点击 " + event.target);
            this.mouseDown = true;

            this.mouseDownOffsetLeft = event.offsetX;
            this.mouseDownOffsetTop = event.offsetY;
        }
    }
});

let startSecondCnt = function (second, vue) {
    vue.secondCnt = second;
    vue.isBtnNormal = false;
    setTimeout(startCountDown, 1000);
};

let startCountDown = function () {
    if (--login.secondCnt === 0) {
        login.isBtnNormal = true;
    } else {
        setTimeout(startCountDown, 1000);
    }
};

document.addEventListener("mouseup", function () {
    login.mouseDown = false;
});

document.addEventListener("mousemove", function (event) {
    if (login.mouseDown) {

        let leftTemp = event.clientX - login.mouseDownOffsetLeft;
        let topTemp = event.clientY - login.mouseDownOffsetTop;

        let leftMax = document.documentElement.clientWidth - login.$el.clientWidth - 5;
        let topMax = document.documentElement.clientHeight - login.$el.clientHeight - 5;

        if (leftTemp < 5) {
            leftTemp = 5;
        } else if (leftTemp > leftMax) {
            leftTemp = leftMax;
        }

        if (topTemp < 5) {
            topTemp = 5;
        } else if (topTemp > topMax) {
            topTemp = topMax;
        }

        login.left = leftTemp;
        login.top = topTemp;

    }
});

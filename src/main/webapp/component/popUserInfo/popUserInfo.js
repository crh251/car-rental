let message_user_outer = new Vue({
    el: '#message-user-outer',
    data: {
        photoUrl: "url('" + webUrl + "/getUserPhoto" + "') 1px 1px"
    },
    methods: {
        togglePop: function () {
            pop_info.isDisplay = !pop_info.isDisplay;
            if (pop_info.isDisplay) {
                pop_info.setLeftTop();
            }
        }
    }
});

let pop_info = new Vue({
    el: '#pop-info',
    data: {
        isDisplay: false,
        left: '',
        top: '',
        phone: '',
        loginUrl: '../component/login/login.html',
        photoUrl: "url('" + webUrl + "/getUserPhoto" + "') 0px 1px",
        changePasswordUrl: ''
    },
    methods: {
        getPhone: function () {
            axios({
                method: 'get',
                url: webUrl + '/getPhone'
            }).then(response => {
                if (isSuccess(response.data)) {
                    console.log("获取手机号成功");
                    this.phone = response.data.datas[0].phone;
                } else {
                    window.open(this.loginUrl, '_self');
                }
            }).catch(error => console.log(error));
        },
        logout: function () {
            console.log("退出");
        },
        setLeftTop: function () {
            this.left = getElementLeft(message_user_outer.$el) - 125;
            this.top = getElementTop(message_user_outer.$el) + 38;
        }
    }
});

window.addEventListener("resize", function () {
    pop_info.setLeftTop();
});

window.addEventListener("click", function () {
    pop_info.isDisplay = false;
});

pop_info.getPhone();
pop_info.setLeftTop();
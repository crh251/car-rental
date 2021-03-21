let car_rental = new Vue({
    el: '#car_rental',
    data: {
        width: 300,
        left: (document.documentElement.clientWidth - 300) / 2,
        top: 100,
        user_id: '1',
        username: localStorage.getItem("username"),
        car_model_list: [],
        car_rental_list: [
            // {
            //     id: 1,
            //     userId: 1,
            //     carId: 1,
            //     carModel: '奔驰',
            //     status: 1,
            //     startTime: '2020-02-02 11:11:11',
            //     endTime: '2020-02-02 11:11:11',
            //     createTime: '2020-02-02 11:11:11',
            //     updateTime: '2020-02-02 11:11:11'
            //
            // }
        ],
        display_car_rental: false,
        loginUrl: './login.html',
        // 点击预订按钮要预订的车辆型号
        car_rental_model: '',
        // 预订的开始使用时间
        start_time: null,
        // 预订的结束使用时间
        end_time: null
    },
    methods: {
        close_car_rental_window: function (event) {
            console.log("close car rental window!")
            this.display_car_rental = false;
            this.car_rental_model = '';
        },
        open_car_rental_window: function (carModel) {

            console.log("open car rental window!");
            console.log("select car model: " + carModel);
            this.car_rental_model = carModel;
            this.display_car_rental = true;
        },
        start_car_rental: function () {
            console.log("start car rental!");

            console.log("car model: " + this.car_rental_model);
            let start_time_format = new Date(this.start_time).format("yyyy-MM-dd hh:mm:ss");
            console.log("start time: " + start_time_format);
            let end_time_format = new Date(this.end_time).format("yyyy-MM-dd hh:mm:ss");
            console.log("end time: " + end_time_format);

            axios({
                method: 'post',
                url: webUrl + '/rental/order',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                    token: localStorage.getItem("token"),
                    carModel: this.car_rental_model,
                    startTime: start_time_format,
                    endTime: end_time_format
                }
            }).then(response => {
                console.log("car rental cancel: " + JSON.stringify(response.data));

                if (isSuccess(response.data)) {
                    alert("预订成功！");
                    location.reload();
                } else {

                    alert(response.data.message);

                    if (notLogin(response.data)) {
                        doLogin();
                    }

                }
            }).catch(error => console.log(error));

        },
        cancel_car_rental: function (id) {
            console.log("cancel car rental, id: " + id);

            axios({
                method: 'post',
                url: webUrl + '/rental/cancel',
                headers: {
                    'Content-type': 'application/x-www-form-urlencoded'
                },
                params: {
                    token: localStorage.getItem("token"),
                    carRentalId: id
                }
            }).then(response => {
                console.log("car rental cancel: " + JSON.stringify(response.data));

                if (isSuccess(response.data)) {
                    alert("取消预订成功！");
                    location.reload();
                } else {

                    alert(response.data.message);

                    if (notLogin(response.data)) {
                        doLogin();
                    }

                }
            }).catch(error => console.log(error));

        }
    }
});

let doLogin = function () {
    localStorage.clear();
    window.open(car_rental.loginUrl, '_self');
}

window.onload = function () {
    console.log("already on load!");

    axios({
        method: 'get',
        url: webUrl + '/rental/list_car_model'
    }).then(response => {
        if (isSuccess(response.data)) {
            console.log("list_car_model: " + response.data.dataList);
            car_rental.car_model_list = response.data.dataList;
        } else {
            alert(response.data.message);
        }
    }).catch(error => console.log(error));


    axios({
        method: 'post',
        url: webUrl + '/rental/my_car_rental',
        headers: {
            'Content-type': 'application/x-www-form-urlencoded'
        },
        params: {
            token: localStorage.getItem("token")
        }
    }).then(response => {
        console.log("my car rental: " + JSON.stringify(response.data));

        if (isSuccess(response.data)) {
            car_rental.car_rental_list = response.data.dataList;
        } else {

            alert(response.data.message);

            if (notLogin(response.data)) {
                doLogin();
            }

        }
    }).catch(error => console.log(error));

}
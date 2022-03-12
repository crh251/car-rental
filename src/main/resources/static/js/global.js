let protocol = document.location.protocol;
let hostStr = window.location.host;

let webUrl = protocol + "//" + hostStr + "/car_rental";
// let webUrl = "https://ieasygo.top/car_rental";

let isSuccess = function (result) {
    return result.code === "0";
};

let notLogin = function (result) {
    return result.code === "1";
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };

    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }

    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(
                RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }

    return fmt;
}

// 获取网页链接的参数
let getParams = function (key) {
    let reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) {
        // console.log("匹配链接r:  " + r[2]);
        return decodeURI(r[2]);
    }
    return null;
};

let interval2time = function (interval) {
    let intervalStr = "";
    if (parseInt(interval / 60) <= 9) {
        intervalStr += "0";
    }
    intervalStr += parseInt(interval / 60) + ":";
    if (interval % 60 <= 9) {
        intervalStr += "0";
    }
    intervalStr += interval % 60;
    return intervalStr;
};

/*AJAX GET请求*/
let ajaxGet = function (url, successCallback, failCallback, type) {
    let xhr = null;
    if (window.XMLHttpRequest) {
        xhr = new window.XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    }
    xhr.addEventListener("readystatechange", function () {
        if (xhr.readyState === 4) {
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                //请求成功
                successCallback(xhr);
            } else {
                //请求失败
                if (failCallback === undefined || failCallback === null) {
                    alert("网络连接失败!");
                } else {
                    failCallback(xhr);
                }
            }
        }
    });
    xhr.withCredentials = true;
    if (type === undefined || type === null) {
        type = true;
    }
    // console.log("type " + type);
    xhr.open('get', url, type);
    // console.log(url + "url进行编码后为: " + encodeURI(url));
    xhr.send(null);
};


/*AJAX POST请求*/
let ajaxPOST = function (url, data, successCallback, failCallback) {
    let xhr = null;
    if (window.XMLHttpRequest) {
        xhr = new window.XMLHttpRequest();
    } else if (window.ActiveXObject) {
        xhr = new ActiveXObject("Msxml2.XMLHTTP");
    }
    xhr.addEventListener("readystatechange", function () {
        if (xhr.readyState === 4) {
            if ((xhr.status >= 200 && xhr.status < 300) || xhr.status === 304) {
                //请求成功
                successCallback(xhr);
            } else {
                //请求失败
                failCallback(xhr);
            }
        }
    });
    xhr.open('post', url, true);
    xhr.withCredentials = true;
    xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");
    xhr.send(data);
};

/*求出element到文档左边像素*/
let getElementLeft = function (element) {
    let actualLeft = element.offsetLeft;
    let current = element.offsetParent;
    while (current !== null) {
        actualLeft += current.offsetLeft;
        current = current.offsetParent;
    }
    return actualLeft;
};

/*求出element到文档上面像素*/
let getElementTop = function (element) {
    let actualTop = element.offsetTop;
    let current = element.offsetParent;
    while (current !== null) {
        actualTop += current.offsetTop;
        current = current.offsetParent;
    }
    return actualTop;
};
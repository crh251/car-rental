# CAR RENTAL 汽车租赁预订

项目访问链接：<https://ieasygo.top/car_rental_h5/html/rental.html>

## 方案设计

### 表结构

#### 用户信息表
```sql
CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(16) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '密码的盐',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用户信息表';
```

#### 车辆信息表
```sql
CREATE TABLE `car_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆id',
    `model` varchar(32) NOT NULL COMMENT '车辆型号',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆信息';
```

#### 车辆预订记录表
```sql
CREATE TABLE `car_rental_info` (
    `id` bigint(20) NOT NULL AUTO_INCREMENT,
    `user_id` bigint(20) NOT NULL COMMENT '用户id',
    `car_id` bigint(20) NOT NULL COMMENT '车辆id',
    `car_model` varchar(32) NOT NULL COMMENT '车辆型号',
    `end_time` datetime NOT NULL COMMENT '预订车辆结束使用时间',
    `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '预订状态。1-已预订。2-已取消。',
    `create_time` datetime NOT NULL COMMENT '创建时间',
    `start_time` datetime NOT NULL COMMENT '预订车辆开始使用时间',
    `update_time` datetime DEFAULT NULL COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_car_id` (`car_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='车辆预订记录信息';
```

### 主要流程设计

#### 车辆预订流程
1. 检查请求参数
2. 对用户id加redis锁，同一用户只能顺序拿到锁。
3. 判断用户在这个时间段内是否已经有车辆预订了，如果有则释放锁并返回冲突提示，没有则下一步。
4. 对车辆型号加redis锁，不同用户预订同种型号车辆只能同步处理预订。
5. 判断是否还有空闲的车辆可以分配给用户在这个时间段内使用，没有则释放锁并返回无可用车辆提示，有则下一步。
6. 生成车辆预订信息，并依次释放车辆型号锁和用户id锁。


#### 车辆取消预订流程
1. 对用户id加redis锁，同一用户只能顺序拿到锁。
2. 判断用户要取消的车辆预订记录是否存在，是否本人记录，是否预订成功记录，是则下一步，否则释放锁返回提示。
3. 将该车辆预订记录置为已取消状态，并释放锁。

> Redis锁方案为单机redis，框架为Redisson，每个相同用户id和车辆型号都对应唯一的key。

---

## 接口 Api

### 
**URL:** http://ieasygo.top/car_rental/user/login

**Type:** POST

**Author:** laijh25319

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 用户登录

**Query-parameters:**

Parameter | Type|Description|Required|example
---|---|---|:---:|:---:
username|string|用户名|true|t1
password|string|密码|true|123

**Request-example:**
```
curl -X POST -i http://ieasygo.top/car_rental/user/login --data 'username=t1&password=123'
```
**Response-fields:**

Field | Type|Description|example
---|---|---|---
code|string|响应编号|0
message|string|响应信息|success
data|object|登录返回用户信息|-
└─id|int64|用户id|1
└─username|string|用户名|t1
└─token|string|token|5b019ba8-71f0-41b2-849a-e678184117a9

**Response-example:**
```json
{
  "code": "0",
  "message": "success",
  "data": {
    "id": 1,
    "username": "t1",
    "token": "5b019ba8-71f0-41b2-849a-e678184117a9"
  },
  "dataList": null
}
```

### 
**URL:** http://ieasygo.top/car_rental/rental/order

**Type:** POST

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 预订车辆

**Query-parameters:**

Parameter | Type|Description|Required|example
---|---|---|---|---
token|string|token|true|5b019ba8-71f0-41b2-849a-e678184117a9
carModel|string|预订车辆型号|true|BMW 650
startTime|date|开始使用时间[yyyy-MM-dd HH:mm]，需要大于当前时间|true|2021-03-22 08:00
endTime|date|结束使用时间[yyyy-MM-dd HH:mm]，需要大于当前时间|true|2021-03-22 17:00

**Request-example:**
```
curl -X POST -i http://ieasygo.top/car_rental/rental/order --data 'token=5b019ba8-71f0-41b2-849a-e678184117a9&startTime=2021-03-22 08:00&carModel=BMW 650&endTime=2021-03-22 17:00'
```
**Response-fields:**

Field | Type|Description|example
---|---|---|:---:
code|string|响应编号|0
message|string|响应信息|success
data|object|车辆预订记录|-
└─id|int64|车辆预订记录id|1
└─userId|int64|用户id|1
└─carId|int64|车辆id|1
└─carModel|string|车辆型号|BMW 650
└─startTime|string|预订车辆开始使用时间|2021-03-22 08:00
└─endTime|string|预订车辆结束使用时间|2021-03-22 17:00
└─status|int32|预订状态。1-已预订。2-已取消。|1
└─createTime|string|创建时间|2021-03-21 18:00:00
└─updateTime|string|更新时间|-

**Response-example:**
```json
{
  "code": "0",
  "message": "success",
  "data": {
    "id": 10,
    "userId": 1,
    "carId": 1,
    "carModel": "Toyota Camry",
    "startTime": "2021-03-21 23:50:00",
    "endTime": "2021-03-21 23:55:00",
    "status": 1,
    "createTime": "2021-03-21 23:21:11",
    "updateTime": null
  },
  "dataList": null
}
```

### 
**URL:** http://ieasygo.top/rental/car_rental/cancel

**Type:** POST

**Author:** laijh25319

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 取消车辆预订

**Query-parameters:**

Parameter | Type|Description|Required|example
---|---|---|---|---
token|string|token|true|5b019ba8-71f0-41b2-849a-e678184117a9
carRentalId|int64|车辆预订记录|true|1

**Request-example:**
```
curl -X POST -i http://ieasygo.top/car_rental/rental/cancel --data 'token=5b019ba8-71f0-41b2-849a-e678184117a9&carRentalId=1'
```
**Response-fields:**

Field | Type|Description|example
---|---|---|:---:
code|string|响应编号|0
message|string|响应信息|success

**Response-example:**
```
{
    "code": "0",
    "message": "success"
}
```

### 
**URL:** http://ieasygo.top/car_rental/rental/list_car_model

**Type:** GET

**Author:** laijh25319

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 查询所有车辆型号

**Request-example:**
```
curl -X GET -i http://ieasygo.top/car_rental/rental/list_car_model
```
**Response-fields:**

Field | Type|Description|example
---|---|---|:---:
code|string|响应编号|0
message|string|响应信息|success
dataList|List|车辆型号列表|`["Toyota Camry","BMW 650"]`

**Response-example:**
```json
{
    "code": "0",
    "message": "success",
    "data": null,
    "dataList": [
        "Toyota Camry",
        "BMW 650"
    ]
}
```

### 
**URL:** http://ieasygo.top/car_rental/rental/my_car_rental

**Type:** POST

**Author:** laijh25319

**Content-Type:** application/x-www-form-urlencoded;charset=utf-8

**Description:** 查询我的车辆预订信息列表

**Query-parameters:**

Parameter | Type|Description|Required|example
---|---|---|---|---
token|string|token|false|5b019ba8-71f0-41b2-849a-e678184117a9

**Request-example:**
```
curl -X POST -i http://ieasygo.top/car_rental/rental/my_car_rental --data 'token=5b019ba8-71f0-41b2-849a-e678184117a9'
```
**Response-fields:**

Field | Type|Description|example
---|---|---|---
code|string|响应编号|0
message|string|响应信息|success
dataList|object|车辆预订记录列表|-
└─id|int64|车辆预订记录id|1
└─userId|int64|用户id|1
└─carId|int64|车辆id|1
└─carModel|string|车辆型号|BMW 650
└─startTime|string|预订车辆开始使用时间|2021-03-22 08:00
└─endTime|string|预订车辆结束使用时间|2021-03-22 17:00
└─status|int32|预订状态。1-已预订。2-已取消。|1
└─createTime|string|创建时间|2021-03-21 18:00:00
└─updateTime|string|更新时间|2021-03-21 18:10:05

**Response-example:**
```json
{
    "code": "0",
    "message": "success",
    "data": null,
    "dataList": [
        {
            "id": 10,
            "userId": 1,
            "carId": 1,
            "carModel": "Toyota Camry",
            "startTime": "2021-03-21 23:50:00",
            "endTime": "2021-03-21 23:55:00",
            "status": 1,
            "createTime": "2021-03-21 23:21:11",
            "updateTime": null
        }
    ]
}
```




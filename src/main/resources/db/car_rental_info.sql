CREATE TABLE `car_rental_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `car_id` bigint(20) NOT NULL COMMENT '车辆id',
  `car_model` varchar(32) NOT NULL COMMENT '车辆型号',
  `end_time` datetime NOT NULL COMMENT '预订车辆结束使用时间',
  `status` tinyint(4) NOT NULL DEFAULT 1 COMMENT '预订状态\n1-已预订\n2-已取消',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `start_time` datetime NOT NULL COMMENT '预订车辆开始使用时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_car_id` (`car_id`),
  KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb3 COMMENT='车辆租赁预订信息';

insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-21 12:19:15', '2021-03-21 13:25:00', '1', '2021-03-21 13:24:00', 2, '2021-03-21 12:46:05', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-21 12:31:20', '2021-03-21 13:45:00', '2', '2021-03-21 13:40:00', 2, '2021-03-21 12:46:43', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 12:35:28', '2021-03-21 13:40:00', '3', '2021-03-21 13:39:00', 2, '2021-03-21 12:46:47', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 12:35:39', '2021-03-21 13:39:00', '4', '2021-03-21 13:38:00', 2, '2021-03-21 20:04:51', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 20:25:51', '2021-03-22 08:10:00', '5', '2021-03-22 08:00:00', 2, '2021-03-21 20:30:48', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 20:35:19', '2021-03-22 20:36:00', '6', '2021-03-22 20:35:00', 1, NULL, '2');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 20:42:23', '2021-03-22 20:44:00', '7', '2021-03-22 20:43:00', 1, NULL, '3');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-21 20:49:00', '2021-03-22 21:50:00', '8', '2021-03-22 20:00:00', 2, '2021-03-22 10:14:28', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 23:20:51', '2021-03-21 23:50:00', '9', '2021-03-21 23:25:00', 2, '2021-03-22 10:14:28', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 23:21:11', '2021-03-21 23:55:00', '10', '2021-03-21 23:50:00', 2, '2021-03-21 18:03:12', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-21 18:20:34', '2021-03-22 10:00:00', '11', '2021-03-22 08:00:00', 1, NULL, '2');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 02:27:18', '2021-03-22 18:10:00', '12', '2021-03-22 18:00:00', 2, '2021-03-22 03:03:18', '2');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 02:49:15', '2021-03-22 10:10:00', '13', '2021-03-22 10:00:00', 1, NULL, '2');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 02:49:39', '2021-03-22 23:10:00', '14', '2021-03-22 23:00:00', 1, NULL, '2');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('3', 'Toyota Camry', '2021-03-22 09:02:22', '2021-03-22 09:10:00', '15', '2021-03-22 09:05:00', 2, '2021-03-22 10:11:28', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('3', 'Toyota Camry', '2021-03-22 09:32:07', '2021-03-22 09:50:00', '16', '2021-03-22 09:35:00', 1, NULL, '3');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 03:56:20', '2021-03-22 12:10:00', '17', '2021-03-22 12:00:00', 1, NULL, '20');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('3', 'Toyota Camry', '2021-03-22 05:42:05', '2021-03-26 13:41:00', '18', '2021-03-22 13:41:00', 2, '2021-03-22 05:42:17', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 06:08:00', '2021-03-23 08:10:00', '19', '2021-03-23 08:00:00', 1, NULL, '15');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-22 06:12:25', '2021-03-23 08:20:00', '20', '2021-03-23 08:10:00', 1, NULL, '15');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-22 14:15:35', '2021-03-23 08:25:00', '21', '2021-03-23 08:20:00', 1, NULL, '15');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('3', 'Toyota Camry', '2021-03-22 14:17:37', '2021-03-23 08:10:00', '22', '2021-03-23 08:00:00', 1, NULL, '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-22 14:18:27', '2021-03-23 08:20:00', '23', '2021-03-23 08:10:00', 1, NULL, '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-23 09:33:02', '2021-03-23 10:10:00', '24', '2021-03-23 10:00:00', 2, '2021-03-24 15:15:09', '1');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('2', 'BMW 650', '2021-03-24 15:49:07', '2021-03-24 16:10:00', '25', '2021-03-24 16:00:00', 1, NULL, '10');
insert into `car_rental_info` (`car_id`, `car_model`, `create_time`, `end_time`, `id`, `start_time`, `status`, `update_time`, `user_id`) values ('1', 'Toyota Camry', '2021-03-24 15:49:20', '2021-03-26 15:48:00', '26', '2021-03-24 17:48:00', 1, NULL, '1');

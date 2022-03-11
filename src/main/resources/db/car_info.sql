CREATE TABLE `car_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '车辆id',
  `model` varchar(32) NOT NULL COMMENT '车辆型号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COMMENT='车辆信息';

insert into `car_info` (`id`, `model`) values ('1', 'Toyota Camry');
insert into `car_info` (`id`, `model`) values ('2', 'BMW 650');
insert into `car_info` (`id`, `model`) values ('3', 'Toyota Camry');
insert into `car_info` (`id`, `model`) values ('4', 'BMW 650');

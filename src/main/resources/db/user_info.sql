CREATE TABLE `user_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `username` varchar(16) NOT NULL COMMENT '用户名',
  `password` varchar(255) NOT NULL COMMENT '密码',
  `salt` varchar(255) NOT NULL COMMENT '密码的盐',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_user_name` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3 COMMENT='用户信息表';

insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('1', '2021-03-31 13:32:14', 'afff00d9abee8bc89fed9d3573612046', '36cb6023-e54b-4c13-abbb-db16db5bbb29', 't1');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('2', NULL, '5358efe77fe298d58c80fd08da8f7f25', '90cd0c28-d7e2-453e-adc3-37de7ac79211', 't2');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('3', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't3');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('4', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't4');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('5', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't5');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('6', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't6');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('7', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't7');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('8', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't8');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('9', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't9');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('10', '2021-03-26 16:04:33', 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't10');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('11', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't11');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('12', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't12');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('13', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't13');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('14', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't14');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('15', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't15');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('16', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't16');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('17', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't17');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('18', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't18');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('19', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't19');
insert into `user_info` (`id`, `last_login_time`, `password`, `salt`, `username`) values ('20', NULL, 'ca6dc0be7d64d20a21ea51a55764e522', '0d1d0668-7823-44a1-8f40-cfd3c7bb9bfd', 't20');

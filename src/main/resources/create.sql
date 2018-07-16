CREATE TABLE `schedule_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `schedule_name` varchar(50) NOT NULL COMMENT '名称',
  `next_exec_time` datetime DEFAULT NULL COMMENT '下次执行时间',
  `bean_name` varchar(50) NOT NULL COMMENT 'beanName',
  `task_parameter` varchar(100) DEFAULT NULL COMMENT '任务的参数',
  `exec_plan` varchar(50) NOT NULL COMMENT '执行计划',
  `last_exec_time` datetime DEFAULT NULL COMMENT '上次执行时间',
  `status` tinyint(4) NOT NULL COMMENT '状态 0:已停止 1:待执行 2:执行中 3:已完成',
  `locker` varchar(100) DEFAULT NULL COMMENT '加锁的所有者',
  `error_msg` varchar(100) DEFAULT NULL COMMENT '错误信息',
  `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `schedule_name` (`schedule_name`),
  KEY `next_exec_time` (`next_exec_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='任务调度器';
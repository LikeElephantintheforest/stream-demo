#### 数据表相关语句

---

- 班级表

```sql
CREATE TABLE `t_test_class`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `name`        varchar(32) NOT NULL COMMENT '名称',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) COMMENT '主键'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='班级表';
```

- 爱好表

```sql
CREATE TABLE `t_test_hobby`
(
    `id`          bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `desc`        varchar(32) NOT NULL COMMENT '描述',
    `create_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time` timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) COMMENT '主键'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='爱好';
```

- 学生表

```sql
CREATE TABLE `t_test_student`
(
    `id`               bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '自增id',
    `class_id`         bigint(20) unsigned NOT NULL COMMENT '班级id',
    `hobby_id`         bigint(20) unsigned NOT NULL COMMENT '兴趣班id',
    `age`              int(11) unsigned NOT NULL COMMENT '年龄',
    `last_attend_time` timestamp NULL DEFAULT NULL COMMENT '最后一次参加兴趣班时间',
    `create_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modify_time`      timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`) COMMENT '主键'
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COMMENT='学生表';
```
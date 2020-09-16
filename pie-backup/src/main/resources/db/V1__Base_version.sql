
CREATE TABLE `sys_user` (
`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
`name` VARCHAR(50) NOT NULL COMMENT '用户名',
`nick_name` VARCHAR(150) DEFAULT NULL COMMENT '昵称',
`avatar_id` BIGINT(20) DEFAULT NULL COMMENT '头像',
`password` VARCHAR(150) DEFAULT NULL COMMENT '密码',
`salt` VARCHAR(40) DEFAULT NULL COMMENT '加密盐',
`email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
`mobile` VARCHAR(100) DEFAULT NULL COMMENT '手机号',
`status` TINYINT(4) DEFAULT NULL COMMENT '状态 0：禁用 1：正常',
`dept_id` BIGINT(20) DEFAULT NULL COMMENT '部门id',
`create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
`last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
`del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
PRIMARY KEY (`id`),
UNIQUE KEY `name` (`name`)

)
ENGINE=InnoDB auto_increment=34 default charset=utf8 COMMENT '用户管理';


CREATE TABLE `sys_role`(
`id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
`name` VARCHAR(100) DEFAULT NULL COMMENT '角色名称',
`org_id` BIGINT(20) DEFAULT NULL COMMENT '机构id',
`remark` VARCHAR(100) DEFAULT NULL COMMENT '备注',
`create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
`last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
`del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=9 default charset=utf8 COMMENT '角色管理';


CREATE TABLE `sys_dept`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '部门名称',
  `parent_id` BIGINT(20) DEFAULT NULL  COMMENT '上级机构ID，一级机构为0',
  `order_num` INT(11) DEFAULT NULL  COMMENT '排序',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
	 )
ENGINE=InnoDB auto_increment=36 default charset=utf8 COMMENT '部门表';

CREATE TABLE  `sys_org`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   `name` VARCHAR(50) DEFAULT NULL COMMENT '机构名称',
   `status` TINYINT(4) DEFAULT NULL COMMENT '状态 0：禁用 1：正常',
   `order_num` INT(11) DEFAULT NULL  COMMENT '排序',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=36 default charset=utf8 COMMENT '机构表';

CREATE TABLE  `sys_org_dept`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   `org_id` BIGINT(20) DEFAULT NULL  COMMENT '机构ID',
   `dept_id` BIGINT(20) DEFAULT NULL  COMMENT '部门ID',
   `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
   `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
   `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
    PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '机构部门';

CREATE TABLE  `sys_org_menu`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   `org_id` BIGINT(20) DEFAULT NULL  COMMENT '机构ID',
   `menu_id` BIGINT(20) DEFAULT NULL  COMMENT '菜单ID',
   `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
   `create_time` datetime DEFAULT NULL COMMENT '创建时间',
   `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
   `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
   `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
    PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '机构权限';

CREATE TABLE `sys_menu`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` VARCHAR(50) DEFAULT NULL COMMENT '菜单名称',
  `parent_id` BIGINT(20) DEFAULT NULL  COMMENT '父菜单ID，一级机构为0',
  `url` VARCHAR(200) DEFAULT NULL COMMENT '菜单URL，类型：1.普通页面（如用户管理/sys/user）2.嵌套完整外部页面，以http（s）开头的连接。3.嵌套服务器页面，
  使用iframe：前缀+目标URL（如SQL监控，iframe：/druid/login.html,iframe:前缀会替换成服务器地址）',
  `perms` VARCHAR(200) DEFAULT NULL COMMENT '授权（多个用逗号分隔，如：sys:user:add,sys:user:edit)',
  `type` INT(11) DEFAULT NULL  COMMENT '类型：0：目录 1：菜单 2：按钮',
  `icon` VARCHAR(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` INT(11) DEFAULT NULL  COMMENT '排序',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '菜单管理';


CREATE TABLE `sys_user_role`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` BIGINT(20) DEFAULT NULL  COMMENT '用户ID',
  `role_id` BIGINT(20) DEFAULT NULL  COMMENT '角色ID',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '用户角色';


CREATE TABLE `sys_role_menu`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` BIGINT(20) DEFAULT NULL  COMMENT '角色ID',
  `menu_id` BIGINT(20) DEFAULT NULL  COMMENT '角色ID',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '角色菜单';


CREATE TABLE `sys_role_dept`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` BIGINT(20) DEFAULT NULL  COMMENT '角色ID',
  `dept_id` BIGINT(20) DEFAULT NULL  COMMENT '部门ID',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '角色机构';


CREATE TABLE `sys_dict`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` VARCHAR(100) NOT NULL COMMENT '数据值',
  `label` VARCHAR(100) NOT NULL COMMENT '标签名',
  `type` VARCHAR(100) NOT NULL COMMENT '类型',
  `description` VARCHAR(100) NOT NULL COMMENT '描述',
  `sort` DECIMAL(10,0) NOT NULL COMMENT '排序（升序）',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '字典表';


CREATE TABLE `sys_config`(
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `value` VARCHAR(100) NOT NULL COMMENT '数据值',
  `label` VARCHAR(100) NOT NULL COMMENT '标签名',
  `type` VARCHAR(100) NOT NULL COMMENT '类型',
  `description` VARCHAR(100) NOT NULL COMMENT '描述',
  `sort` DECIMAL(10,0) NOT NULL COMMENT '排序（升序）',
  `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
  `remarks` VARCHAR(255)  DEFAULT NULL COMMENT '备注信息',
  `del_flag` TINYINT(4) DEFAULT '0' COMMENT'是否删除 -1：已删除 0：正常',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=4 default charset=utf8 COMMENT '系统配置表';

CREATE TABLE `sys_log`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
   `operation` VARCHAR(50) DEFAULT NULL COMMENT '用户操作',
   `method` VARCHAR(200) DEFAULT NULL COMMENT '请求方法',
   `params` VARCHAR(5000) DEFAULT NULL COMMENT '请求参数',
   `time` BIGINT(20) NOT NULL COMMENT '持续时长（毫秒）',
   `ip` VARCHAR(64) DEFAULT NULL COMMENT 'IP地址',
   `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '系统操作日志';

CREATE TABLE `sys_login_log`(
   `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
   `user_name` VARCHAR(50) DEFAULT NULL COMMENT '用户名',
   `status` VARCHAR(50) DEFAULT NULL COMMENT '登录状态（online：在线，登录初始状态，方便统计在线人数：login：退出登录之后将online置为login；logout：退出登录）',
   `ip` VARCHAR(64) DEFAULT NULL COMMENT 'IP地址',
   `create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
  `last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
   PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '系统登录日志';


CREATE TABLE `sys_attachments`(
 `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
`file_name` VARCHAR(50) DEFAULT NULL COMMENT '文件名称',
`uuid` VARCHAR(100) DEFAULT NULL COMMENT 'uuid',
`file_path` VARCHAR(255) DEFAULT NULL COMMENT '文件路径',
`file_size` BIGINT(20) DEFAULT NULL COMMENT '文件大小',
`file_type` VARCHAR(50) DEFAULT NULL COMMENT '文件类型',
`description` VARCHAR(100) DEFAULT NULL COMMENT '描述',
`create_by` VARCHAR(50) DEFAULT NULL COMMENT '创建人',
`create_time` datetime DEFAULT NULL COMMENT '创建时间',
`last_update_by` VARCHAR(50) DEFAULT NULL COMMENT '更新人',
`last_update_time`  datetime DEFAULT NULL COMMENT '更新时间',
PRIMARY KEY (`id`)
)
ENGINE=InnoDB auto_increment=1 default charset=utf8 COMMENT '系统附件表';
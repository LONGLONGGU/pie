INSERT INTO `sys_menu` VALUES (1, '系统管理', 0, '/sys', NULL, 'sys:menu:view', 0, 'el-icon-setting', 0, 'admin', '2020-03-09 11:12:04', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (2, '用户管理', 1, '/sys/user', NULL, NULL, 1, 'el-icon-service', 0, 'admin', '2020-03-09 11:15:04', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (3, '部门管理', 1, '/sys/dept', NULL, NULL, 1, 'el-icon-news', 1, 'admin', '2020-03-09 11:16:45', NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (4, '角色管理', 1, '/sys/role', NULL, NULL, 1, 'el-icon-view', 2, 'admin', '2020-03-09 11:18:27', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (5, '菜单管理', 1, '/sys/menu', NULL, NULL, 1, 'el-icon-menu', 3, 'admin', '2020-03-09 11:19:54', NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (8, '查看', 5, '', NULL, 'sys:menu:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (9, '新增', 5, '', NULL, 'sys:menu:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (10, '修改', 5, '', NULL, 'sys:menu:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (11, '查看', 3, '', NULL, 'sys:dept:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (12, '新增', 3, '', NULL, 'sys:dept:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (13, '修改', 3, '', NULL, 'sys:dept:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (14, '查询', 4, '', NULL, 'sys:role:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (15, '新增', 4, '', NULL, 'sys:role:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (16, '修改', 4, '', NULL, 'sys:role:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (17, '查看', 2, '', NULL, 'sys:user:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (18, '新增', 2, '', NULL, 'sys:user:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (19, '修改', 2, '', NULL, 'sys:user:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (20, '接口文档', 0, '/swagger', 'http://localhost:8081/swagger-ui.html', '', 1, 'el-icon-tickets', 2, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (21, '机构管理', 1, '/sys/org', NULL, '', 1, 'el-icon-s-cooperation', 1, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (22, '数据字典', 1, '/sys/dict', NULL, '', 1, 'el-icon-notebook-1', 4, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (23, '查看', 22, '', NULL, 'sys:dict:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (24, '新增', 22, '', NULL, 'sys:dict:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (25, '修改', 22, '', NULL, 'sys:dict:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (26, '删除', 2, '', NULL, 'sys:user:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (27, '删除', 3, '', NULL, 'sys:dept:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (28, '删除', 4, '', NULL, 'sys:role:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (29, '删除', 5, '', NULL, 'sys:menu:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (30, '删除', 22, '', NULL, 'sys:dict:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (31, '系统日志', 1, '/sys/log', NULL, '', 1, 'el-icon-c-scale-to-original', 5, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (32, '查询', 21, '', NULL, 'sys:org:view', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (33, '新增', 21, '', NULL, 'sys:org:add', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (34, '修改', 21, '', NULL, 'sys:org:edit', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (35, '删除', 21, '', NULL, 'sys:org:delete', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (36, '登录日志', 1, '/sys/loginlog', NULL, '', 1, 'el-icon-loading', 6, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (37, '在线用户', 1, '/sys/online', NULL, '', 1, 'el-icon-view', 6, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (38, '系统监控', 0, '/monitoring', NULL, '', 0, 'el-icon-view', 2, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (39, '数据监控', 38, '/monitoring/druid', 'http://localhost:8081/druid/login.html', '', 1, 'el-icon-warning', 1, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (40, '服务监控', 38, '/monitoring/service', 'http://localhost:8000/#/applications', '', 1, 'el-icon-view', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (41, '注册中心', 38, '/monitoring/consul', 'http://localhost:8500/', '', 1, 'el-icon-view', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (43, '上传', 2, '', NULL, 'sys:file:upload', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (44, '下载', 2, '', NULL, 'sys:file:download', 2, '', 0, NULL, NULL, NULL, NULL, 0, NULL);
INSERT INTO `sys_menu` VALUES (45, '代码生成', 0, '/codegenerator/index', NULL, 'sys:codegenerator:view', 1, 'el-icon-sunrise', 4, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (46, '数据备份', 1, '/sys/backup', '', '', 1, 'el-icon-coin', 7, NULL, NULL, NULL, NULL, 0, 0);
INSERT INTO `sys_menu` VALUES (47, '附件管理', 1, '/sys/attachments', '', 'sys:attachments:view', 1, 'el-icon-receiving', 6, NULL, NULL, NULL, NULL, 0, 0);

INSERT INTO `sys_dept` VALUES (1, '系统管理', NULL, 1, NULL, NULL, NULL, NULL, 0);

INSERT INTO `sys_org` VALUES (1, 'admin', 1, 1, NULL, NULL, NULL, NULL, 0);

INSERT INTO `sys_org_dept` VALUES (1, 1, 1, 'admin', '2020-03-13 11:34:30', NULL, NULL, 0);
INSERT INTO `sys_org_dept` VALUES (2, 1, 2, NULL, NULL, NULL, NULL, 0);

INSERT INTO `sys_role` VALUES (3, 'SuperAdmin',1,'SuperAdmin','SuperAdmin', NULL, NULL, NULL, 0);

INSERT INTO `sys_user` VALUES (4, 'SuperAdmin', NULL, NULL, 'f52fb8fd19f51f17a3346099f27ca84f', '90fb2441b845424db163', 'SuperAdmin@qq.com', '15391389897', 1, 1, 'SuperAdmin', '2020-03-20 16:01:38', NULL, NULL, 0);

INSERT INTO `sys_user_role` VALUES (1, 4, 3, 'SuperAdmin', '2020-03-15 11:42:45', NULL, NULL, 0);

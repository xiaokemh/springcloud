-- 初始化数据
-- 角色
insert into SYS_ROLE (ID, ROLE_NAME, ROLE_DESCRIBE, ROLE_STATUS)
values ('1', '管理员', '系统维护', 1);
-- 用户 密码默认 123456
insert into SYS_USER (ID, USERNAME, PASSWORD, HEADPIC, ISVALID, CREATETIME, CREATOR, PHONE, ORG_ID, USERALIAS)
values ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, 1, '2019-01-01 00:00:00', 'admin', '123456789', '1', '管理员');
-- 用户角色关联
insert into SYS_USER_ROLE (ID, ROLE_ID, USER_ID)
values ('1', '1', '1');
-- 字典
insert into SYS_DICT (DICT_ID, DICT_KEY, DICT_NAME, DICT_FID, ISVALID, DICT_SORTS, REMARK)
values ('0', 'Root', '根字典', '--', 1, 1, '父级根字典');
-- 组织部门
insert into SYS_ORGANIZATION_INFO (ORG_ID, ORG_NAME, ORG_TYPE, ORG_FID, ORG_CONTACTS, ORG_PHONE, CREATOR, CREATE_TIME)
values ('0', '根组织', 'rootOrg', '--', '张三', '123456789', 'admin','2019-01-01 00:00:00');
insert into SYS_ORGANIZATION_INFO (ORG_ID, ORG_NAME, ORG_TYPE, ORG_FID, ORG_CONTACTS, ORG_PHONE, CREATOR, CREATE_TIME)
values ('1', '一级部门', 'type', '0', 'admin', '123456789', 'admin','2019-01-01 00:00:00');
-- 菜单
INSERT INTO `sys_menu_info` VALUES ('0', '根菜单', '--', 'type', 'http://www.geostar.com.cn/', 0, '根菜单', 1, 'todo', 1, 'string', '2019-1-1 00:00:00');
INSERT INTO `sys_menu_info` VALUES ('05e7bae5-bb22-477e-9c8a-ae5a3b1291c1', '用户管理', '9a978252-cc8c-4ec0-8666-6dcb30d733f3', '', 'user', 2, '', 3, '&#xe686;', 1, 'font', '2019-5-17 14:15:48');
INSERT INTO `sys_menu_info` VALUES ('0ed780b5-9c37-42c7-9269-987ccf806202', '组织管理', '9a978252-cc8c-4ec0-8666-6dcb30d733f3', '', 'personnel', 1, '', 3, '&#xe683;', 1, 'font', '2019-5-17 14:15:41');
INSERT INTO `sys_menu_info` VALUES ('1173e4fd-7f24-4bce-bd9a-d4dba5d6840b', '角色管理', '11964093-0bb2-4951-bed5-0eb10e8d28f1', '', 'role', 1, '', 3, '&#xe610;', 1, 'font', '2019-5-16 14:54:27');
INSERT INTO `sys_menu_info` VALUES ('11964093-0bb2-4951-bed5-0eb10e8d28f1', '角色菜单管理', '2421e28e-3818-4bfd-a180-9f49145db929', '', 'roleMenu', 6, '', 3, '&#xe671;', 1, 'font', '2019-5-17 14:11:20');
INSERT INTO `sys_menu_info` VALUES ('2421e28e-3818-4bfd-a180-9f49145db929', '菜单', '0', '2', '测试', 2, '测试', 2, '&#xe64f;', 1, 'font', '2019-3-27 18:50:39');
INSERT INTO `sys_menu_info` VALUES ('5134e6ac-22ab-4ac2-ace0-fc9d23c89314', '字典管理', '804e4341-f67e-4c19-8b3d-b950c38b87f1', '', 'dict-list', 1, '', 3, '&#xe680;', 1, 'font', '2019-5-16 14:58:10');
INSERT INTO `sys_menu_info` VALUES ('593d9c4c-57d5-45c0-b1ad-64f23beb7575', '系统配置', 'c2f5c67a-263b-4994-a6e1-774eb80f5a44', '', 'system', 3, '', 3, '&#xe678;', 1, 'font', '2019-5-16 15:11:08');
INSERT INTO `sys_menu_info` VALUES ('804e4341-f67e-4c19-8b3d-b950c38b87f1', '数据字典管理', '2421e28e-3818-4bfd-a180-9f49145db929', '', 'dict', 7, '', 3, '&#xe680;', 1, 'font', '2019-5-17 14:11:41');
INSERT INTO `sys_menu_info` VALUES ('8721ab95-eb0a-4351-b42f-1785fc6f7673', '菜单管理', '11964093-0bb2-4951-bed5-0eb10e8d28f1', '', 'menu', 2, '', 3, '&#xe671;', 1, 'font', '2019-5-16 14:54:46');
INSERT INTO `sys_menu_info` VALUES ('9a978252-cc8c-4ec0-8666-6dcb30d733f3', '组织机构管理', '2421e28e-3818-4bfd-a180-9f49145db929', '', 'org', 5, '', 3, '&#xe683;', 1, 'font', '2019-5-17 14:11:04');
INSERT INTO `sys_menu_info` VALUES ('c2f5c67a-263b-4994-a6e1-774eb80f5a44', '配置管理', '2421e28e-3818-4bfd-a180-9f49145db929', '', 'config', 9, '', 3, '&#xe678;', 1, 'font', '2019-5-17 14:12:04');

-- 菜单角色关联
INSERT INTO `sys_role_menu` VALUES ('1', '1', '0');
INSERT INTO `sys_role_menu` VALUES ('10', '1', '8721ab95-eb0a-4351-b42f-1785fc6f7673');
INSERT INTO `sys_role_menu` VALUES ('11', '1', '9a978252-cc8c-4ec0-8666-6dcb30d733f3');
INSERT INTO `sys_role_menu` VALUES ('12', '1', 'c2f5c67a-263b-4994-a6e1-774eb80f5a44');
INSERT INTO `sys_role_menu` VALUES ('2', '1', '05e7bae5-bb22-477e-9c8a-ae5a3b1291c1');
INSERT INTO `sys_role_menu` VALUES ('3', '1', '0ed780b5-9c37-42c7-9269-987ccf806202');
INSERT INTO `sys_role_menu` VALUES ('4', '1', '1173e4fd-7f24-4bce-bd9a-d4dba5d6840b');
INSERT INTO `sys_role_menu` VALUES ('5', '1', '11964093-0bb2-4951-bed5-0eb10e8d28f1');
INSERT INTO `sys_role_menu` VALUES ('6', '1', '2421e28e-3818-4bfd-a180-9f49145db929');
INSERT INTO `sys_role_menu` VALUES ('7', '1', '5134e6ac-22ab-4ac2-ace0-fc9d23c89314');
INSERT INTO `sys_role_menu` VALUES ('8', '1', '593d9c4c-57d5-45c0-b1ad-64f23beb7575');
INSERT INTO `sys_role_menu` VALUES ('9', '1', '804e4341-f67e-4c19-8b3d-b950c38b87f1');


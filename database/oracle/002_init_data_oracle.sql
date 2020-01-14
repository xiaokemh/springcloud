-- 初始化数据
-- 角色
insert into SYS_ROLE (ID, ROLE_NAME, ROLE_DESCRIBE, ROLE_STATUS)
values ('1', '管理员', '系统维护', 1);
-- 用户 密码默认 123456
insert into SYS_USER (ID, USERNAME, PASSWORD, HEADPIC, ISVALID, CREATETIME, CREATOR, PHONE, ORG_ID, USERALIAS)
values ('1', 'admin', 'e10adc3949ba59abbe56e057f20f883e', null, 1, to_date('01-01-2019 00:00:00', 'dd-mm-yyyy hh24:mi:ss'), 'admin', '123456789', '1', '管理员');
-- 用户角色关联
insert into SYS_USER_ROLE (ID, ROLE_ID, USER_ID)
values ('1', '1', '1');
-- 字典
insert into SYS_DICT (DICT_ID, DICT_KEY, DICT_NAME, DICT_FID, ISVALID, DICT_SORTS, REMARK)
values ('0', 'Root', '根字典', '--', 1, 1, '父级根字典');
-- 组织部门
insert into SYS_ORGANIZATION_INFO (ORG_ID, ORG_NAME, ORG_TYPE, ORG_FID, ORG_CONTACTS, ORG_PHONE, CREATOR, CREATE_TIME)
values ('0', '根组织', 'rootOrg', '--', '张三', '123456789', 'admin', to_date('01-01-2019 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_ORGANIZATION_INFO (ORG_ID, ORG_NAME, ORG_TYPE, ORG_FID, ORG_CONTACTS, ORG_PHONE, CREATOR, CREATE_TIME)
values ('1', '一级部门', 'type', '0', 'admin', '123456789', 'admin', to_date('01-01-2019 00:00:00', 'dd-mm-yyyy hh24:mi:ss'));
-- 菜单
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('2421e28e-3818-4bfd-a180-9f49145db929', '菜单', '0', '2', '测试', 2, '测试', 2, ''||'&'||'#xe64f;', 1, 'font', to_date('27-03-2019 18:50:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('05e7bae5-bb22-477e-9c8a-ae5a3b1291c1', '用户管理', '9a978252-cc8c-4ec0-8666-6dcb30d733f3', null, 'user', 2, null, 3, ''||'&'||'#xe686;', 1, 'font', to_date('17-05-2019 14:15:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('0ed780b5-9c37-42c7-9269-987ccf806202', '组织管理', '9a978252-cc8c-4ec0-8666-6dcb30d733f3', null, 'personnel', 1, null, 3, ''||'&'||'#xe683;', 1, 'font', to_date('17-05-2019 14:15:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('1173e4fd-7f24-4bce-bd9a-d4dba5d6840b', '角色管理', '11964093-0bb2-4951-bed5-0eb10e8d28f1', null, 'role', 1, null, 3, ''||'&'||'#xe610;', 1, 'font', to_date('16-05-2019 14:54:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('11964093-0bb2-4951-bed5-0eb10e8d28f1', '角色菜单管理', '2421e28e-3818-4bfd-a180-9f49145db929', null, 'roleMenu', 6, null, 3, ''||'&'||'#xe671;', 1, 'font', to_date('17-05-2019 14:11:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('5134e6ac-22ab-4ac2-ace0-fc9d23c89314', '字典管理', '804e4341-f67e-4c19-8b3d-b950c38b87f1', null, 'dict-list', 1, null, 3, ''||'&'||'#xe680;', 1, 'font', to_date('16-05-2019 14:58:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('593d9c4c-57d5-45c0-b1ad-64f23beb7575', '系统配置', 'c2f5c67a-263b-4994-a6e1-774eb80f5a44', null, 'system', 3, null, 3, ''||'&'||'#xe678;', 1, 'font', to_date('16-05-2019 15:11:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('804e4341-f67e-4c19-8b3d-b950c38b87f1', '数据字典管理', '2421e28e-3818-4bfd-a180-9f49145db929', null, 'dict', 7, null, 3, ''||'&'||'#xe680;', 1, 'font', to_date('17-05-2019 14:11:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('8721ab95-eb0a-4351-b42f-1785fc6f7673', '菜单管理', '11964093-0bb2-4951-bed5-0eb10e8d28f1', null, 'menu', 2, null, 3, ''||'&'||'#xe671;', 1, 'font', to_date('16-05-2019 14:54:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('9a978252-cc8c-4ec0-8666-6dcb30d733f3', '组织机构管理', '2421e28e-3818-4bfd-a180-9f49145db929', null, 'org', 5, null, 3, ''||'&'||'#xe683;', 1, 'font', to_date('17-05-2019 14:11:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('c2f5c67a-263b-4994-a6e1-774eb80f5a44', '配置管理', '2421e28e-3818-4bfd-a180-9f49145db929', null, 'config', 9, null, 3, ''||'&'||'#xe678;', 1, 'font', to_date('17-05-2019 14:12:00', 'dd-mm-yyyy hh24:mi:ss'));
insert into SYS_MENU_INFO (MENU_ID, MENU_NAME, MENU_PID, MENU_TYPE, MENU_URL, MENU_SORTS, MENU_REMARK, MENU_LEVELS, MENU_ICON, ISVALID, ICON_TYPE, CREATETIME)
values ('0', '根菜单', '--', 'type', 'http://www.geostar.com.cn/', 0, '根菜单', 0, 'todo', 1, 'string', to_date('01-01-2019', 'dd-mm-yyyy'));

-- 菜单角色关联
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('10', '1', '8721ab95-eb0a-4351-b42f-1785fc6f7673');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('11', '1', '9a978252-cc8c-4ec0-8666-6dcb30d733f3');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('12', '1', 'c2f5c67a-263b-4994-a6e1-774eb80f5a44');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('2', '1', '05e7bae5-bb22-477e-9c8a-ae5a3b1291c1');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('3', '1', '0ed780b5-9c37-42c7-9269-987ccf806202');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('4', '1', '1173e4fd-7f24-4bce-bd9a-d4dba5d6840b');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('5', '1', '11964093-0bb2-4951-bed5-0eb10e8d28f1');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('6', '1', '2421e28e-3818-4bfd-a180-9f49145db929');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('7', '1', '5134e6ac-22ab-4ac2-ace0-fc9d23c89314');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('8', '1', '593d9c4c-57d5-45c0-b1ad-64f23beb7575');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('9', '1', '804e4341-f67e-4c19-8b3d-b950c38b87f1');
insert into SYS_ROLE_MENU (ID, ROLE_ID, MENU_ID)
values ('1', '1', '0');
Commit;
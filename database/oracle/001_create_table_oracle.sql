-- 组织表
CREATE TABLE SYS_ORGANIZATION_INFO(
  ORG_ID VARCHAR2(50) NOT NULL,
  ORG_NAME VARCHAR2(50) NOT NULL,
  ORG_TYPE VARCHAR2(50) ,
  ORG_FID VARCHAR2(50) NOT NULL ,
  ORG_CONTACTS VARCHAR2(50) NOT NULL,
  ORG_PHONE VARCHAR2(50),
  CREATOR VARCHAR2(50) NOT NULL,
  CREATE_TIME date NOT NULL,
  PRIMARY KEY (ORG_ID)
);
-- Add comments to the table
comment on table SYS_ORGANIZATION_INFO
  is '组织信息表';
-- Add comments to the columns
comment on column SYS_ORGANIZATION_INFO.ORG_ID
  is '组织主键Id';
comment on column SYS_ORGANIZATION_INFO.ORG_NAME
  is '组织名称';
comment on column SYS_ORGANIZATION_INFO.ORG_TYPE
  is '组织类别';
comment on column SYS_ORGANIZATION_INFO.ORG_FID
  is '上级部门id';
comment on column SYS_ORGANIZATION_INFO.ORG_CONTACTS
  is '联系人';
comment on column SYS_ORGANIZATION_INFO.ORG_PHONE
  is '联系方式';
comment on column SYS_ORGANIZATION_INFO.CREATOR
  is '创建人';
comment on column SYS_ORGANIZATION_INFO.CREATE_TIME
  is '创建日期';

-- 用户表
CREATE TABLE SYS_USER (
  ID VARCHAR2(50) NOT NULL,
  USERNAME VARCHAR2(50) NOT NULL,
  PASSWORD VARCHAR2(250) NOT NULL,
  HEADPIC VARCHAR2(50) DEFAULT NULL,
  ISVALID number(11,0) NOT NULL,
  CREATETIME date NOT NULL,
  CREATOR VARCHAR2(50) ,
  PHONE VARCHAR2(50),
  ORG_ID VARCHAR2(50) NOT NULL,
  USERALIAS VARCHAR2(50) NOT NULL,
  PRIMARY KEY (ID)
);
-- Add comments to the table
comment on table SYS_USER
  is '用户表';
-- Add comments to the columns
comment on column SYS_USER.ID
  is '主键ID';
comment on column SYS_USER.USERNAME
  is '用户名';
comment on column SYS_USER.PASSWORD
  is '密码 ';
comment on column SYS_USER.HEADPIC
  is '用户头像';
comment on column SYS_USER.ISVALID
  is '是否有效：0有效 1无效';
comment on column SYS_USER.CREATETIME
  is '创建日期';
comment on column SYS_USER.CREATOR
  is '创建人';
comment on column SYS_USER.PHONE
  is '联系方式';
comment on column SYS_USER.ORG_ID
  is '所属组织ID';
comment on column SYS_USER.USERALIAS
  is '用户别名';
-- 角色表：
CREATE TABLE SYS_ROLE (
  ID VARCHAR2(50) NOT NULL,
  ROLE_NAME VARCHAR2(100) NOT NULL,
  ROLE_DESCRIBE VARCHAR2(100),
  ROLE_STATUS number(11,0) NOT NULL,
  PRIMARY KEY (ID)
);
-- Add comments to the table
comment on table SYS_ROLE
  is '角色表';
-- Add comments to the columns
comment on column SYS_ROLE.ID
  is '角色Id';
comment on column SYS_ROLE.ROLE_NAME
  is '角色名称';
comment on column SYS_ROLE.ROLE_DESCRIBE
  is '角色描述';
comment on column SYS_ROLE.ROLE_STATUS
  is '角色状态 0禁用，1启用';
-- 用户角色表
CREATE TABLE  SYS_USER_ROLE (
  ID VARCHAR2(50) NOT NULL,
  ROLE_ID VARCHAR2(50) NOT NULL,
  USER_ID VARCHAR2(50) NOT NULL,
  PRIMARY KEY (ID)
);
-- Add comments to the table
comment on table SYS_USER_ROLE
  is '用户角色表';
-- Add comments to the columns
comment on column SYS_USER_ROLE.ID
  is '用户角色主键id';
comment on column SYS_USER_ROLE.ROLE_ID
  is '角色ID';
comment on column SYS_USER_ROLE.USER_ID
  is '用户ID';
-- 菜单表
CREATE TABLE  SYS_MENU_INFO (
  MENU_ID VARCHAR2(50) NOT NULL,
  MENU_NAME VARCHAR2(50) NOT NULL,
  MENU_PID VARCHAR2(50) NOT NULL ,
  MENU_TYPE VARCHAR2(30),
  MENU_URL VARCHAR2(100) NOT NULL,
  MENU_SORTS number(10,0) NOT NULL ,
  MENU_REMARK VARCHAR2(200) ,
  MENU_LEVELS number(10,0) NOT NULL,
  MENU_ICON VARCHAR2(30) ,
  ISVALID number(10,0) NOT NULL,
  ICON_TYPE VARCHAR2(10) ,
  CREATETIME date NOT NULL,
  PRIMARY KEY (MENU_ID)
);
-- Add comments to the table
comment on table SYS_MENU_INFO
  is '菜单表';
-- Add comments to the columns
comment on column SYS_MENU_INFO.MENU_ID
  is '菜单主键ID';
comment on column SYS_MENU_INFO.MENU_NAME
  is '菜单名称';
comment on column SYS_MENU_INFO.MENU_PID
  is '菜单父节点';
comment on column SYS_MENU_INFO.MENU_TYPE
  is '来源于字典表';
comment on column SYS_MENU_INFO.MENU_URL
  is '菜单地址';
comment on column SYS_MENU_INFO.MENU_SORTS
  is '菜单排序';
comment on column SYS_MENU_INFO.MENU_REMARK
  is '菜单描述';
comment on column SYS_MENU_INFO.MENU_LEVELS
  is '菜单级别';
comment on column SYS_MENU_INFO.MENU_ICON
  is '文字图标或路径';
comment on column SYS_MENU_INFO.ISVALID
  is '0.禁用，1.启用';
comment on column SYS_MENU_INFO.ICON_TYPE
  is 'font.文字图标，image.图片地址';
comment on column SYS_MENU_INFO.CREATETIME
  is '创建时间';
-- 角色菜单表
CREATE TABLE SYS_ROLE_MENU (
  ID VARCHAR2(50) NOT NULL ,
  ROLE_ID VARCHAR2(50) NOT NULL,
  MENU_ID VARCHAR2(50) NOT NULL,
  PRIMARY KEY (ID)
);
-- Add comments to the table
comment on table SYS_ROLE_MENU
  is '角色菜单表';
-- Add comments to the columns
comment on column SYS_ROLE_MENU.ID
  is '角色菜单主键';
comment on column SYS_ROLE_MENU.ROLE_ID
  is '角色ID';
comment on column SYS_ROLE_MENU.MENU_ID
  is '菜单ID';
-- 字典表
CREATE TABLE sys_dict (
  DICT_ID VARCHAR2(50),
  DICT_KEY VARCHAR2(50) NOT NULL,
  DICT_NAME VARCHAR2(50),
  DICT_FID VARCHAR2(50),
  ISVALID number(4,0) NOT NULL,
  DICT_SORTS number(10,0) NOT NULL,
  REMARK VARCHAR2(100),
  PRIMARY KEY (DICT_ID)
);
-- Add comments to the table
comment on table SYS_DICT
  is '字典表';
-- Add comments to the columns
comment on column SYS_DICT.DICT_ID
  is '字典主键Id';
comment on column SYS_DICT.DICT_KEY
  is '字典key';
comment on column SYS_DICT.DICT_NAME
  is '字典名称';
comment on column SYS_DICT.DICT_FID
  is '父ID';
comment on column SYS_DICT.ISVALID
  is '是否有效';
comment on column SYS_DICT.DICT_SORTS
  is '字典排序';
comment on column SYS_DICT.REMARK
  is '备注';
--系统配置表
CREATE TABLE SYS_CONFIG (
  SYS_ID VARCHAR2(50) NOT NULL,
  SYS_NAME VARCHAR2(50) ,
  SYS_VALUE VARCHAR2(50),
  SYS_DESC VARCHAR2(200),
  PRIMARY KEY (SYS_ID)
);
-- Add comments to the table
comment on table SYS_CONFIG
  is '系统配置表';
-- Add comments to the columns
comment on column SYS_CONFIG.SYS_ID
  is '主键ID';
comment on column SYS_CONFIG.SYS_NAME
  is '名称';
comment on column SYS_CONFIG.SYS_VALUE
  is '值';
comment on column SYS_CONFIG.SYS_DESC
  is '描述';
--推送消息表
CREATE TABLE SYS_MESSAGE (
  MESSAGE_ID VARCHAR2(50) NOT NULL ,
  MESSAGE_TITLE VARCHAR2(250),
  MESSAGE_URL VARCHAR2(250) ,
  STATUS number(2,0) NOT NULL,
  REMARK VARCHAR2(250),
  CREATE_USER VARCHAR2(50) ,
  CREATE_TIME date ,
  RECIVER VARCHAR2(50) ,
  DEAL_TIME date ,
  MESSAGE_TYPE VARCHAR2(50) ,
  MESSAGE_CONTENT VARCHAR2(500) ,
  EXPAND_DATA VARCHAR2(500),
  UNIQUE_CODE VARCHAR2(50) ,
  PRIMARY KEY (MESSAGE_ID)
);
-- Add comments to the table
comment on table SYS_MESSAGE
  is '推送消息表';
-- Add comments to the columns
comment on column SYS_MESSAGE.MESSAGE_ID
  is '主键ID';
comment on column SYS_MESSAGE.MESSAGE_TITLE
  is '标题';
comment on column SYS_MESSAGE.MESSAGE_URL
  is '跳转URL';
comment on column SYS_MESSAGE.STATUS
  is '状态0：未读 1：已读';
comment on column SYS_MESSAGE.REMARK
  is '备注';
comment on column SYS_MESSAGE.CREATE_USER
  is '发布人';
comment on column SYS_MESSAGE.CREATE_TIME
  is '发布时间';
comment on column SYS_MESSAGE.RECIVER
  is '接收人';
comment on column SYS_MESSAGE.DEAL_TIME
  is '处理时间';
comment on column SYS_MESSAGE.MESSAGE_TYPE
  is '消息类型';
comment on column SYS_MESSAGE.MESSAGE_CONTENT
  is '消息内容';
comment on column SYS_MESSAGE.EXPAND_DATA
  is '扩展内容';
comment on column SYS_MESSAGE.UNIQUE_CODE
  is '关联业务唯一标识';
--推送消息关联接收人表
CREATE TABLE SYS_MESSAGE_RECEIVER (
  ID VARCHAR2(50) NOT NULL,
  MESSAGE_ID VARCHAR2(50) NOT NULL,
  RECIVER VARCHAR2(50) NOT NULL ,
  STATUS number(10,0) NOT NULL,
  DEAL_TIME date ,
  REMARK VARCHAR2(255) ,
  PRIMARY KEY (id)
);
-- Add comments to the table
comment on table SYS_MESSAGE_RECEIVER
  is '推送消息关联接收人表';
-- Add comments to the columns
comment on column SYS_MESSAGE_RECEIVER.ID
  is '主键id';
comment on column SYS_MESSAGE_RECEIVER.MESSAGE_ID
  is '消息id';
comment on column SYS_MESSAGE_RECEIVER.RECIVER
  is '用户id';
comment on column SYS_MESSAGE_RECEIVER.STATUS
  is '状态0：未读 1：已读';
comment on column SYS_MESSAGE_RECEIVER.DEAL_TIME
  is '处理时间(查看时间或接受时间)';
comment on column SYS_MESSAGE_RECEIVER.REMARK
  is '备用字段，拓展使用';
commit;
-- 组织表
CREATE TABLE IF NOT EXISTS `sys_organization_info` (
  `ORG_ID` varchar(50) NOT NULL,
  `ORG_NAME` varchar(50) NOT NULL COMMENT '组织名称',
  `ORG_TYPE` varchar(50) DEFAULT NULL COMMENT '组织类别',
  `ORG_FID` varchar(50) NOT NULL COMMENT '上级部门',
  `ORG_CONTACTS` varchar(50) NOT NULL COMMENT '联系人',
  `ORG_PHONE` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `CREATOR` varchar(50) NOT NULL,
  `CREATE_TIME` datetime NOT NULL COMMENT '创建日期',
  PRIMARY KEY (`ORG_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='组织信息表';

-- 用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
  `ID` varchar(50) NOT NULL COMMENT '主键ID',
  `USERNAME` varchar(50) NOT NULL COMMENT '用户名',
  `PASSWORD` varchar(250) NOT NULL COMMENT '密码',
  `HEADPIC` varchar(50) DEFAULT NULL COMMENT '用户头像',
  `ISVALID` int(11) NOT NULL COMMENT '是否有效：0有效 1无效',
  `CREATETIME` date NOT NULL COMMENT '创建日期',
  `CREATOR` varchar(50) DEFAULT NULL COMMENT '创建人',
  `PHONE` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `ORG_ID` varchar(50) NOT NULL COMMENT '所属组织ID',
  `USERALIAS` varchar(50) NOT NULL COMMENT '用户别名',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色表：
CREATE TABLE IF NOT EXISTS `sys_role` (
  `ID` varchar(50) NOT NULL,
  `ROLE_NAME` varchar(100) NOT NULL COMMENT '角色名称',
  `ROLE_DESCRIBE` varchar(100) DEFAULT NULL COMMENT '角色描述',
  `ROLE_STATUS` int(11) NOT NULL COMMENT '角色状态 0禁用，1启用',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色信息表';

-- 用户角色表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
  `ID` varchar(50) NOT NULL,
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `USER_ID` varchar(50) NOT NULL COMMENT '用户ID',
  PRIMARY KEY (`ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 菜单表
CREATE TABLE IF NOT EXISTS `sys_menu_info` (
  `MENU_ID` varchar(50) NOT NULL COMMENT '菜单ID',
  `MENU_NAME` varchar(50) NOT NULL COMMENT '菜单名称',
  `MENU_PID` varchar(50) NOT NULL COMMENT '菜单父节点',
  `MENU_TYPE` varchar(30) DEFAULT NULL COMMENT '来源于字典表',
  `MENU_URL` varchar(100) NOT NULL COMMENT '菜单地址',
  `MENU_SORTS` int(10) NOT NULL DEFAULT '0' COMMENT '菜单排序',
  `MENU_REMARK` varchar(200) DEFAULT NULL COMMENT '菜单描述',
  `MENU_LEVELS` int(10) NOT NULL COMMENT '菜单级别',
  `MENU_ICON` varchar(30) DEFAULT NULL COMMENT '文字图标或路径',
  `ISVALID` int(10) NOT NULL DEFAULT '1' COMMENT '0.禁用，1.启用',
  `ICON_TYPE` varchar(10) DEFAULT NULL COMMENT 'font.文字图标，image.图片地址',
  `CREATETIME` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`MENU_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 角色菜单表
CREATE TABLE IF NOT EXISTS `sys_role_menu` (
  `ID` varchar(50) NOT NULL COMMENT '主键',
  `ROLE_ID` varchar(50) NOT NULL COMMENT '角色ID',
  `MENU_ID` varchar(50) NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 字典表
CREATE TABLE IF NOT EXISTS `sys_dict` (
  `DICT_ID` varchar(50) NOT NULL,
  `DICT_KEY` varchar(50) DEFAULT NULL COMMENT '字典key',
  `DICT_NAME` varchar(50) DEFAULT NULL,
  `DICT_FID` varchar(50) DEFAULT NULL COMMENT '父ID',
  `ISVALID` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否有效',
  `DICT_SORTS` int(10) NOT NULL COMMENT '字典排序',
  `REMARK` varchar(100) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`DICT_ID`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=172 DEFAULT CHARSET=utf8mb4 COMMENT='字典信息表';

-- 系统配置表
CREATE TABLE `sys_config` (
  `SYS_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `SYS_NAME` varchar(50) DEFAULT NULL COMMENT '名称',
  `SYS_VALUE` varchar(50) DEFAULT NULL COMMENT '值',
  `SYS_DESC` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`SYS_ID`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ROW_FORMAT=COMPACT COMMENT='系统配置信息表';

-- 推送消息表
CREATE TABLE IF NOT EXISTS `sys_message` (
  `message_id` varchar(50) NOT NULL COMMENT '主键ID',
  `message_title` varchar(250) DEFAULT NULL COMMENT '标题',
  `message_url` varchar(250) DEFAULT NULL COMMENT '跳转URL',
  `status` int(1) NOT NULL COMMENT '状态0：未读 1：已读',
  `remark` varchar(250) DEFAULT NULL COMMENT '备注',
  `create_user` varchar(50) DEFAULT NULL COMMENT '发布人',
  `create_time` datetime DEFAULT NULL COMMENT '发布时间',
  `reciver` varchar(50) DEFAULT NULL COMMENT '接收人',
  `deal_time` datetime DEFAULT NULL COMMENT '处理时间',
  `message_type` varchar(50) DEFAULT NULL COMMENT '消息类型',
  `message_content` varchar(500) DEFAULT NULL COMMENT '消息内容',
  `expand_data` varchar(500) DEFAULT NULL COMMENT '扩展内容',
  `unique_code` varchar(50) DEFAULT NULL COMMENT '关联业务唯一标识',
  PRIMARY KEY (`message_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推送消息表';

-- 推送消息关联接收人表
CREATE TABLE IF NOT EXISTS `sys_message_receiver` (
  `id` varchar(50) NOT NULL COMMENT '主键id',
  `message_id` varchar(50) NOT NULL COMMENT '消息id',
  `reciver` varchar(50) NOT NULL COMMENT '用户id',
  `status` int(10) NOT NULL DEFAULT '0' COMMENT '状态0：未读 1：已读',
  `deal_time` datetime DEFAULT NULL COMMENT '处理时间(查看时间或接受时间)',
  `remark` varchar(255) DEFAULT NULL COMMENT '备用字段，拓展使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='推送消息接收表';
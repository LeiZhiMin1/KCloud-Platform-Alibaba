CREATE TABLE IF NOT EXISTS `boot_sys_login_log_${suffix}`(
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `creator` bigint DEFAULT '0' COMMENT '创建人',
  `editor` bigint DEFAULT '0' COMMENT '编辑人',
  `create_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `del_flag` tinyint(1) NOT NULL DEFAULT '0' COMMENT '删除标识 0未删除 1已删除',
  `version` int NOT NULL DEFAULT '0' COMMENT '版本号',
  `dept_id` bigint DEFAULT '0' COMMENT '部门ID',
  `dept_path` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '0' COMMENT '部门PATH',
  `tenant_id` bigint NOT NULL DEFAULT '0' COMMENT '租户ID',
  `username` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录的用户名',
  `ip` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录的IP地址',
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录的归属地',
  `browser` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录的浏览器',
  `os` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录的操作系统',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '登录状态 0登录成功 1登录失败',
  `message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录信息',
  `type` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录类型',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `idx_tenant_id_request_status` (`tenant_id`,`status`) USING BTREE COMMENT '租户编号_请求状态_索引'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='登录日志';
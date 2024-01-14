SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_area
-- ----------------------------
DROP TABLE IF EXISTS `t_area`;
CREATE TABLE `t_area`  (
  `_code` varchar(12) NOT NULL COMMENT '行政区划码',
  `_name` varchar(500) NOT NULL COMMENT '行政区名称',
  `_level` int NOT NULL COMMENT '行政区等级',
  PRIMARY KEY (`_code`)
) COMMENT = '行政区划表';

SET FOREIGN_KEY_CHECKS = 1;

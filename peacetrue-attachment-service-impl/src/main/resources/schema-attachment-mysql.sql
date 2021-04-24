DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment
(
    id           BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL COMMENT '主键',
    name         VARCHAR(32)                                NOT NULL COMMENT '名称',
    path         VARCHAR(255)                               NOT NULL COMMENT '路径',
    sizes        BIGINT UNSIGNED                            NOT NULL COMMENT '大小（字节）',
    state_id     TINYINT UNSIGNED                           NOT NULL COMMENT '状态编码. 1、临时，2、生效、3、删除',
    remark       VARCHAR(255)                               NOT NULL COMMENT '备注',
    creator_id   BIGINT UNSIGNED                            NOT NULL COMMENT '创建者主键',
    created_time DATETIME                                   NOT NULL COMMENT '创建时间'
) COMMENT '附件';




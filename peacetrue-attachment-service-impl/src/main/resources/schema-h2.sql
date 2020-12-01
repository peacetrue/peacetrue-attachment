DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name         VARCHAR(32)                      NOT NULL COMMENT '名称',
    path         VARCHAR(255)                      NOT NULL COMMENT '路径',
    sizes        BIGINT                            NOT NULL COMMENT '大小（字节）',
    state_id     TINYINT                           NOT NULL COMMENT '状态编码. 1、临时，2、生效、3、删除',
    remark       VARCHAR(255)                      NOT NULL COMMENT '备注',
    creator_id   BIGINT                            NOT NULL COMMENT '创建者主键',
    created_time DATETIME                          NOT NULL COMMENT '创建时间'
);

COMMENT ON TABLE attachment IS '附件';
COMMENT ON COLUMN attachment.id IS '主键';



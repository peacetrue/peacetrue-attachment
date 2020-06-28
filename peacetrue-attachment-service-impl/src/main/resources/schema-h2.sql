DROP TABLE IF EXISTS attachment;
CREATE TABLE attachment
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name         VARCHAR(255)                      NOT NULL COMMENT '名称',
    path         VARCHAR(1022)                     NOT NULL COMMENT '路径',
    sizes        BIGINT                            NOT NULL COMMENT '大小（字节）',
    state_code   VARCHAR(15)                       NOT NULL COMMENT '状态编码：临时、生效、删除',
    remark       VARCHAR(255) COMMENT '备注',
    creator_id   VARCHAR(31)                       NOT NULL COMMENT '创建者主键',
    created_time DATETIME                          NOT NULL COMMENT '创建时间'
);

COMMENT ON TABLE attachment IS '附件';
COMMENT ON COLUMN attachment.id IS '主键';



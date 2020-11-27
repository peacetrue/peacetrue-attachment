package com.github.peacetrue.attachment;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiayx
 */
@Data
public class AttachmentVO implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long id;
    /** 名称 */
    private String name;
    /** 路径 */
    private String path;
    /** 大小（字节） */
    private Long sizes;
    /** 状态编码. 1、临时，2、生效、3、删除 */
    private Integer stateId;
    /** 备注 */
    private String remark;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private LocalDateTime createdTime;
}

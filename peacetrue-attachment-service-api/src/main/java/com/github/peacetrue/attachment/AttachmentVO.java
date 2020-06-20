package com.github.peacetrue.attachment;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

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
    private Long size;
    /** 状态编码：临时、生效、删除 */
    private String stateCode;
    /** 备注 */
    private String remark;
    /** 创建者主键 */
    private Long creatorId;
    /** 创建时间 */
    private Date createdTime;
}

package com.github.peacetrue.attachment;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 附件实体类
 *
 * @author xiayx
 */
@Getter
@Setter
@ToString
public class Attachment implements Serializable {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Id
    private Long id;
    /** 名称 */
    private String name;
    /** 路径 */
    private String path;
    /** 大小（字节） */
    private Long sizes;
    /** 状态编码：临时、生效、删除 */
    private String stateCode;
    /** 备注 */
    private String remark;
    /** 创建者主键 */
    private String creatorId;
    /** 创建时间 */
    private LocalDateTime createdTime;

}

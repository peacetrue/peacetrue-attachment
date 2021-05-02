package com.github.peacetrue.attachment;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.core.Range;
import lombok.*;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentQuery extends OperatorImpl<Long> {

    public static final AttachmentQuery DEFAULT = new AttachmentQuery();

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long[] id;
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
    private Range.LocalDateTime createdTime;

    public AttachmentQuery(Long[] id) {
        this.id = id;
    }

}

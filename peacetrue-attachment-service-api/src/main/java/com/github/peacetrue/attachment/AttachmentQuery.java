package com.github.peacetrue.attachment;

import com.github.peacetrue.core.OperatorCapableImpl;
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
public class AttachmentQuery extends OperatorCapableImpl<Long> {

    public static final AttachmentQuery DEFAULT = new AttachmentQuery();

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long[] id;
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
    private Range.Date createdTime;

    public AttachmentQuery(Long[] id) {
        this.id = id;
    }


}

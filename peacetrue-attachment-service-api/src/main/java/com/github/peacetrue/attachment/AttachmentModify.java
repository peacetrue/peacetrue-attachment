package com.github.peacetrue.attachment;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentModify extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @NotNull
    private Long id;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    /** 路径 */
    @NotNull
    @Size(min = 1, max = 1022)
    private String path;
    /** 大小（字节） */
    @NotNull
    private Long size;
    /** 状态编码：临时、生效、删除 */
    @NotNull
    @Size(min = 1, max = 15)
    private String stateCode;
    /** 备注 */
    @Size(min = 1, max = 255)
    private String remark;

}
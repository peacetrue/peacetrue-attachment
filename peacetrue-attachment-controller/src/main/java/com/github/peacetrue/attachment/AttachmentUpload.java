package com.github.peacetrue.attachment;

import com.github.peacetrue.core.OperatorCapableImpl;
import lombok.*;
import org.springframework.http.codec.multipart.FilePart;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentUpload extends OperatorCapableImpl<Long> {

    private static final long serialVersionUID = 0L;

    @Size(max = 255)
    private String relativePath;
    @NotNull
    private FilePart filePart;
    @Size(max = 255)
    private String remark;

    public AttachmentUpload(FilePart filePart) {
        this.filePart = filePart;
    }
}

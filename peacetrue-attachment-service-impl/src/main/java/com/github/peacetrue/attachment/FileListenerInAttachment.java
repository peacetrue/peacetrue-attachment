package com.github.peacetrue.attachment;

import com.github.peacetrue.core.Operators;
import com.github.peacetrue.file.FileAdd;
import com.github.peacetrue.file.FileVO;
import com.github.peacetrue.spring.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

/**
 * @author : xiayx
 * @since : 2020-12-01 10:19
 **/
@Slf4j
@Component
public class FileListenerInAttachment {

    @Autowired
    private AttachmentService attachmentService;

    @Order(0)
    @EventListener
    public void addAttachmentAfterFileAdd(PayloadApplicationEvent<FileAdd> event) {
        FileVO source = (FileVO) event.getSource();
        log.info("新增文件后新增附件[{}]", source);
        AttachmentAdd attachmentAdd = BeanUtils.map(source, AttachmentAdd.class);
        Operators.setOperator(event.getPayload(), attachmentAdd);
        attachmentService.add(attachmentAdd)
                .publishOn(Schedulers.elastic())
                .subscribe();
    }
}

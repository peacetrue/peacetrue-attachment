package com.github.peacetrue.attachment;

import com.github.peacetrue.file.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import reactor.core.scheduler.Schedulers;

/**
 * @author : xiayx
 * @since : 2020-11-29 17:25
 **/
@Slf4j
@Component
public class AttachmentListener {

    @Autowired
    private FileService fileService;

    @Order(0)
    @EventListener
    public void deleteLocalFileAfterAttachmentDelete(PayloadApplicationEvent<AttachmentDelete> event) {
        AttachmentVO source = (AttachmentVO) event.getSource();
        log.info("删除附件[{}]后删除本地文件[{}]", source.getId(), source.getPath());
        fileService.delete(source.getPath())
                .publishOn(Schedulers.elastic())
                .subscribe(result -> {
                    if (result) log.info("文件[{}]删除成功", source.getPath());
                    else log.warn("文件[{}]删除失败", source.getPath());
                });
    }
}

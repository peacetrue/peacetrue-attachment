package com.github.peacetrue.attachment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Nullable;
import java.util.List;

/**
 * 附件客户端
 *
 * @author xiayx
 */
@FeignClient(name = "peacetrue-attachment")
public interface AttachmentServiceClient {

    @PostMapping(value = "/attachments")
    AttachmentVO add(AttachmentAdd params);

    @GetMapping(value = "/attachments", params = "page")
    Page<AttachmentVO> query(@Nullable @SpringQueryMap AttachmentQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments", params = "sort")
    List<AttachmentVO> query(@SpringQueryMap AttachmentQuery params, Sort sort, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments")
    List<AttachmentVO> query(@SpringQueryMap AttachmentQuery params, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments/get")
    AttachmentVO get(@SpringQueryMap AttachmentGet params, @SpringQueryMap String... projection);

    @PostMapping(value = "/attachments")
    Integer modify(AttachmentModify params);

    @GetMapping(value = "/attachments/delete")
    Integer delete(@SpringQueryMap AttachmentDelete params);

}

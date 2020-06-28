package com.github.peacetrue.attachment;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 附件客户端
 *
 * @author xiayx
 */
@ReactiveFeignClient(name = "peacetrue-attachment", url = "${peacetrue.attachment.url:${peacetrue.server.url:}}")
public interface AttachmentServiceClient extends AttachmentService {

    @PostMapping(value = "/attachments")
    Mono<AttachmentVO> add(AttachmentAdd params);

    @GetMapping(value = "/attachments", params = "page")
    Mono<Page<AttachmentVO>> query(@Nullable @SpringQueryMap AttachmentQuery params, Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments", params = "sort")
    Flux<AttachmentVO> query(@SpringQueryMap AttachmentQuery params, Sort sort, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments")
    Flux<AttachmentVO> query(@SpringQueryMap AttachmentQuery params, @SpringQueryMap String... projection);

    @GetMapping(value = "/attachments/get")
    Mono<AttachmentVO> get(@SpringQueryMap AttachmentGet params, @SpringQueryMap String... projection);

    @PutMapping(value = "/attachments")
    Mono<Integer> modify(AttachmentModify params);

    @DeleteMapping(value = "/attachments/delete")
    Mono<Integer> delete(@SpringQueryMap AttachmentDelete params);

}

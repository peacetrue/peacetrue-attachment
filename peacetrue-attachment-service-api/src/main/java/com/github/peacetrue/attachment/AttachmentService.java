package com.github.peacetrue.attachment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 附件服务接口
 *
 * @author xiayx
 */
public interface AttachmentService {

    /** 新增 */
    Mono<AttachmentVO> add(AttachmentAdd params);

    /** 分页查询 */
    Mono<Page<AttachmentVO>> query(@Nullable AttachmentQuery params, @Nullable Pageable pageable, String... projection);

    /** 全量查询 */
    Flux<AttachmentVO> query(AttachmentQuery params, @Nullable Sort sort, String... projection);

    /** 全量查询 */
    default Flux<AttachmentVO> query(AttachmentQuery params, String... projection) {
        return this.query(params, (Sort) null, projection);
    }

    /** 获取 */
    Mono<AttachmentVO> get(AttachmentGet params, String... projection);

    /** 修改 */
    Mono<Integer> modify(AttachmentModify params);

    /** 删除 */
    Mono<Integer> delete(AttachmentDelete params);

}

package com.github.peacetrue.attachment;

import com.github.peacetrue.core.Range;
import com.github.peacetrue.spring.data.relational.core.query.CriteriaUtils;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.data.relational.core.query.UpdateUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.util.StreamUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.data.domain.*;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 附件服务实现
 *
 * @author xiayx
 */
@Slf4j
@Service
public class AttachmentServiceImpl implements AttachmentService {

    @Autowired
    private R2dbcEntityTemplate entityTemplate;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public static Criteria buildCriteria(AttachmentQuery params) {
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("name")::like, value -> "%" + value + "%", params::getName),
                CriteriaUtils.nullableCriteria(Criteria.where("path")::like, value -> "%" + value + "%", params::getPath),
                CriteriaUtils.nullableCriteria(Criteria.where("sizes")::is, params::getSizes),
                CriteriaUtils.nullableCriteria(Criteria.where("stateCode")::like, value -> "%" + value + "%", params::getStateCode),
                CriteriaUtils.nullableCriteria(Criteria.where("remark")::like, value -> "%" + value + "%", params::getRemark),
                CriteriaUtils.nullableCriteria(Criteria.where("creatorId")::is, params::getCreatorId),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::greaterThanOrEquals, params.getCreatedTime()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::lessThanOrEquals, params.getCreatedTime()::getUpperBound)
        );
    }

    @Override
    @Transactional
    public Mono<AttachmentVO> add(AttachmentAdd params) {
        log.info("新增附件信息[{}]", params);
        Attachment entity = BeanUtils.map(params, Attachment.class);
        entity.setCreatorId(params.getOperatorId());
        entity.setCreatedTime(LocalDateTime.now());
        return entityTemplate.insert(entity)
                .map(item -> BeanUtils.map(item, AttachmentVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<AttachmentVO>> query(@Nullable AttachmentQuery params, @Nullable Pageable pageable, String... projection) {
        log.info("分页查询附件信息[{}]", params);
        if (params == null) params = AttachmentQuery.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.Date.DEFAULT);
        Pageable finalPageable = pageable == null ? PageRequest.of(0, 10) : pageable;
        Criteria where = buildCriteria(params);

        return entityTemplate.count(Query.query(where), Attachment.class)
                .flatMap(total -> total == 0L ? Mono.empty() : Mono.just(total))
                .<Page<AttachmentVO>>flatMap(total -> {
                    Query query = Query.query(where).with(finalPageable).sort(finalPageable.getSortOr(Sort.by("createdTime").descending()));
                    return entityTemplate.select(query, Attachment.class)
                            .map(item -> BeanUtils.map(item, AttachmentVO.class))
                            .reduce(new ArrayList<>(), StreamUtils.reduceToCollection())
                            .map(item -> new PageImpl<>(item, finalPageable, total));
                })
                .switchIfEmpty(Mono.just(new PageImpl<>(Collections.emptyList(), finalPageable, 0L)));
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<AttachmentVO> query(@Nullable AttachmentQuery params, @Nullable Sort sort, String... projection) {
        log.info("全量查询附件信息[{}]", params);
        if (params == null) params = AttachmentQuery.DEFAULT;
        if (params.getCreatedTime() == null) params.setCreatedTime(Range.Date.DEFAULT);
        if (sort == null) sort = Sort.by("createdTime").descending();
        Criteria where = buildCriteria(params);
        Query query = Query.query(where).sort(sort).limit(100);
        return entityTemplate.select(query, Attachment.class)
                .map(item -> BeanUtils.map(item, AttachmentVO.class));
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<AttachmentVO> get(AttachmentGet params, String... projection) {
        log.info("获取附件信息[{}]", params);
//        Criteria where = CriteriaUtils.and(
//                CriteriaUtils.nullableId(params::getId),
//        );
        Criteria where = CriteriaUtils.id(params::getId);
        return entityTemplate.selectOne(Query.query(where), Attachment.class)
                .map(item -> BeanUtils.map(item, AttachmentVO.class));
    }

    @Override
    @Transactional
    public Mono<Integer> modify(AttachmentModify params) {
        log.info("修改附件信息[{}]", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityTemplate.selectOne(idQuery, Attachment.class)
                .map(item -> BeanUtils.map(item, AttachmentVO.class))
                .zipWhen(entity -> {
                    Attachment modify = BeanUtils.map(params, Attachment.class);
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityTemplate.update(idQuery, update, Attachment.class);
                })
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

    @Override
    @Transactional
    public Mono<Integer> delete(AttachmentDelete params) {
        log.info("删除附件信息[{}]", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityTemplate.selectOne(idQuery, Attachment.class)
                .map(item -> BeanUtils.map(item, AttachmentVO.class))
                .zipWhen(region -> entityTemplate.delete(idQuery, Attachment.class))
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .switchIfEmpty(Mono.just(0));
    }

}

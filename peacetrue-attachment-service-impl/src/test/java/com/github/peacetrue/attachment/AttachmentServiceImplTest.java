package com.github.peacetrue.attachment;

import com.github.peacetrue.spring.util.BeanUtils;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.io.Serializable;


/**
 * @author : xiayx
 * @since : 2020-05-22 16:43
 **/
@SpringBootTest(classes = TestServiceAttachmentAutoConfiguration.class)
@ActiveProfiles("attachment-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AttachmentServiceImplTest {

    public static final EasyRandom EASY_RANDOM = new EasyRandom(new EasyRandomParameters().randomize(Serializable.class, () -> "operatorName"));
    public static final AttachmentAdd ADD = EASY_RANDOM.nextObject(AttachmentAdd.class);
    public static final AttachmentModify MODIFY = EASY_RANDOM.nextObject(AttachmentModify.class);
    public static AttachmentVO vo;

    static {
        ADD.setOperatorId(EASY_RANDOM.nextObject(Long.class));
        MODIFY.setOperatorId(EASY_RANDOM.nextObject(Long.class));
    }

    @Autowired
    private AttachmentServiceImpl service;

    @Test
    @Order(10)
    void add() {
        service.add(ADD)
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Assertions.assertEquals(data.getCreatorId(), ADD.getOperatorId());
                    vo = data;
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        AttachmentQuery params = BeanUtils.map(vo, AttachmentQuery.class);
        service.query(params, PageRequest.of(0, 10))
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        AttachmentQuery params = BeanUtils.map(vo, AttachmentQuery.class);
        service.query(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        AttachmentGet params = BeanUtils.map(vo, AttachmentGet.class);
        service.get(params)
                .as(StepVerifier::create)
                .assertNext(item -> Assertions.assertEquals(vo.getId(), item.getId()))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        AttachmentModify params = MODIFY;
        params.setId(vo.getId());
        service.modify(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    @Order(60)
    void delete() {
        AttachmentDelete params = new AttachmentDelete(vo.getId());
        service.delete(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}

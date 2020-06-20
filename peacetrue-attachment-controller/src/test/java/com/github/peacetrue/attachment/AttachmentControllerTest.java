package com.github.peacetrue.attachment;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

/**
 * @author xiayx
 */
@SpringBootTest(classes = TestControllerAttachmentAutoConfiguration.class)
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AttachmentControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    @Order(10)
    public void add() {
        this.client.post().uri("/attachments")
                .bodyValue(AttachmentServiceImplTest.ADD)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AttachmentVO.class).value((Consumer<AttachmentVO>) vo -> AttachmentServiceImplTest.vo = vo);
    }

    @Test
    @Order(20)
    public void queryForPage() {
        this.client.get()
                .uri("/attachments?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.totalElements").isEqualTo(1);
    }

    @Test
    @Order(30)
    public void queryForList() {
        this.client.get()
                .uri("/attachments")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(1);
    }

    @Test
    @Order(40)
    public void get() {
        this.client.get()
                .uri("/attachments/{0}", AttachmentServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(AttachmentVO.class).isEqualTo(AttachmentServiceImplTest.vo);
    }


    @Test
    @Order(50)
    public void modify() {
        AttachmentModify modify = AttachmentServiceImplTest.MODIFY;
        modify.setId(AttachmentServiceImplTest.vo.getId());
        this.client.put()
                .uri("/attachments/{id}", AttachmentServiceImplTest.vo.getId())
                .bodyValue(modify)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

    @Test
    @Order(60)
    public void delete() {
        this.client.delete()
                .uri("/attachments/{0}", AttachmentServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

}

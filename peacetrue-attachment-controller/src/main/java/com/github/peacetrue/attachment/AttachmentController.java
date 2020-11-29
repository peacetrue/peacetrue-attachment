package com.github.peacetrue.attachment;

import com.github.peacetrue.file.FileController;
import com.github.peacetrue.file.FileService;
import com.github.peacetrue.spring.util.BeanUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 附件控制器
 *
 * @author xiayx
 */
@Slf4j
@RestController
@RequestMapping(value = "/attachments")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;
    @Autowired
    private FileService fileService;
    @Autowired
    private FileController fileController;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<AttachmentVO> addByForm(AttachmentAdd params) {
        log.info("新增附件信息(请求方法+表单参数)[{}]", params);
        return attachmentService.add(params);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<AttachmentVO> addByJson(@RequestBody AttachmentAdd params) {
        log.info("新增附件信息(请求方法+JSON参数)[{}]", params);
        return attachmentService.add(params);
    }

    @GetMapping(params = "page")
    public Mono<Page<AttachmentVO>> query(AttachmentQuery params, Pageable pageable, String... projection) {
        log.info("分页查询附件信息(请求方法+参数变量)[{}]", params);
        return attachmentService.query(params, pageable, projection);
    }

    @GetMapping
    public Flux<AttachmentVO> query(AttachmentQuery params, Sort sort, String... projection) {
        log.info("全量查询附件信息(请求方法+参数变量)[{}]", params);
        return attachmentService.query(params, sort, projection);
    }

    @GetMapping("/{id}")
    public Mono<AttachmentVO> getByUrlPathVariable(@PathVariable Long id, String... projection) {
        log.info("获取附件信息(请求方法+路径变量)详情[{}]", id);
        return attachmentService.get(new AttachmentGet(id), projection);
    }

    @RequestMapping("/get")
    public Mono<AttachmentVO> getByPath(AttachmentGet params, String... projection) {
        log.info("获取附件信息(请求路径+参数变量)详情[{}]", params);
        return attachmentService.get(params, projection);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> modifyByForm(AttachmentModify params) {
        log.info("修改附件信息(请求方法+表单参数)[{}]", params);
        return attachmentService.modify(params);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modifyByJson(@RequestBody AttachmentModify params) {
        log.info("修改附件信息(请求方法+JSON参数)[{}]", params);
        return attachmentService.modify(params);
    }

    @DeleteMapping("/{id}")
    public Mono<Integer> deleteByUrlPathVariable(@PathVariable Long id) {
        log.info("删除附件信息(请求方法+URL路径变量)[{}]", id);
        return attachmentService.delete(new AttachmentDelete(id));
    }

    @DeleteMapping(params = "id")
    public Mono<Integer> deleteByUrlParamVariable(AttachmentDelete params) {
        log.info("删除附件信息(请求方法+URL参数变量)[{}]", params);
        return attachmentService.delete(params);
    }

    @RequestMapping(path = "/delete")
    public Mono<Integer> deleteByPath(AttachmentDelete params) {
        log.info("删除附件信息(请求路径+URL参数变量)[{}]", params);
        return attachmentService.delete(params);
    }

    @ResponseBody
    @PostMapping(params = "fileCount=1")
    public Mono<AttachmentVO> upload(@RequestPart("file") Mono<FilePart> file, String remark) {
        log.info("上传单个附件");
        return fileController.upload(file)
                .flatMap(fileVO -> {
                    AttachmentAdd attachmentAdd = BeanUtils.map(fileVO, AttachmentAdd.class);
                    attachmentAdd.setRemark(remark);
                    return attachmentService.add(attachmentAdd);
                });
    }

    @ResponseBody
    @PostMapping(params = "fileCount")
    public Flux<AttachmentVO> upload(@RequestPart("files") Flux<FilePart> files, String remark) {
        log.info("上传多个附件");
        return fileController.upload(files)
                .flatMap(fileVO -> {
                    AttachmentAdd attachmentAdd = BeanUtils.map(fileVO, AttachmentAdd.class);
                    attachmentAdd.setRemark(remark);
                    return attachmentService.add(attachmentAdd);
                });
    }

    /** {@link FileController#DISPOSITION_TYPE_ATTACHMENT} */
    @GetMapping(value = "/{id}", params = "dispositionType")
    public Mono<Void> download(ServerHttpResponse response,
                               String dispositionType,
                               @PathVariable Long id) {
        return attachmentService.get(new AttachmentGet(id))
                .flatMap(vo -> {
                    String absoluteFilePath = fileService.getAbsoluteFilePath(vo.getPath());
                    return FileController.writeLocalFile(response, dispositionType, absoluteFilePath, vo.getName());
                });
    }


}

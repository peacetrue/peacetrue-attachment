package com.github.peacetrue.attachment;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ControllerAttachmentProperties.class)
@ComponentScan(basePackageClasses = ControllerAttachmentAutoConfiguration.class)
@PropertySource("classpath:/application-attachment-controller.yml")
public class ControllerAttachmentAutoConfiguration {

}

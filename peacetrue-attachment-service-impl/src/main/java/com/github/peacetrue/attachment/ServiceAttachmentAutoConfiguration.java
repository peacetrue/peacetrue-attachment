package com.github.peacetrue.attachment;

import com.github.peacetrue.spring.core.io.support.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ServiceAttachmentProperties.class)
@ComponentScan(basePackageClasses = ServiceAttachmentAutoConfiguration.class)
@PropertySource(value = "classpath:/application-attachment-service.yml", factory = YamlPropertySourceFactory.class)
public class ServiceAttachmentAutoConfiguration {

    private ServiceAttachmentProperties properties;

    public ServiceAttachmentAutoConfiguration(ServiceAttachmentProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }
}

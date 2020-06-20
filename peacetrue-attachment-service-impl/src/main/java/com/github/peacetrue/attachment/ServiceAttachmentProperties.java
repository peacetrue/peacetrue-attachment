package com.github.peacetrue.attachment;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.attachment")
public class ServiceAttachmentProperties {

}

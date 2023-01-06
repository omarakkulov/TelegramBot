package com.akkulov.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:rabbit-queue.properties")
@ConfigurationProperties(prefix = "rabbit.queue")
public class RabbitQueueProperties {

  private String textMessageUpdateQueueName;
}

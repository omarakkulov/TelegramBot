package com.akkulov.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Пропертя с названиями очередей.
 */
@Getter
@Setter
@PropertySource("classpath:rabbit-queue.properties")
@ConfigurationProperties(prefix = "rabbit.queue")
public class RabbitQueueProperties {

  private String textMessageUpdateQueueName;
  private String answerMessageQueueName;
}

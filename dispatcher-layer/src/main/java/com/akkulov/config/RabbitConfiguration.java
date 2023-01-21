package com.akkulov.config;

import com.akkulov.properties.RabbitQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация очередей и вспомогательных бинов для rabbitmq.
 */
@Configuration
@RequiredArgsConstructor
public class RabbitConfiguration {

  /**
   * Пропертя с названиями очередей.
   */
  private final RabbitQueueProperties rabbitQueueProperties;

  /**
   * Преобразует приходящий объект апдейта в json и передавать их в rabbitmq.
   * И наоборот, при получении обратно этих json из брокера, преобразует их в java-объект.
   */
  @Bean
  public MessageConverter jsonMessageConverter() {
    return new Jackson2JsonMessageConverter();
  }

  /**
   * Бин, соответствующий очереди с текстовыми сообщениями в rabbitmq.
   */
  @Bean
  public Queue textMessageQueue() {
    return new Queue(rabbitQueueProperties.getTextMessageUpdateQueueName());
  }

  /**
   * Бин, соответствующий очереди, в которую будут помещаться ответы.
   */
  @Bean
  public Queue answerMessageQueue() {
    return new Queue(rabbitQueueProperties.getAnswerMessageQueueName());
  }
}

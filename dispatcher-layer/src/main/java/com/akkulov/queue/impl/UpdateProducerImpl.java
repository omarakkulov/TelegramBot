package com.akkulov.queue.impl;

import com.akkulov.queue.UpdateProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Отправитель сообщений в очередь.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UpdateProducerImpl implements UpdateProducer {

  private final RabbitTemplate rabbitTemplate;

  @Override
  public void produce(String rabbitQueueName, Update update) {
    log.info("Message is being sent to queue={}, message={}",
        rabbitQueueName,
        update.getMessage().getText()
    );
    rabbitTemplate.convertAndSend(rabbitQueueName, update);
  }
}

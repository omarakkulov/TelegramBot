package com.akkulov.queue.impl;

import com.akkulov.queue.UpdateProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Отправитель сообщений в очередь.
 */
@Service
@Slf4j
public class UpdateProducerImpl implements UpdateProducer {

  @Override
  public void produce(String rabbitQueueName, Update update) {
    log.info("Message was sent to queue={}, message={}", rabbitQueueName, update.getMessage().getText());
  }
}

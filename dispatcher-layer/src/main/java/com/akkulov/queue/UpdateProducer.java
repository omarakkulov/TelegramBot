package com.akkulov.queue;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Класс отправки сообщения в очередь.
 */
public interface UpdateProducer {

  /**
   * Отправить сообщение в очередь.
   *
   * @param rabbitQueueName название очереди
   * @param update          сообщение
   */
  void produce(String rabbitQueueName, Update update);
}

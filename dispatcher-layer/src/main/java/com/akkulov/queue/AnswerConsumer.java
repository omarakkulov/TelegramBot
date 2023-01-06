package com.akkulov.queue;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Класс для обработки сообщения в очереди.
 */
public interface AnswerConsumer {

  void consume(SendMessage sendMessage);
}

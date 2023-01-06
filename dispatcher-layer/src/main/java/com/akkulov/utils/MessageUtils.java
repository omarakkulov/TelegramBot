package com.akkulov.utils;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Утилитный класс для работы с сообщениями.
 */
public final class MessageUtils {

  private MessageUtils() {
  }

  /**
   * Создать объект сообщения для отправки пользователю.
   *
   * @param update сообщение
   * @param text   текст сообщения
   * @return объект сообщения для отправки пользователю
   */
  public static SendMessage sendMessageWithText(Update update, String text) {
    Message message;
    if (update.getMessage() == null) {
      message = update.getEditedMessage();
    } else {
      message = update.getMessage();
    }

    return SendMessage.builder()
        .chatId(message.getChatId())
        .text(text)
        .build();
  }
}

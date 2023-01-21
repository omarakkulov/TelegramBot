package com.akkulov.controller;

import com.akkulov.bot.TelegramBot;
import com.akkulov.properties.RabbitQueueProperties;
import com.akkulov.queue.UpdateProducer;
import com.akkulov.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Распределитель сообщений по очередям.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDistributor {

  private TelegramBot telegramBot;
  private final UpdateProducer updateProducer;
  private final RabbitQueueProperties rabbitQueueProperties;

  /**
   * Заинжектить ссылку телеграм-бота.
   *
   * @param telegramBot ссылку телеграм-бота
   */
  public void registerBot(TelegramBot telegramBot) {
    this.telegramBot = telegramBot;
  }

  /**
   * Обработать входящее сообщение.
   *
   * @param update входящее сообщение (апдейт)
   */
  public void processMessage(Update update) {
    if (update.getEditedMessage() != null) {
      final var sendMessage = MessageUtils.sendMessageWithText(update,
          "Не редачь сообщения, убьет!"
      );

      sendResponseMessageToUser(sendMessage);
      return;
    }

    if (update.getMessage().getText() == null) {
      processUnsupportedMessageType(update);
    }

    processTextMessage(update);
  }

  /**
   * Обработать текстовое сообщение.
   *
   * @param update апдейт
   */
  private void processTextMessage(Update update) {
    updateProducer.produce(rabbitQueueProperties.getTextMessageUpdateQueueName(), update);
    final var sendMessage = MessageUtils.sendMessageWithText(update, update.getMessage().getText());
    sendResponseMessageToUser(sendMessage);
  }

  /**
   * Обработать неподдерживаемый вид сообщения.
   *
   * @param update апдейт
   */
  private void processUnsupportedMessageType(Update update) {
    final var sendMessage = MessageUtils.sendMessageWithText(update,
        "Неподдерживаемый вид сообщения!"
    );

    sendResponseMessageToUser(sendMessage);
  }

  /**
   * Отправить ответ пользователю.
   *
   * @param sendMessage ответ пользователю
   */
  private void sendResponseMessageToUser(SendMessage sendMessage) {
    telegramBot.sendResponse(sendMessage);
  }
}

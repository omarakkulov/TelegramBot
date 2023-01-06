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

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageDistributor {

  private TelegramBot telegramBot;
  private final UpdateProducer updateProducer;
  private final RabbitQueueProperties rabbitQueueProperties;

  public void registerBot(TelegramBot telegramBot) {
    this.telegramBot = telegramBot;
  }

  /**
   * Обработать входящее сообщение.
   *
   * @param update входящее сообщение
   */
  public void processMessage(Update update) {
    if (update.getEditedMessage() != null) {
      var sendMessage = MessageUtils.sendMessageWithText(update, "Не редачь сообщения, убьет!");
      sendAnswerToUser(sendMessage);
      return;
    }

    if (update.getMessage().getText() == null) {
      processUnsupportedMessageType(update);
    }

    processTextMessage(update);
  }

  private void processTextMessage(Update update) {
    updateProducer.produce(rabbitQueueProperties.getTextMessageUpdateQueueName(), update);
    var sendMessage = MessageUtils.sendMessageWithText(update, update.getMessage().getText());
    sendAnswerToUser(sendMessage);
  }

  /**
   * Обработать неподдерживаемый вид сообщения.
   *
   * @param update сообщение
   */
  private void processUnsupportedMessageType(Update update) {
    var sendMessage = MessageUtils.sendMessageWithText(update, "Неподдерживаемый вид сообщения!");
    sendAnswerToUser(sendMessage);
  }

  /**
   * Отправить ответ пользователю.
   *
   * @param sendMessage ответ пользователю
   */
  private void sendAnswerToUser(SendMessage sendMessage) {
    telegramBot.sendAnswerToUser(sendMessage);
  }
}

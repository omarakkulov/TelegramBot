package com.akkulov.bot;

import com.akkulov.controller.MessageDistributor;
import com.akkulov.properties.TelegramBotProperties;
import javax.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Getter
@Setter
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

  /**
   * Данные по боту.
   */
  private final TelegramBotProperties telegramBotProperties;

  /**
   * Распределитель сообщений.
   */
  private final MessageDistributor messageDistributor;

  /**
   * Внедрить ссылку бота в MessageDistributor.
   */
  @PostConstruct
  private void initializeMessageDistributor() {
    messageDistributor.registerBot(this);
  }

  /**
   * Получить сообщение от пользователя.
   *
   * @param update Update received
   */
  @Override
  public void onUpdateReceived(Update update) {
    if (update.getMessage() != null) {
      log.info("Incoming message: chatId={}, username={}, message={}",
          update.getMessage().getChatId(),
          update.getMessage().getFrom().getUserName(),
          update.getMessage().getText()
      );
    } else {
      log.info("Message was edited. Edited message: chatId={}, username={}, message={}",
          update.getEditedMessage().getChatId(),
          update.getEditedMessage().getFrom().getUserName(),
          update.getEditedMessage().getText());
    }

    messageDistributor.processMessage(update);
  }

  /**
   * Отправить ответ пользователю.
   *
   * @param sendMessage ответ пользователю
   */
  public void sendAnswerToUser(SendMessage sendMessage) {
    try {
      execute(sendMessage);
      log.info("Successfully sent response to user");
    } catch (TelegramApiException e) {
      log.error("Error while during message acceptance");
      throw new RuntimeException(e);
    }
  }

  @Override
  public String getBotUsername() {
    return telegramBotProperties.getUsername();
  }

  @Override
  public String getBotToken() {
    return telegramBotProperties.getToken();
  }
}

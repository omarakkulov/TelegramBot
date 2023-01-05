package com.akkulov.bot;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Getter
@Setter
@Slf4j
@PropertySource("classpath:botConfig.properties")
@ConfigurationProperties(prefix = "bot")
public class TelegramBot extends TelegramLongPollingBot {

  private String username;
  private String token;

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public String getBotToken() {
    return token;
  }

  @SneakyThrows
  @Override
  public void onUpdateReceived(Update update) {
    log.info("Incoming message: chatId={}, username={}, message={}",
        update.getMessage().getChatId(),
        update.getMessage().getFrom().getUserName(),
        update.getMessage().getText()
    );

    execute(SendMessage.builder()
        .text(update.getMessage().getText())
        .chatId(update.getMessage().getChatId())
        .build());
  }
}

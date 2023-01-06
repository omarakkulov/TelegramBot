package com.akkulov.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@PropertySource("classpath:botConfig.properties")
@ConfigurationProperties(prefix = "bot")
public class TelegramBotProperties {

  private String username;
  private String token;
}

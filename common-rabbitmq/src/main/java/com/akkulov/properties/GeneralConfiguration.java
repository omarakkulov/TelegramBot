package com.akkulov.properties;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({
    TelegramBotProperties.class,
    RabbitQueueProperties.class
})
@Configuration
public class GeneralConfiguration {

}

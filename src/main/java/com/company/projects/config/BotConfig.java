package com.company.projects.config;

import com.company.projects.ReminderBot;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 04/09/23 16:44
 * some-personal-projects
 */

@Configuration
public class BotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String username;

    @Bean
    public ReminderBot myTelegramBot() {
        TelegramBotsApi telegramBotsApi;
        try {
            telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

        ReminderBot bot = new ReminderBot();
        bot.setBotToken(botToken);
        bot.setBotUsername(username);
        try {
            telegramBotsApi.registerBot(bot);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        return bot;
    }
}

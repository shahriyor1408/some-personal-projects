package com.company.projects;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@RequiredArgsConstructor
public class DemoApplication {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String username;

    private final ReminderBot bot;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            TelegramBotsApi telegramBotsApi;
            try {
                telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
            bot.setBotToken(botToken);
            bot.setBotUsername(username);
            try {
                telegramBotsApi.registerBot(bot);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        };
    }

}

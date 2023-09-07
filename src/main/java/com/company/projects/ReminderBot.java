package com.company.projects;

import com.company.projects.service.BotRequestService;
import com.company.projects.service.BotResponseService;
import com.company.projects.service.BotService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 04/09/23 16:27
 * some-personal-projects
 */

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ReminderBot extends TelegramLongPollingBot {
    private String botToken;
    private String botUsername;
    private final BotService botService;
    private final BotRequestService botRequestService;
    private final BotResponseService botResponseService;

    public void setBotToken(String botToken) {
        this.botToken = botToken;
    }

    public void setBotUsername(String botUsername) {
        this.botUsername = botUsername;
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        if (update.hasMessage()) {
            botService.addUser(message);
            if (message.getText().equals("/start")) {
                botResponseService.start(message);

            } else if (message.getText().equalsIgnoreCase("Bugun kim navbatchi")) {
                botService.todayDuty();

            } else if (message.getText().equalsIgnoreCase("salom")) {
                botResponseService.hello(message);

            } else {
                botService.sendSimpleMessage(message);

            }
        } else if (update.hasCallbackQuery()) {
            botRequestService.handleCallBack(update.getCallbackQuery());
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    public void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(EditMessageText editMessageText) {
        try {
            execute(editMessageText);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(SendPhoto sendPhoto) {
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMsg(DeleteMessage deleteMessage) {
        try {
            execute(deleteMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}

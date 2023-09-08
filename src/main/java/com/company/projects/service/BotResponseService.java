package com.company.projects.service;

import com.company.projects.ReminderBot;
import com.company.projects.util.MessageUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/09/23 14:01
 * some-personal-projects
 */

@Service
public class BotResponseService {

    private final ReminderBot reminderBot;
    private final MessageUtil messageUtil;

    public BotResponseService(@Lazy ReminderBot reminderBot, MessageUtil messageUtil) {
        this.reminderBot = reminderBot;
        this.messageUtil = messageUtil;
    }

    public void start(Message message) {
        String chatId = String.valueOf(message.getChatId());
        String photo = "https://play-lh.googleusercontent.com/ELarT28qLtY5LGU9y6mXwqsI6h4OJQhM-_JmJpvgQ-tcVwgxF5z3ymN2JEIIlw988Og_";
        String caption = "** Assalomu alaykum,botga xush kelibsiz!\uD83C\uDF89 \n Bu bot orqali siz navbatchilikni nazorat qilishingiz mumkin. **\uD83D\uDD10";
        SendPhoto sendPhoto = messageUtil.getSendPhoto(photo, caption, chatId);
        reminderBot.sendMsg(sendPhoto);
    }

    public void hello(Message message) {
        String text = "Salom\uD83D\uDC4B, nima gap.\nMaza yaxshimi. \n*Navbatchi yaxshi ishlayaptimi?*";
        SendMessage sendMessage = messageUtil.getSendMessage(message, text);
        reminderBot.sendMsg(sendMessage);
    }
}

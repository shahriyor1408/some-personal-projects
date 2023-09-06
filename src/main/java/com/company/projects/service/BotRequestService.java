package com.company.projects.service;

import com.company.projects.ReminderBot;
import com.company.projects.domains.BotUser;
import com.company.projects.repository.BotRepository;
import com.company.projects.util.InlineKeyboardUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 06/09/23 16:32
 * some-personal-projects
 */
@Service
public class BotRequestService {

    private final InlineKeyboardUtil util;
    private final ReminderBot reminderBot;
    private final BotRepository botRepository;

    public BotRequestService(InlineKeyboardUtil util, @Lazy ReminderBot reminderBot, BotRepository botRepository) {
        this.util = util;
        this.reminderBot = reminderBot;
        this.botRepository = botRepository;
    }

    public void handleCallBack(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        User user = callbackQuery.getFrom();
        System.out.println("---------");
        if (data.equals("/change")) {
            System.out.println("*****************");
            DeleteMessage deleteMessage = new DeleteMessage();
            deleteMessage.setMessageId(Math.toIntExact(user.getId()));
            deleteMessage.setChatId(String.valueOf(user.getId()));
            reminderBot.sendMsg(deleteMessage);

            SendMessage sendMessage = new SendMessage();
            sendMessage.setText("Almashtirmoqchi bo'lgan odamni tanlang");
            List<BotUser> users = botRepository.findAll();
            List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
            for (BotUser botUser : users) {
                if (!botUser.getUsername().equals(user.getUserName())) {
                    rowList.add(util.getRow(util.getInlineKeyboardButton(botUser.getUsername(),
                            "/edit/" + botUser.getUsername())));
                }
            }
            if (rowList.isEmpty()) {
                rowList.add(util.getRow(util.getInlineKeyboardButton("Foydalanuvchilar mavjud emas!", "/noData")));
            }
            sendMessage.setReplyMarkup(util.getMarkup(rowList));
        }
    }
}

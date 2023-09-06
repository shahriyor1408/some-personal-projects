package com.company.projects.service;

import com.company.projects.ReminderBot;
import com.company.projects.domains.BotUser;
import com.company.projects.repository.BotRepository;
import com.company.projects.util.InlineKeyboardUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.List;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 06/09/23 13:17
 * some-personal-projects
 */

@Service
public class BotService {

    private final BotRepository botRepository;
    private final ReminderBot reminderBot;

    private final InlineKeyboardUtil util;

    public BotService(BotRepository botRepository, @Lazy ReminderBot reminderBot, InlineKeyboardUtil util) {
        this.botRepository = botRepository;
        this.reminderBot = reminderBot;
        this.util = util;
    }

    @Scheduled(cron = "0 */1 * * * *")
    public void dutyReminder() {
        List<BotUser> users = botRepository.findAll();
        for (BotUser user : users) {
            SendPhoto sendPhoto = new SendPhoto(user.getChatId(),
                    new InputFile("https://media.istockphoto.com/id/879952484/photo/blaming-you-anxious-man-judged-by-different-people-pointing-fingers-at-him-negative-human.webp?b=1&s=170667a&w=0&k=20&c=z0g1MtC5yCYGAJErarxonh11Rst-DqRj73rIwR5LM6A="));
            sendPhoto.setCaption("Bugun siz navbatchisiz!");
            InlineKeyboardMarkup markup = util.getMarkup(util.getRowList(util.getRow(util.getInlineKeyboardButton("Almashtirish", "/change"))));
            sendPhoto.setReplyMarkup(markup);
            reminderBot.sendMsg(sendPhoto);
        }
    }

    public void sendSimpleMessage(Message message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        User user = message.getFrom();
        String text = "Hi, " + user.getFirstName() + " jim o'tiring o'zim bilaman!";
        sendMessage.setText(text);
        reminderBot.sendMsg(sendMessage);
    }

    public void addUser(Message message) {
        User user = message.getFrom();
        Optional<BotUser> userOpt = botRepository.findByUsername(user.getUserName());
        if (userOpt.isPresent()) {
            System.out.println("\n\t" + user.getUserName() + " is already registered!\n\t");
        } else {
            BotUser botUser = BotUser.builder()
                    .username(user.getUserName())
                    .chatId(String.valueOf(message.getChatId()))
                    .userId(String.valueOf(user.getId()))
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .isDuty(false)
                    .orderNumber(-1)
                    .build();
            botRepository.save(botUser);
        }
    }
}

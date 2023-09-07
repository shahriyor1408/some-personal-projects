package com.company.projects.service;

import com.company.projects.ReminderBot;
import com.company.projects.domains.BotUser;
import com.company.projects.repository.BotRepository;
import com.company.projects.util.InlineKeyboardUtil;
import com.company.projects.util.MessageUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 06/09/23 16:32
 * some-personal-projects
 */
@Service
public class BotRequestService {

    private final InlineKeyboardUtil inlineKeyboardUtil;
    private final MessageUtil messageUtil;
    private final ReminderBot reminderBot;
    private final BotRepository botRepository;
    private final BotService botService;

    public BotRequestService(InlineKeyboardUtil inlineKeyboardUtil, MessageUtil messageUtil,
                             @Lazy ReminderBot reminderBot, BotRepository botRepository, BotService botService) {
        this.inlineKeyboardUtil = inlineKeyboardUtil;
        this.messageUtil = messageUtil;
        this.reminderBot = reminderBot;
        this.botRepository = botRepository;
        this.botService = botService;
    }

    public void handleCallBack(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        List<BotUser> users = botRepository.findAll();

        BotUser duty = check(callbackQuery);
        if (Objects.isNull(duty)) {
            System.out.println("Xatolik sodir bo'ldi!");
        } else {
            if (data.equals("/change")) {
                changeCallBack(callbackQuery, users);

            } else if (data.startsWith("/edit")) {
                editCallBack(callbackQuery, data, duty);

            } else if (data.equals("/listDuty")) {
                listDutyCallBack(callbackQuery, users);

            } else if (data.equals("/ok")) {
                DeleteMessage deleteMessage = messageUtil.getDeleteMessage(callbackQuery);
                deleteMessage.setChatId(duty.getChatId());
                reminderBot.sendMsg(deleteMessage);

            } else if (data.equals("/backToDuty")) {
                botService.todayDuty();
            }
        }
    }

    private BotUser check(CallbackQuery callbackQuery) {
        Optional<BotUser> userOpt = botRepository.findByDuty();
        BotUser duty = null;
        if (userOpt.isEmpty()) {
            System.out.println("Bugun navbatchi mavjud emas!");
        } else {
            duty = userOpt.get();
            if (!duty.getUsername().equals(callbackQuery.getFrom().getUserName())) {
                EditMessageText editMessage = messageUtil.getEditMessage(callbackQuery);
                editMessage.setChatId(duty.getChatId());

                editMessage.setText("Almashtirishni faqat navbatchi amalga oshirishi mumkin!");
                InlineKeyboardMarkup markup = inlineKeyboardUtil.getMarkup(inlineKeyboardUtil.getRowList(inlineKeyboardUtil.getRow(inlineKeyboardUtil.getInlineKeyboardButton("OK", "/ok"))));
                editMessage.setReplyMarkup(markup);
                reminderBot.sendMsg(editMessage);
            }
        }
        return duty;
    }

    private void listDutyCallBack(CallbackQuery callbackQuery, List<BotUser> users) {
        reminderBot.sendMsg(messageUtil.getDeleteMessage(callbackQuery));
        SendMessage sendMessage = messageUtil.getSendMessage(callbackQuery);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Navbatchilar ro'yhati: \n\n");
        for (int i = 1; i <= users.size(); i++) {
            Optional<BotUser> orderNumber = botRepository.findByOrderNumber(i);
            if (orderNumber.isEmpty()) {
                System.out.println("Bunday tartib raqamdagi foydalanuvchi topilmadi!");
                return;
            }
            stringBuilder.append(i).append(" -> ").append(orderNumber.get().getUsername()).append("\n");
        }
        sendMessage.setText(stringBuilder.toString());
        InlineKeyboardMarkup markup = inlineKeyboardUtil.getMarkup(inlineKeyboardUtil
                .getRowList(inlineKeyboardUtil.getRow(inlineKeyboardUtil
                        .getInlineKeyboardButton("Orqaga", "/backToDuty"))));
        sendMessage.setReplyMarkup(markup);
        reminderBot.sendMsg(sendMessage);
    }

    private void editCallBack(CallbackQuery callbackQuery, String data, BotUser duty) {
        EditMessageText editMessage = messageUtil.getEditMessage(callbackQuery);
        editMessage.setChatId(duty.getChatId());

        String[] split = data.split("/");
        String username = split[2];
        Optional<BotUser> botUserOpt = botRepository.findByUsername(username);
        if (botUserOpt.isEmpty()) {
            System.out.println("Bunday foydalanuvchi mavjud emas!");
        } else {
            int order = duty.getOrderNumber();
            BotUser botUser = botUserOpt.get();
            duty.setOrderNumber(botUser.getOrderNumber());
            botUser.setOrderNumber(order);
            botRepository.save(botUser);
            botRepository.save(duty);
            editMessage.setText("Almashtirish muvaffaqiyatli amalga ishirildi!");
            InlineKeyboardMarkup markup = inlineKeyboardUtil.getMarkup(inlineKeyboardUtil.getRowList(inlineKeyboardUtil.getRow(inlineKeyboardUtil.getInlineKeyboardButton("OK", "/ok"))));
            editMessage.setReplyMarkup(markup);
            reminderBot.sendMsg(editMessage);
        }
    }

    private void changeCallBack(CallbackQuery callbackQuery, List<BotUser> users) {
        SendMessage sendMessage = messageUtil.getSendMessage(callbackQuery);
        sendMessage.setText("O'zgaritirish uchun foydalanuvchini tanlang:");
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        for (BotUser botUser : users) {
            if (!botUser.getUsername().equals(callbackQuery.getFrom().getUserName())) {
                rowList.add(inlineKeyboardUtil.getRow(inlineKeyboardUtil.getInlineKeyboardButton(botUser.getUsername(),
                        "/edit/" + botUser.getUsername())));
            }
        }
        if (rowList.isEmpty()) {
            rowList.add(inlineKeyboardUtil.getRow(inlineKeyboardUtil.getInlineKeyboardButton("Foydalanuvchilar mavjud emas!", "/noData")));
        }
        rowList.add(inlineKeyboardUtil.getRow(inlineKeyboardUtil.getInlineKeyboardButton("Orqaga", "/backToDuty")));
        sendMessage.setReplyMarkup(inlineKeyboardUtil.getMarkup(rowList));
        reminderBot.sendMsg(sendMessage);
    }
}

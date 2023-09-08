package com.company.projects.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 07/09/23 17:25
 * some-personal-projects
 */
@Component
public class MessageUtil {
    public SendMessage getSendMessage(CallbackQuery callbackQuery) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(callbackQuery.getMessage().getChatId()));
        return sendMessage;
    }

    public SendMessage getSendMessage(Message message, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(message.getChatId()));
        sendMessage.setText(text);
        return sendMessage;
    }

    public DeleteMessage getDeleteMessage(CallbackQuery callbackQuery) {
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setMessageId(callbackQuery.getMessage().getMessageId());
        return deleteMessage;
    }

    public EditMessageText getEditMessage(CallbackQuery callbackQuery) {
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setMessageId(callbackQuery.getMessage().getMessageId());
        return editMessageText;
    }

    public SendPhoto getSendPhoto(String photo, String caption, String chatId) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setPhoto(new InputFile(photo));
        sendPhoto.setCaption(caption);
        return sendPhoto;
    }
}

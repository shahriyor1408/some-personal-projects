package com.company.projects.util;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author "Sohidjonov Shahriyor"
 * @since 06/09/23 15:43
 * some-personal-projects
 */
@Component
public class InlineKeyboardUtil {
    public InlineKeyboardButton getInlineKeyboardButton(String demo, String callBack) {
        InlineKeyboardButton button = new InlineKeyboardButton(demo);
        button.setCallbackData(callBack);
        return button;
    }

    public List<InlineKeyboardButton> getRow(InlineKeyboardButton... row) {
        return new ArrayList<>(Arrays.asList(row));
    }

    @SafeVarargs
    public final List<List<InlineKeyboardButton>> getRowList(List<InlineKeyboardButton>... rows) {
        return new ArrayList<>(Arrays.asList(rows));
    }

    public InlineKeyboardMarkup getMarkup(List<List<InlineKeyboardButton>> rowList) {
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        inlineKeyboardMarkup.setKeyboard(rowList);
        return inlineKeyboardMarkup;
    }
}

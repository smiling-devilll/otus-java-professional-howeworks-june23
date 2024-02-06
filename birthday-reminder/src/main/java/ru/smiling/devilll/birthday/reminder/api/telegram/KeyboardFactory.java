package ru.smiling.devilll.birthday.reminder.api.telegram;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.smiling.devilll.birthday.reminder.domain.ReminderService;
import ru.smiling.devilll.birthday.reminder.model.RemindPeriod;

import java.util.ArrayList;

public class KeyboardFactory {
    public static ReplyKeyboard getBirthdayKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRow.add(new KeyboardButton(TgButton.GET_ALL.getText()));
        keyboardRow.add(new KeyboardButton(TgButton.FIND_BIRTHDAY_BY_NAME.getText()));
        keyboardRow.add(new KeyboardButton(TgButton.ADD_BIRTHDAY.getText()));
        keyboardRow.add(new KeyboardButton(TgButton.SET_REMINDER.getText()));
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboard getReminderKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true); //подгоняем размер
        replyKeyboardMarkup.setOneTimeKeyboard(false); //скрываем после использования

        ArrayList<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        keyboardRows.add(keyboardRow);
        keyboardRow.add(new KeyboardButton(RemindPeriod.DAY_BEFORE.getName()));
        keyboardRow.add(new KeyboardButton(RemindPeriod.SAME_DAY.getName()));
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        return replyKeyboardMarkup;
    }
}

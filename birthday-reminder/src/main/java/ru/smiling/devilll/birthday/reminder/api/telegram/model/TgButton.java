package ru.smiling.devilll.birthday.reminder.api.telegram.model;

import java.util.Arrays;
import java.util.Optional;

public enum TgButton {
    GET_ALL("Всех посмотреть"),
    FIND_BIRTHDAY_BY_NAME("Найти дату рождения по фамилии"),
//    FIND_BIRTHDAY_BY_PATTERN("Напомнить дату рождения "),
    ADD_BIRTHDAY("Добавить в склерозник"),
    SET_REMINDER("Настроить напоминалку");

    private final String text;

    TgButton(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static Optional<TgButton> findByName(String name) {
        return Arrays.stream(TgButton.values()).filter(button -> button.getText().equals(name)).findFirst();
    }
}

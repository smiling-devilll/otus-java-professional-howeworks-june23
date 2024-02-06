package ru.smiling.devilll.birthday.reminder.model;

import java.util.Arrays;
import java.util.Optional;

public enum RemindPeriod {
    DAY_BEFORE("Напоминать накануне"),
    SAME_DAY("Напоминать в тот же день");

    private String name;

    RemindPeriod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Optional<RemindPeriod> findByName(String name) {
        return Arrays.stream(RemindPeriod.values()).filter(period -> period.getName().equals(name)).findFirst();
    }
}

package ru.smiling.devilll.birthday.reminder.model;


import java.time.LocalTime;

public class Settings {
    private LocalTime remindAt;
    private RemindPeriod remindPeriod;

    public Settings() {

    }

    public Settings(LocalTime remindAt) {
        this.remindAt = remindAt;
    }

    public Settings(RemindPeriod remindPeriod) {
        this.remindPeriod = remindPeriod;
    }


    public LocalTime getRemindAt() {
        return remindAt;
    }

    public void setRemindAt(LocalTime remindAt) {
        this.remindAt = remindAt;
    }

    public RemindPeriod getRemindPeriod() {
        return remindPeriod;
    }

    public void setRemindPeriod(RemindPeriod remindPeriod) {
        this.remindPeriod = remindPeriod;
    }
}

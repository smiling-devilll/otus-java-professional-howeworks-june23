package ru.smiling.devilll.birthday.reminder.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

//@Entity
//@TypeDefs(@TypeDef(name = "json", typeClass = JsonBinaryType.class))
public class UserSettings {
    //    @Id
    private long userId;
    private Settings settings;

    public UserSettings() {

    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}

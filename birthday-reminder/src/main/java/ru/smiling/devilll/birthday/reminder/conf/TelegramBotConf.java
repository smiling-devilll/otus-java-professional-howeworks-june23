package ru.smiling.devilll.birthday.reminder.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "telegram")
public class TelegramBotConf {
    private String token;
    private String botName;
    private long creatorId;

    public TelegramBotConf() {

    }

    public String getToken() {
        return token;
    }

    public String getBotName() {
        return botName;
    }

    public void setBotName(String botName) {
        this.botName = botName;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(long creatorId) {
        this.creatorId = creatorId;
    }
}

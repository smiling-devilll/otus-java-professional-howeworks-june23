package ru.smiling.devilll.birthday.reminder.domain.reminder;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.smiling.devilll.birthday.reminder.domain.dao.UserDao;
import ru.smiling.devilll.birthday.reminder.model.RemindPeriod;
import ru.smiling.devilll.birthday.reminder.model.Settings;

import java.time.LocalTime;

@Service
public class ReminderService {
    private final UserDao userDao;
    private final Reminder reminder;

    public ReminderService(UserDao userDao, Reminder reminder) {
        this.userDao = userDao;
        this.reminder = reminder;
    }

    public void setRemindAt(long externalUserId, LocalTime remindAt) {
        var user = userDao.getUserByExternalId(String.valueOf(externalUserId));
        var setting = new Settings(remindAt);

        userDao.saveUserSettings(user.getId(), setting);
    }

    public void setRemindPeriod(long externalUserId, RemindPeriod remindPeriod) {

    }


    @Scheduled
    public void remindOfBirthdays() {
//todo
    }
}

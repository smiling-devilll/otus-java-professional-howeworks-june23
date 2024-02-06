package ru.smiling.devilll.birthday.reminder.domain.dao;

import ru.smiling.devilll.birthday.reminder.model.Settings;
import ru.smiling.devilll.birthday.reminder.model.SourceSystem;
import ru.smiling.devilll.birthday.reminder.model.User;

public interface UserDao {
    long createUser(String name, String externalId, SourceSystem system);
    User getUser(long id);
    User getUserByExternalId(String externalId);
    long saveUserSettings(long userId, Settings settings);
}

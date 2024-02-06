package ru.smiling.devilll.birthday.reminder.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.smiling.devilll.birthday.reminder.domain.dao.UserDao;
import ru.smiling.devilll.birthday.reminder.model.Settings;
import ru.smiling.devilll.birthday.reminder.model.SourceSystem;
import ru.smiling.devilll.birthday.reminder.model.User;

@Service
public class UserService {
    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public long createUser(String name, long externalId, SourceSystem system) {
        return userDao.createUser(name, String.valueOf(externalId), system);
    }

    public User getUserByExternalId(long externalId) {
        return userDao.getUserByExternalId(String.valueOf(externalId));
    }

    public Settings getSettings(long userId) {
        return null;
    }

    public void saveUserSettings(long userId, Settings settings) {

    }
}

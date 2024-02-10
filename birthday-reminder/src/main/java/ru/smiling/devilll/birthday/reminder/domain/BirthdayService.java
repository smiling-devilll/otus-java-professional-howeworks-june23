package ru.smiling.devilll.birthday.reminder.domain;

import org.springframework.stereotype.Service;
import ru.smiling.devilll.birthday.reminder.domain.dao.PersonDao;
import ru.smiling.devilll.birthday.reminder.domain.dao.UserDao;
import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;
import java.util.Optional;

@Service
public class BirthdayService {
    private final UserDao userDao;
    private final PersonDao personDao;

    public BirthdayService(UserDao userDao, PersonDao personDao) {
        this.userDao = userDao;
        this.personDao = personDao;
    }

    public long saveBirthdayEntry(Person person, long externalUserId) {
        var user = userDao.getUserByExternalId(String.valueOf(externalUserId));
        return personDao.savePerson(person, user.getId());
    }

    public Optional<Person> findEntryByLastName(long externalUserId, String lastName) {
        var user = userDao.getUserByExternalId(String.valueOf(externalUserId));
        return personDao.findByLastName(lastName, user.getId());
    }

    public Optional<Person> findEntryByFirstLastNamePattern(long externalUserId, String pattern) {
        var user = userDao.getUserByExternalId(String.valueOf(externalUserId));
        return personDao.findByFirstLastNamePattern(pattern, user.getId());
    }

    public List<Person> getAllForUser(long externalUserId, long offset, long limit) {
        var user = userDao.getUserByExternalId(String.valueOf(externalUserId));
        return personDao.getAllForUser(user.getId(), offset, limit);
    }
}

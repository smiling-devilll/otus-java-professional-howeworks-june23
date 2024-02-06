package ru.smiling.devilll.birthday.reminder.domain.dao;

import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;
import java.util.Optional;

public interface PersonDao {
    long savePerson(Person person, long externalUserId);
    Person getById(long id);
    Optional<Person> findByLastName(String lastNameFull, long userId);
    Optional<Person> findByFirstLastNamePattern(String pattern, long userId);
    List<Person> getAllForUser(long userId, long offset, long limit);
}

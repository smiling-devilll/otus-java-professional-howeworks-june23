package ru.smiling.devilll.birthday.reminder.domain;

import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;

public interface Reminder {
    void remindOfBirthdays(long userId, List<Person> birthdays);
}

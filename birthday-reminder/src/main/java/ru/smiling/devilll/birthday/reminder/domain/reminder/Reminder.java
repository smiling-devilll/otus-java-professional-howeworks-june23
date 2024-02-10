package ru.smiling.devilll.birthday.reminder.domain.reminder;

import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;

public interface Reminder {
    void remindOfBirthdays(long userId, List<Person> birthdays);
}

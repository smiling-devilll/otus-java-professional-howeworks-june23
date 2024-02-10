package ru.smiling.devilll.birthday.reminder.api.telegram;

import ru.smiling.devilll.birthday.reminder.domain.reminder.Reminder;
import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;

public class TgReminder implements Reminder {
    @Override
    public void remindOfBirthdays(long userId, List<Person> birthdays) {

    }
}

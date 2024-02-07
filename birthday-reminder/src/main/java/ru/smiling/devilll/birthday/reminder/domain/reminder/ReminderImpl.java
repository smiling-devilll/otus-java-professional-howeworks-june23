package ru.smiling.devilll.birthday.reminder.domain.reminder;

import org.springframework.stereotype.Service;
import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;

@Service
public class ReminderImpl implements Reminder {
    @Override
    public void remindOfBirthdays(long userId, List<Person> birthdays) {

    }
}

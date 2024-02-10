package ru.smiling.devilll.birthday.reminder.api.telegram;

import ru.smiling.devilll.birthday.reminder.model.Person;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class TgMessageParser {
    public static Person parsePerson(String person) throws IllegalArgumentException {
        var array = person.split(" ", 3);

        if (array.length == 3) {
            try {
                var personObject = new Person();
                personObject.setFirstName(array[0]);
                personObject.setLastName(array[1]);
                var date = LocalDate.parse(array[2]);
                personObject.setBirthday(date);
                return personObject;
            } catch (DateTimeParseException ex) {
                throw new IllegalArgumentException("Не разобрал дату, должна быть в формате 1970-01-01");
            }
        } else {
            throw new IllegalArgumentException("Не получилось разобрать строчку, маловато пробелов что-то");
        }

    }

    public static LocalTime parseTime(String time) throws IllegalArgumentException {
        try {
            return LocalTime.parse(time+":00");
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Не получилось разобрать время, должно быть в формате HH:mm");
        }
    }
}

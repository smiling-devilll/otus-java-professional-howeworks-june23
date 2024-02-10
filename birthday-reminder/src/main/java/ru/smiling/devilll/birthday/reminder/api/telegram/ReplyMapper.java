package ru.smiling.devilll.birthday.reminder.api.telegram;

import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;

public class ReplyMapper {
    public static String personsToString(List<Person> persons) {
        StringBuilder sb = new StringBuilder();
        for (Person p : persons) {
            sb.append(personToString(p)).append("\n");
        }
        return sb.toString();
    }

    public static String personToString(Person person) {
        return "День рождения у " + person.getFirstName() + " " + person.getLastName() + " - " + person.getBirthday().toString();
    }
}

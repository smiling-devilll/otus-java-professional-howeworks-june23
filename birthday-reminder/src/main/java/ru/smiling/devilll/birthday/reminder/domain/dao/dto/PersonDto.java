package ru.smiling.devilll.birthday.reminder.domain.dao.dto;

import jakarta.persistence.*;
import ru.smiling.devilll.birthday.reminder.model.Person;

import java.time.LocalDate;

@Entity(name = "person")
public class PersonDto {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_prs_id_seq")
    @SequenceGenerator(name = "person_prs_id_seq", sequenceName = "person_prs_id_seq", allocationSize = 1)
    private Long personId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "birthday")
    private LocalDate birthday;
    @Column(name = "user_id")
    private Long userId;

    public PersonDto() {

    }

    public PersonDto(Person p, long userId) {
        this.personId = p.getPersonId();
        this.firstName = p.getFirstName();
        this.lastName = p.getLastName();
        this.birthday = p.getBirthday();
        this.userId = userId;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Person toPerson() {
        var p = new Person();
        p.setPersonId(personId);
        p.setBirthday(birthday);
        p.setFirstName(firstName);
        p.setLastName(lastName);
        return p;
    }
}

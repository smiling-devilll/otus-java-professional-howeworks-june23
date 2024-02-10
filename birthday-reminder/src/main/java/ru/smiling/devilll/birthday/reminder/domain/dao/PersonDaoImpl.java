package ru.smiling.devilll.birthday.reminder.domain.dao;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.smiling.devilll.birthday.reminder.domain.dao.dto.PersonDto;
import ru.smiling.devilll.birthday.reminder.model.Person;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class PersonDaoImpl implements PersonDao {
    private final SessionFactory sessionFactory;

    public PersonDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long savePerson(Person person, long externalUserId) {
        var personDto = new PersonDto(person, externalUserId);
        sessionFactory.getCurrentSession().persist(personDto);

        return personDto.getPersonId();
    }

    @Override
    public Person getById(long id) {
        try {
            return sessionFactory.getCurrentSession().createQuery(
                    "select p from person p where p.personId = :id", PersonDto.class
            ).getSingleResult().toPerson();
        } catch (NoResultException ex) {
            throw new RuntimeException("No person with id " + id);
        }
    }

    @Override
    public Optional<Person> findByLastName(String lastNameFull, long userId) {
        try {
            return Optional.of(sessionFactory.getCurrentSession().createQuery(
                            "select p from person p where p.userId = :userId and p.lastName=:lastNameFull", PersonDto.class
                    )
                    .setParameter("userId", userId)
                    .setParameter("lastNameFull", lastNameFull).getSingleResult().toPerson());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Person> findByFirstLastNamePattern(String pattern, long userId) {
        try {
            return Optional.of(sessionFactory.getCurrentSession().createQuery(
                            """
                                    select p from person p 
                                    where p.userId = :userId and 
                                    (p.firstName || ' ' || p.lastName like :pattern or
                                    p.lastName || ' ' || p.firstName like :pattern)
                                    """, PersonDto.class
                    )
                    .setParameter("pattern", pattern)
                    .getSingleResult().toPerson());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Person> getAllForUser(long userId, long offset, long limit) {
        return sessionFactory.getCurrentSession().createQuery(
                        "select p from person p where p.userId = :userId order by p.birthday", PersonDto.class
                )
                .setParameter("userId", userId)
                .getResultStream()
                .map(PersonDto::toPerson)
                .collect(Collectors.toList());
    }
}

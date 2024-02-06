package ru.smiling.devilll.birthday.reminder.domain.dao;

import jakarta.transaction.Transactional;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.smiling.devilll.birthday.reminder.domain.dao.dto.UserDto;
import ru.smiling.devilll.birthday.reminder.model.Settings;
import ru.smiling.devilll.birthday.reminder.model.SourceSystem;
import ru.smiling.devilll.birthday.reminder.model.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long createUser(String name, String externalId, SourceSystem system) {
        var userDto = new UserDto();
        userDto.setName(name);
        userDto.setExternalSystemId(externalId);
        userDto.setSystem(system);
        sessionFactory.getCurrentSession()
                .persist(userDto);
        return userDto.getId();
    }

    @Override
    public User getUser(long id) {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from bot_user u where u.id = :id", UserDto.class)
                .setParameter("id", id)
                .getSingleResult().toUser();
    }

    @Override
    public User getUserByExternalId(String externalId) {
        return sessionFactory.getCurrentSession()
                .createQuery("select u from bot_user u where u.externalSystemId = :externalId", UserDto.class)
                .setParameter("externalId", externalId)
                .getSingleResult().toUser();
    }

    @Override
    public long saveUserSettings(long userId, Settings settings) {
        //todo on update
        return 0;
    }
}

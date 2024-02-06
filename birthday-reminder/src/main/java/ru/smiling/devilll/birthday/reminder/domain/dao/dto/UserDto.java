package ru.smiling.devilll.birthday.reminder.domain.dao.dto;

import jakarta.persistence.*;
import ru.smiling.devilll.birthday.reminder.model.SourceSystem;
import ru.smiling.devilll.birthday.reminder.model.User;

@Entity(name = "bot_user")
public class UserDto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bot_user_usr_id_seq")
    @SequenceGenerator(name = "bot_user_usr_id_seq", sequenceName = "bot_user_usr_id_seq", allocationSize = 1)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "system")
    private SourceSystem system;
    @Column(name = "external_system_id")
    private String externalSystemId;

    public UserDto() {
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.system = user.getSystem();
        this.externalSystemId = user.getExternalSystemId();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SourceSystem getSystem() {
        return system;
    }

    public void setSystem(SourceSystem system) {
        this.system = system;
    }

    public String getExternalSystemId() {
        return externalSystemId;
    }

    public void setExternalSystemId(String externalSystemId) {
        this.externalSystemId = externalSystemId;
    }

    public User toUser() {
        var user = new User();
        user.setId(id);
        user.setName(name);
        user.setSystem(system);
        user.setExternalSystemId(externalSystemId);
        return user;
    }
}

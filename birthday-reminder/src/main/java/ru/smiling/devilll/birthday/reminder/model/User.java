package ru.smiling.devilll.birthday.reminder.model;

public class User {
    private long id;
    private String name;
    private String externalSystemId;
    private SourceSystem system;

    public User() {
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
}

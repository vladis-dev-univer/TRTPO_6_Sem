package by.bsuir.project.entity;

import java.util.Date;
import java.util.Objects;

public class UserInfo extends Entity {
    private User user;
    private String surname;
    private String name;
    private String pseudonym;
    private Level level;
    private Date dateOfBirth;

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public void setPseudonym(String pseudonym) {
        this.pseudonym = pseudonym;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserInfo() {
    }

    public UserInfo(User user, String surname, String name, String pseudonym, Level level, Date dateOfBirth) {
        this.user = user;
        this.surname = surname;
        this.name = name;
        this.pseudonym = pseudonym;
        this.level = level;
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(getUser(), userInfo.getUser()) &&
                Objects.equals(getSurname(), userInfo.getSurname()) &&
                Objects.equals(getName(), userInfo.getName()) &&
                Objects.equals(getPseudonym(), userInfo.getPseudonym()) &&
                getLevel() == userInfo.getLevel() &&
                Objects.equals(getDateOfBirth(), userInfo.getDateOfBirth());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser(), getSurname(), getName(), getPseudonym(), getLevel(), getDateOfBirth());
    }
}

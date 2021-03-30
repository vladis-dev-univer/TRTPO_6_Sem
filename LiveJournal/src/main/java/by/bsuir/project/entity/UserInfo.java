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
    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

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

    public UserInfo(User user, String surname, String name, String pseudonym, Level level, Date dateOfBirth, String imgPath) {
        this.user = user;
        this.surname = surname;
        this.name = name;
        this.pseudonym = pseudonym;
        this.level = level;
        this.dateOfBirth = dateOfBirth;
        this.imgPath = imgPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInfo)) return false;
        if (!super.equals(o)) return false;
        UserInfo userInfo = (UserInfo) o;
        return Objects.equals(user, userInfo.user) &&
                Objects.equals(surname, userInfo.surname) &&
                Objects.equals(name, userInfo.name) &&
                Objects.equals(pseudonym, userInfo.pseudonym) &&
                level == userInfo.level &&
                Objects.equals(dateOfBirth, userInfo.dateOfBirth) &&
                Objects.equals(imgPath, userInfo.imgPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user, surname, name, pseudonym, level, dateOfBirth, imgPath);
    }
}

package by.bsuir.project.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;


@SuppressWarnings("serial")
public class User extends Entity {
    private String login;
    private String password;
    private String email;
    private Role role;
    private Date dateOfReg;
    private boolean isActive;
    private final List<PublicationComment> publicationComments = new ArrayList<>();
    private final List<Publication> publications = new ArrayList<>();

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Date getDateOfReg() {
        return dateOfReg;
    }

    public void setDateOfReg(Date dateOfReg) {
        this.dateOfReg = dateOfReg;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<PublicationComment> getPublicationComments() {
        return publicationComments;
    }

    public List<Publication> getPublications() {
        return publications;
    }

    public User() {
    }

    public User(String login, String password, String email, Role role, Date dateOfReg, boolean isActive) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.role = role;
        this.dateOfReg = dateOfReg;
        this.isActive = isActive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return isActive() == user.isActive() &&
                Objects.equals(getLogin(), user.getLogin()) &&
                Objects.equals(getPassword(), user.getPassword()) &&
                Objects.equals(getEmail(), user.getEmail()) &&
                getRole() == user.getRole() &&
                Objects.equals(getDateOfReg(), user.getDateOfReg()) &&
                Objects.equals(getPublicationComments(), user.getPublicationComments()) &&
                Objects.equals(getPublications(), user.getPublications());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getLogin(), getPassword(), getEmail(), getRole(), getDateOfReg(), isActive(), getPublicationComments(), getPublications());
    }
}

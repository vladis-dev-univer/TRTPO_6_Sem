package by.bsuir.project.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class Publication extends Entity {
    private String name;
    private Date publicDate;
    private String content;
    private Genre genre;
    private User user;
    private final List<PublicationComment> publicationComments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PublicationComment> getPublicationComments() {
        return publicationComments;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Publication() {
    }

    public Publication(String name, Date publicDate, String content, Genre genre, User user) {
        this.name = name;
        this.publicDate = publicDate;
        this.content = content;
        this.genre = genre;
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Publication)) return false;
        if (!super.equals(o)) return false;
        Publication that = (Publication) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPublicDate(), that.getPublicDate()) &&
                Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getGenre(), that.getGenre()) &&
                Objects.equals(getUser(), that.getUser()) &&
                Objects.equals(getPublicationComments(), that.getPublicationComments());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName(), getPublicDate(), getContent(), getGenre(), getUser(), getPublicationComments());
    }
}

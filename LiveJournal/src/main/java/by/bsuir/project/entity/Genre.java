package by.bsuir.project.entity;

import java.util.Objects;

public class Genre extends Entity{
    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre() {
    }

    public Genre(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genre)) return false;
        if (!super.equals(o)) return false;
        Genre genre = (Genre) o;
        return Objects.equals(getTitle(), genre.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTitle());
    }
}

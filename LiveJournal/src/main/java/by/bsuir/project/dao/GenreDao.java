package by.bsuir.project.dao;

import by.bsuir.project.entity.Genre;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface GenreDao extends Dao<Genre> {
    List<Genre> read() throws PersistentException;
}

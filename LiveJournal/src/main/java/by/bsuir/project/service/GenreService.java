package by.bsuir.project.service;


import by.bsuir.project.entity.Genre;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface GenreService extends Service {

    void create(Genre genre) throws PersistentException;

    void save(Genre genre) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    Genre findByIdentity(Integer identity) throws PersistentException;

    List<Genre> findAll() throws PersistentException;
}

package by.bsuir.project.dao;

import by.bsuir.project.entity.Entity;
import by.bsuir.project.exception.PersistentException;

public interface Dao<Type extends Entity> {
    Integer create(Type entity) throws PersistentException;

    Type read(Integer id) throws PersistentException;

    void update(Type entity) throws PersistentException;

    void delete(Integer id) throws PersistentException;
}

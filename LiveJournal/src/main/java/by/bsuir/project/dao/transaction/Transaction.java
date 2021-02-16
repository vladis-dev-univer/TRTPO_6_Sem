package by.bsuir.project.dao.transaction;

import by.vladis.poetrylike.dao.Dao;
import by.vladis.poetrylike.exception.PersistentException;

public interface Transaction {
    <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException;

    void commit() throws PersistentException;

    void rollback() throws PersistentException;
}

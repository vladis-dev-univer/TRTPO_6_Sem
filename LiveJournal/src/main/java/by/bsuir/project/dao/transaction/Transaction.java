package by.bsuir.project.dao.transaction;


import by.bsuir.project.dao.Dao;
import by.bsuir.project.exception.PersistentException;

public interface Transaction {
    <Type extends Dao<?>> Type createDao(Class<Type> key) throws PersistentException;

    void commit() throws PersistentException;

    void rollback() throws PersistentException;
}

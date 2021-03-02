package by.bsuir.project.dao;


import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface UserDao extends Dao<User> {
    User read(String login, String password) throws PersistentException;

    List<User> read() throws PersistentException;

    List<User> readByLogin(String search) throws PersistentException;

    List<User> readSubList(int limit, int offset) throws PersistentException;

    int readRowCount() throws PersistentException;
}

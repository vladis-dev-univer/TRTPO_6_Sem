package by.bsuir.project.service;


import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;

import java.util.List;


public interface UserService extends Service {

    //    void savePassword(User user) throws PersistentException;

    User findByLoginAndPassword(String login, String password) throws PersistentException;

    void saveUpdate(User user) throws PersistentException;

    void create(User user) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    User findByIdentity(Integer identity) throws PersistentException;

    List<User> findByLogin(String search) throws PersistentException;

    List<User> findAll() throws PersistentException;

    List<User> findAllForPagination(int limit, int offset) throws PersistentException;

    public int getRowCountForPagination() throws PersistentException;
}

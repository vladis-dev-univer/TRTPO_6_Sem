package by.bsuir.project.dao;


import by.bsuir.project.dao.Dao;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface PublicationDao extends Dao<Publication> {

    List<Publication> readByUser(Integer userId) throws PersistentException;

    List<Publication> read() throws PersistentException;

    List<Publication> readByName(String search) throws PersistentException;

    List<Publication> readSubList(int limit, int offset) throws PersistentException;

    int readRowCount() throws PersistentException;

    List<Publication> readSubListByUserId(int limit, int offset, Integer userId) throws PersistentException;

    int readRowCountByUserId(Integer userId) throws PersistentException;
}

package by. bsuir.project.service;

import by.bsuir.project.entity.Publication;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface PublicationService extends Service {

    void create(Publication publication) throws PersistentException;

    void save(Publication publication) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    Publication findByIdentity(Integer identity) throws PersistentException;

    List<Publication> findAll() throws PersistentException;

    List<Publication> findByUser(Integer id) throws PersistentException;

    List<Publication> findAllForPagination(int limit, int offset) throws PersistentException;

    int getRowCountForPagination() throws PersistentException;

    List<Publication> findAllForPaginationByUserId(int limit, int offset, Integer id) throws PersistentException;

    int getRowCountForPaginationByUserId(Integer id) throws PersistentException;

    List<Publication> findByName(String search) throws PersistentException;
}

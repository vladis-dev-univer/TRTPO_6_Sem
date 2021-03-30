package by.bsuir.project.service;


import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface PublicationCommentService extends Service {

    void save(PublicationComment publicationComment) throws PersistentException;

    void create(PublicationComment publicationComment) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    PublicationComment findByIdentity(Integer identity) throws PersistentException;

    List<PublicationComment> findByPublication(Integer id) throws PersistentException;
}

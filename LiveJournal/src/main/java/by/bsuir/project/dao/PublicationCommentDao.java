package by.bsuir.project.dao;


import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface PublicationCommentDao extends Dao<PublicationComment> {

    List<PublicationComment> readByPublication(Integer publicId) throws PersistentException;
}

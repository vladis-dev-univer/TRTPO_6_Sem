package by.bsuir.project.service.impl;


import by.bsuir.project.dao.PublicationCommentDao;
import by.bsuir.project.entity.PublicationComment;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.PublicationCommentService;

import java.util.List;

public class PublicationCommentServiceImpl extends ServiceImpl implements PublicationCommentService {

    @Override
    public void save(PublicationComment publicationComment) throws PersistentException {
        PublicationCommentDao publicationCommentDao = transaction.createDao(PublicationCommentDao.class);
        if (publicationComment.getId() != null) {
            publicationCommentDao.update(publicationComment);
        } else {
            publicationComment.setId(publicationCommentDao.create(publicationComment));
        }
    }

    @Override
    public void create(PublicationComment publicationComment) throws PersistentException {
        PublicationCommentDao publicationCommentDao = transaction.createDao(PublicationCommentDao.class);
        publicationCommentDao.create(publicationComment);
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        PublicationCommentDao publicationCommentDao = transaction.createDao(PublicationCommentDao.class);
        publicationCommentDao.delete(identity);
    }

    @Override
    public PublicationComment findByIdentity(Integer identity) throws PersistentException {
        PublicationCommentDao publicationCommentDao = transaction.createDao(PublicationCommentDao.class);
        return publicationCommentDao.read(identity);
    }

    @Override
    public List<PublicationComment> findByPublication(Integer publicId) throws PersistentException {
        PublicationCommentDao publicationCommentDao = transaction.createDao(PublicationCommentDao.class);
        return  publicationCommentDao.readByPublication(publicId);
    }
}

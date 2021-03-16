package by.bsuir.project.service;

import by.bsuir.project.dao.PublicationDao;
import by.bsuir.project.entity.Publication;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.impl.ServiceImpl;

import java.util.List;

public class PublicationServiceImpl extends ServiceImpl implements PublicationService {
    @Override
    public void create(Publication publication) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        dao.create(publication);
    }

    @Override
    public void save(Publication publication) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        if (publication.getId() != null) {
            dao.update(publication);
        } else {
            publication.setId(dao.create(publication));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        dao.delete(identity);
    }

    @Override
    public Publication findByIdentity(Integer identity) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.read(identity);
    }

    @Override
    public List<Publication> findAll() throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.read();
    }

    @Override
    public List<Publication> findByUser(Integer id) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readByUser(id);
    }

    @Override
    public List<Publication> findAllForPagination(int limit, int offset) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readSubList(limit, offset);
    }

    @Override
    public int getRowCountForPagination() throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readRowCount();
    }

    @Override
    public List<Publication> findAllForPaginationByUserId(int limit, int offset, Integer id) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readSubListByUserId(limit, offset, id);
    }

    @Override
    public int getRowCountForPaginationByUserId(Integer id) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readRowCountByUserId(id);
    }

    @Override
    public List<Publication> findByName(String search) throws PersistentException {
        PublicationDao dao = transaction.createDao(PublicationDao.class);
        return dao.readByName(search);
    }

}

package by.bsuir.project.service.impl;

import by.bsuir.project.dao.GenreDao;
import by.bsuir.project.entity.Genre;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.GenreService;

import java.util.List;

public class GenreServiceImpl extends ServiceImpl implements GenreService {

    @Override
    public void create(Genre genre) throws PersistentException {
        GenreDao dao = transaction.createDao(GenreDao.class);
        dao.create(genre);
    }

    @Override
    public void save(Genre genre) throws PersistentException {
        GenreDao dao = transaction.createDao(GenreDao.class);
        if (genre.getId() != null) {
            dao.update(genre);
        } else {
            genre.setId(dao.create(genre));
        }
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        GenreDao dao = transaction.createDao(GenreDao.class);
        dao.delete(identity);
    }

    @Override
    public List<Genre> findAll() throws PersistentException {
        GenreDao dao = transaction.createDao(GenreDao.class);
        return dao.read();
    }

    @Override
    public Genre findByIdentity(Integer identity) throws PersistentException {
        GenreDao dao = transaction.createDao(GenreDao.class);
        return dao.read(identity);
    }

}

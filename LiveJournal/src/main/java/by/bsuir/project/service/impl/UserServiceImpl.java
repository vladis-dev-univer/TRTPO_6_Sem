package by.bsuir.project.service.impl;

import by.bsuir.project.dao.UserDao;
import by.bsuir.project.entity.User;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserService;
import by.bsuir.project.util.UtilPassword;

import java.util.List;

public class UserServiceImpl extends ServiceImpl implements UserService {

    @Override
    public User findByLoginAndPassword(String login, String password) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read(login, UtilPassword.hashPassword(password));
    }

    @Override
    public void saveUpdate(User user) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        dao.update(user);
    }

    @Override
    public void create(User user) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        user.setPassword(UtilPassword.hashPassword(user.getPassword()));
        user.setId(dao.create(user));
    }

    @Override
    public void delete(Integer identity) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        dao.delete(identity);
    }

    @Override
    public User findByIdentity(Integer identity) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read(identity);
    }

    @Override
    public List<User> findByLogin(String search) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.readByLogin(search);
    }

    @Override
    public List<User> findAll() throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.read();
    }

    @Override
    public List<User> findAllForPagination(int limit, int offset) throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.readSubList(limit, offset);
    }

    @Override
    public int getRowCountForPagination() throws PersistentException {
        UserDao dao = transaction.createDao(UserDao.class);
        return dao.readRowCount();
    }

    //    //for change password
//    @Override
//    public void savePassword(User user) throws PersistentException {
//        UserDao dao = transaction.createDao(UserDao.class);
//        if(user.getId() != null){
//            if(user.getPassword() != null){
//                user.setPassword(user.getPassword());
//            } else {
//                User oldUser = dao.read(user.getId());
//                user.setPassword(oldUser.getPassword());
//            }
//            dao.update(user);
//        } else {
//            user.setPassword("");
//            user.setId(dao.create(user));
//        }
//    }

}

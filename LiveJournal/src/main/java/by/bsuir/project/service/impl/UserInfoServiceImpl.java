package by.bsuir.project.service.impl;


import by.bsuir.project.dao.UserInfoDao;
import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;
import by.bsuir.project.service.UserInfoService;

import java.util.List;

public class UserInfoServiceImpl extends ServiceImpl implements UserInfoService {
    @Override
    public void create(UserInfo userInfo) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.create(userInfo);
    }

    @Override
    public void save(UserInfo userInfo) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        if(userInfo.getId() != null) {
            dao.update(userInfo);
        } else {
            userInfo.setId(dao.create(userInfo));
        }
    }

    @Override
    public void saveUpdate(UserInfo userInfo) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.update(userInfo);
    }


    @Override
    public void delete(Integer identity) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        dao.delete(identity);
    }

    @Override
    public UserInfo findByIdentity(Integer identity) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.read(identity);
    }

    @Override
    public UserInfo findByUserId(Integer userId) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readByUserId(userId);
    }

    @Override
    public List<UserInfo> findAll() throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.read();
    }

    @Override
    public List<UserInfo> findAllForPagination(int limit, int offset) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readSubList(limit, offset);
    }

    @Override
    public int getRowCountForPagination() throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readRowCount();
    }

    @Override
    public List<UserInfo> findAllForPaginationByLevel(int limit, int offset, String search) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readSubListByLevel(limit, offset, search);
    }

    @Override
    public int getRowCountForPaginationByLevel(String search) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readRowCountByLevel(search);
    }

    @Override
    public List<UserInfo> findAllForPaginationByUserId(int limit, int offset, Integer userId) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readSubListByUserId(limit, offset, userId);
    }

    @Override
    public int getRowCountForPaginationByUserId(Integer userId) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readRowCountByUserId(userId);
    }

    @Override
    public List<UserInfo> findByName(String search) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readByName(search);
    }

    @Override
    public List<UserInfo> findByPseudonym(String search) throws PersistentException {
        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
        return dao.readByPseudonym(search);
    }
//
//    @Override
//    public List<UserInfo> findByLevel(String search) throws PersistentException {
//        UserInfoDao dao = transaction.createDao(UserInfoDao.class);
//        return dao.readByLevel(search);
//    }

}

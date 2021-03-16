package by.bsuir.project.dao;



import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface UserInfoDao extends Dao<UserInfo> {
    List<UserInfo> read() throws PersistentException;

    List<UserInfo> readSubList(int limit, int offset) throws PersistentException;

    int readRowCount() throws PersistentException;

    List<UserInfo> readSubListByLevel(int limit, int offset, String search) throws PersistentException;

    int readRowCountByLevel(String search) throws PersistentException;

    List<UserInfo> readSubListByUserId(int limit, int offset, Integer userId) throws PersistentException;

    int readRowCountByUserId(Integer userId) throws PersistentException;

    UserInfo readByUserId(Integer userId) throws PersistentException;

    List<UserInfo> readByName(String search) throws PersistentException;

    List<UserInfo> readByPseudonym(String search) throws PersistentException;

//    List<UserInfo> readByLevel(String search) throws PersistentException;

}

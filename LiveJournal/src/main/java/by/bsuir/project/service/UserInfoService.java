package by.bsuir.project.service;


import by.bsuir.project.entity.UserInfo;
import by.bsuir.project.exception.PersistentException;

import java.util.List;

public interface UserInfoService extends Service {

    void create(UserInfo userInfo) throws PersistentException;

    void save(UserInfo userInfo) throws PersistentException;

    void saveUpdate(UserInfo userInfo) throws PersistentException;

    void delete(Integer identity) throws PersistentException;

    UserInfo findByIdentity(Integer identity) throws PersistentException;

    UserInfo findByUserId(Integer userId) throws PersistentException;

    List<UserInfo> findAll() throws PersistentException;

    List<UserInfo> findAllForPagination(int limit, int offset) throws PersistentException;

    int getRowCountForPagination() throws PersistentException;

    List<UserInfo> findAllForPaginationByLevel(int limit, int offset, String search) throws PersistentException;

    int getRowCountForPaginationByLevel(String search) throws PersistentException;

    List<UserInfo> findAllForPaginationByUserId(int limit, int offset, Integer userId) throws PersistentException;

    int getRowCountForPaginationByUserId(Integer userId) throws PersistentException;

    List<UserInfo> findByName(String search) throws PersistentException;

    List<UserInfo> findByPseudonym(String search) throws PersistentException;

//    List<UserInfo> findByLevel(String search) throws PersistentException;
}

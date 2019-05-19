package com.challenge.dao;

import com.challenge.exception.DAOException;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tmaldonado on 29/06/2017.
 */

public interface DAO<T,ID extends Serializable> {
    T create() throws DAOException;
    T saveOrUpdate(T entity) throws DAOException;
    T get(ID id) throws DAOException;
    void delete(ID id) throws DAOException;
    List<T> findAll() throws DAOException;
    List<T> findAll(T example) throws  DAOException;
    void clearSession();
    public Class<T> getEntityClass();
}

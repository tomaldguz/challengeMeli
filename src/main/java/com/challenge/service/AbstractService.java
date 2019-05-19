package com.challenge.service;

import com.challenge.dao.DAO;

import com.challenge.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.challenge.exception.ServiceException.Code.OBJECT_NOT_FOUND;
import static com.challenge.exception.ServiceException.TRANS_ARG;

public abstract class AbstractService<T , D extends DAO<T, Long> > {

    D dao;

    public AbstractService(D dao) {
        this.dao = dao;
    }

    // read - one
    @Transactional(readOnly = true)
    public T findOne(final long id) {
        T t = dao.get(id);
        if ( t == null ) {
            throw new ServiceException(OBJECT_NOT_FOUND, HttpStatus.NOT_FOUND, "exception.title.notfound","exception.detail.object.notfound", TRANS_ARG + dao.getEntityClass().getSimpleName());
        }
        return t;
    }

    // read - one
    @Transactional(readOnly = true)
    public T findOneIfExists(final long id) {
        return dao.get(id);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(T example) {
        return dao.findAll(example);
    }

    // read - all
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return dao.findAll();
    }

    // write
    @Transactional
    public T save(final T entity) throws Exception {
        return dao.saveOrUpdate(entity);
    }

    @Transactional
    public void delete(final long entityId) {
        dao.delete(entityId);
    }

    public void clearSession() {
        this.dao.clearSession();
    }
}

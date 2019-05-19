package com.challenge.dao.hibernate;

import com.challenge.dao.DAO;
import com.challenge.exception.DAOException;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Logger;

import static com.challenge.exception.ServiceException.TRANS_ARG;

/**
 * Created by tmaldonado on 29/06/2017.
 */
@Repository
public class DAOImp<T, ID extends Serializable> implements DAO<T, ID> {

    @Autowired
    private SessionFactory sessionFactory;

    private final static Logger LOGGER = Logger.getLogger(DAOImp.class.getSimpleName());

    @Override
    public T create() {
        try {
            return getEntityClass().newInstance();
        } catch (InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public T saveOrUpdate(T entity) throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.saveOrUpdate(entity);
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException(he, "exception.detail.internal.save", TRANS_ARG + getEntityClass().getSimpleName());
        }
        return entity;
    }

    @Override
    public T get(ID id) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            T entity = (T) session.get(getEntityClass(), id);
            return entity;
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException(he, "exception.detail.internal.get", TRANS_ARG + getEntityClass().getSimpleName(), id);
        }
    }

    @Override
    public void delete(ID id) throws DAOException {
        Session session = sessionFactory.getCurrentSession();
        try {
            T entity = (T) session.get(getEntityClass(), id);
            if (entity != null) {
                session.delete(entity);
            }
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException(he, "exception.detail.internal.delete", TRANS_ARG + getEntityClass().getSimpleName(), id);
        }
    }

    @Override
    public List<T> findAll() throws DAOException{
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery("SELECT e FROM " + getEntityClass().getSimpleName() + " e");
            List<T> entities = query.list();
            return entities;
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException( he, "exception.detail.internal.findall", TRANS_ARG + getEntityClass().getSimpleName());
        }
    }

    @Override
    public List<T> findAll(T example) throws DAOException{
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(example.getClass()).add(Example.create(example));
            List<T> entities = criteria.list();
            return entities;
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException( he, "exception.detail.internal.findall", TRANS_ARG + getEntityClass().getSimpleName());
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public Class<T> getEntityClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void clearSession() {
        sessionFactory.getCurrentSession().clear();
    }

}

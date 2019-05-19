package com.challenge.dao.hibernate;

import com.challenge.dao.SearchDAO;
import com.challenge.exception.DAOException;
import com.challenge.model.Search;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.challenge.exception.ServiceException.TRANS_ARG;

@Repository
public class SearchDAOImp extends DAOImp<Search, Long> implements SearchDAO {

    public Collection<Search> findLike(String term){
        try {
            Session session = getSessionFactory().getCurrentSession();
            String strQuery = "select s from Search s  where upper(s.term) LIKE :term";

            Query query = session.createQuery(strQuery);
            query.setParameter("term",term.toUpperCase() + "%");
            query.setMaxResults(100);

            List<Search> entities = query.list();
            return entities;
        } catch (HibernateException he) {
            he.printStackTrace();
            throw new DAOException("exception.detail.internal.findall", TRANS_ARG + getEntityClass().getName());
        }
    }

}

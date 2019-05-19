package com.challenge.service;

import com.challenge.dao.SearchDAO;
import com.challenge.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Component
public class SearchService extends AbstractService<Search, SearchDAO> {

    @Autowired
    SearchDAO dao;

    public SearchService(SearchDAO dao) {
        super(dao);
    }

    @Transactional
    public Collection<Search> findLike(final String term) throws Exception {
        Collection<Search> searches = dao.findLike(term);
        return searches;
    }

}

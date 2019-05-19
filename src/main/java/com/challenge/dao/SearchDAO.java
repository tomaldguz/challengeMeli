package com.challenge.dao;

import com.challenge.model.Search;

import java.util.Collection;

public interface SearchDAO extends DAO<Search, Long>  {

    public Collection<Search> findLike(String term);
}

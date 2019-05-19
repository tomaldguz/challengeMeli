package com.challenge.controller;

import com.challenge.exception.ServiceException;
import com.challenge.model.Search;
import com.challenge.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static com.challenge.exception.ServiceException.Code.MISSING_PARAMETER;


@RestController
public class SearchController {

    @Autowired
    SearchService service;

    @RequestMapping(value = "/api/search", method = RequestMethod.POST)
    public Search save(@RequestBody Search search) throws Exception {
        Collection<Search> found = service.findAll(search);
        if (  found.size() > 0 ) {
            return found.iterator().next();
        }
        search.setTerm(search.getTerm().toLowerCase());
        return service.save(search);
    }

    @RequestMapping(value = "/api/search/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.GET)
    public Collection<Search> search(@RequestParam(required = false) String term) throws Exception {
        if (term == null) {
            throw new ServiceException(MISSING_PARAMETER, HttpStatus.EXPECTATION_FAILED, "exception.title.missingParameter", "exception.detail.missingParameter", "term");
        }
        return service.findLike(term);
    }
}

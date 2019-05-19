package com.challenge.controller;

import com.challenge.model.Search;
import com.challenge.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


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
        return service.save(search);
    }

    @RequestMapping(value = "/api/search/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) throws Exception {
        service.delete(id);
    }

    @RequestMapping(value = "/api/search", method = RequestMethod.GET)
    public Collection<Search> search(@RequestParam String term) throws Exception {
        return service.findLike(term);
    }
}

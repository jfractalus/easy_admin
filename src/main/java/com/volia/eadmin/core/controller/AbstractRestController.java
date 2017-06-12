package com.volia.eadmin.core.controller;

import com.google.common.collect.Lists;
import com.volia.eadmin.domain.AbstractEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Slf4j
@RestController
@RequestMapping("/rest")
public abstract class AbstractRestController<T extends AbstractEntity, R extends PagingAndSortingRepository<T, Long>> {
    @Autowired
    private R repository;

    @RequestMapping(value = "/rest", method = GET)
    public List<T> getAllRest() {
        return Lists.newArrayList(repository.findAll());
    }

    @RequestMapping(value = "/rest/{id}", method = GET)
    public T getByIdRest(@PathVariable long id) {
        return repository.findOne(id);
    }

    @RequestMapping(value = "/rest", method = {POST, PUT})
    public T updateEntityRest(@RequestBody T entity){
        return repository.save(entity);
    }

    @RequestMapping(value = "/rest", method = DELETE)
    public void deleteByIdRest(@RequestBody long id){
        repository.delete(id);
    }

}


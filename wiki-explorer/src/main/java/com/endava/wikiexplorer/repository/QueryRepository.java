package com.endava.wikiexplorer.repository;

import com.endava.wikiexplorer.entity.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aciurea on 8/17/2016.
 */
@Repository
public interface QueryRepository extends CrudRepository<Query, Integer> {

    Query findByTitlesIgnoreCase(String titles);
}

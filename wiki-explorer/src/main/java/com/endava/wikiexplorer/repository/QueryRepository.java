package com.endava.wikiexplorer.repository;

import com.endava.wikiexplorer.model.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aciurea on 8/17/2016.
 */
@Repository
public interface QueryRepository extends CrudRepository<Query, Long> {

    Query findByTitles(String titles);
}

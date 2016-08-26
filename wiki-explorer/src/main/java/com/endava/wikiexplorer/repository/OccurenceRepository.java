package com.endava.wikiexplorer.repository;

import com.endava.wikiexplorer.model.Occurrence;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aciurea on 8/17/2016.
 */
@Repository
public interface OccurenceRepository extends CrudRepository<Occurrence,Integer> {
}

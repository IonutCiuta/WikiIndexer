package com.endava.wikiexplorer.repository;

import com.endava.wikiexplorer.model.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Table;

/**
 * Created by aciurea on 8/17/2016.
 */
@Repository
@Table(name="Query")
public interface QueryRepository extends CrudRepository<Query,Long>{
}

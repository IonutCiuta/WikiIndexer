package com.endava.wikiexplorer.repository;

import com.endava.wikiexplorer.entity.Word;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by aciurea on 8/17/2016.
 */
@Repository
public interface WordRepository extends CrudRepository<Word,Integer> {
    Word findByValue(String value);
}

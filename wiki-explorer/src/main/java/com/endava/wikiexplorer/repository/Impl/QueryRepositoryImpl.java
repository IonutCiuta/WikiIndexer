package com.endava.wikiexplorer.repository.Impl;

import com.endava.wikiexplorer.model.Query;
import com.endava.wikiexplorer.repository.QueryRepository;

import java.util.Iterator;

/**
 * Created by aciurea on 8/18/2016.
 */
public abstract class QueryRepositoryImpl implements QueryRepository {
    @Override
    public Query findByTitles(String titles) {
        Iterator iterator= (Iterator) findAll();
        while(iterator.hasNext()){
            Query query= (Query) iterator.next();
            if(query.getTitles().equals(titles)){
                return query;
            }
        }
        return null;
    }
}

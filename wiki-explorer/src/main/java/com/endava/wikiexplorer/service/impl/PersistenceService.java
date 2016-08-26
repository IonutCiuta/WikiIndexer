package com.endava.wikiexplorer.service.impl;


import com.endava.wikiexplorer.entity.Query;

/**
 * Ionut Ciuta on 8/26/2016.
 */
public interface PersistenceService {
    Query findQuery(String query);
    void saveQuery(Query query);
}

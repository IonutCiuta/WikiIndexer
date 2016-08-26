package com.endava.wikiexplorer.service.impl;


import com.endava.wikiexplorer.entity.Analysis;

/**
 * Ionut Ciuta on 8/26/2016.
 */
public interface PersistenceService {
    Analysis findQuery(String query);
    void saveQuery(Analysis analysis);
}

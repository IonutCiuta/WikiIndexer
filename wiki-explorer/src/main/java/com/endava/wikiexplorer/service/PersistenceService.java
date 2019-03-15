package com.endava.wikiexplorer.service;


import com.endava.wikiexplorer.entity.Analysis;

/**
 * Ionut Ciuta on 8/26/2016.
 */
public interface PersistenceService {
    Analysis findAnalysis(String query);
    void saveAnalysis(Analysis analysis);
}

package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.util.WikiContentAnalysis;

/**
 * Ionut Ciuta on 8/11/2016.
 */

public interface WikiArticleService {

    WikiContentAnalysis manageRequest(String titles);

    void addDbContent(WikiContentAnalysis wikiContentAnalysis);

    WikiContentAnalysis requestDbContent(String titles);

    WikiDTO requestWikiContent(String titles);


    WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO);

}

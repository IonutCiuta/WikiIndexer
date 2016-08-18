package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.util.WikiContentAnalysis;

/**
 * Ionut Ciuta on 8/11/2016.
 */

public interface WikiArticleService {

    public void addDbContent(WikiContentAnalysis wikiContentAnalysis);

    public WikiContentAnalysis requestDbContent(String titles);

    public WikiDTO requestWikiContent(String titles);

    public WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO);

}

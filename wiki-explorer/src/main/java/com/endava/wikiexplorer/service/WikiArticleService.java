package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.util.WikiContentAnalysis;

/**
 * Created by aciurea on 8/17/2016.
 */
public interface WikiArticleService {

    //public WikiDTO requestDbContent(String titles);

    public WikiContentAnalysis getWikiContent(String titles);

    public WikiDTO requestWikiContent(String titles);

    public WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO);
}

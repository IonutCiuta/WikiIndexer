package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Analysis;

/**
 * Ionut Ciuta on 8/26/2016.
 */
public interface WikiService {
    WikiDTO requestWikiContent(String queryTitles);
    WikiDTO requestRandomWikiContent();
    Analysis analyzeWikiContent(WikiDTO wikiDTO);
}

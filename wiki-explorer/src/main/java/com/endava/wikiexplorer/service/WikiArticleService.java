package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.util.WikiContentAnalysis;
import com.endava.wikiexplorer.util.WikiContentAnalyzer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * Ionut Ciuta on 8/11/2016.
 */

public interface WikiArticleService {

    public WikiContentAnalysis requestDbContent(String titles);

    public WikiDTO requestWikiContent(String titles);

    public WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO);

}

package com.endava.wikiexplorer.service.Impl;

import com.endava.wikiexplorer.dto.Occurrence;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.service.WikiArticleService;
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
@Service
public class WikiArticleServiceImpl implements WikiArticleService {
    private final Logger log = Logger.getLogger(WikiArticleService.class);

    @Value("${wiki.api.url}")
    private String wikiURL;

    public WikiContentAnalysis getWikiContent(String titles) {
        //todo check database
        return analyzeWikiContent(requestWikiContent(titles));
    }

    public WikiDTO requestWikiContent(String titles) {
        log.info("Requesting Wikipedia article: " + titles);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(wikiURL + titles, WikiDTO.class);
    }

    public WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO) {
        log.info("Analyzing articles: " + wikiDTO.getQueryTitles());
        WikiContentAnalysis analysis = new WikiContentAnalysis();

        long start = System.currentTimeMillis();
        List<Occurrence> occurrences = WikiContentAnalyzer.analyzeArticlesParallel(wikiDTO.getArticles());
        long end = System.currentTimeMillis();

        analysis.setArticleTitle(wikiDTO.getQueryTitles());
        analysis.setTopOccurrences(occurrences);
        analysis.setAnalysisTime(end - start);
        return analysis;
    }
}

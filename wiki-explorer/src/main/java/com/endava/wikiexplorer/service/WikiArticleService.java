package com.endava.wikiexplorer.service;

import com.endava.wikiexplorer.dto.WikiArticleAnalyticsDTO;
import com.endava.wikiexplorer.dto.WikiArticleDTO;
import com.endava.wikiexplorer.util.ArticleAnalysis;
import com.endava.wikiexplorer.util.WikiContentAnalyzer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@Service
public class WikiArticleService {
    private final Logger log = Logger.getLogger(WikiArticleService.class);

    public WikiArticleDTO requestWikiArticle(String title) {
        log.info("Requesting Wikipedia article: " + title);
        RestTemplate restTemplate = new RestTemplate();
        //todo
        return restTemplate.getForObject("https://en.wikipedia.org/w/api.php?action=query&titles=Snail&prop=revisions&rvprop=content&format=json", WikiArticleDTO.class);
    }

    public WikiArticleAnalyticsDTO analyzeArticle(WikiArticleDTO wikiArticle) {
        ArticleAnalysis articleAnalysis = WikiContentAnalyzer.analyzeArticleContent(wikiArticle);
        articleAnalysis.displayAnalysis();
        return null;
    }
}

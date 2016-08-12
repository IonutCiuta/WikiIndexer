package com.endava.wikiexplorer.controller;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.service.WikiArticleService;
import com.endava.wikiexplorer.util.WikiContentAnalysis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@RestController
public class ArticleDataController {
    private final Logger log = Logger.getLogger(ArticleDataController.class);

    @Autowired
    private WikiArticleService wikiArticleService;

    @RequestMapping("/article/{titles}")
    public void getStatistics(@PathVariable String titles) {
        log.info("Requested data for article: " + titles);
        WikiDTO wikiDTO = wikiArticleService.requestWikiContent(titles);
        WikiContentAnalysis wikiContentAnalysis = wikiArticleService.analyzeWikiContent(wikiDTO);
        wikiContentAnalysis.displayAnalysis();
    }
}

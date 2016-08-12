package com.endava.wikiexplorer.controller;

import com.endava.wikiexplorer.dto.WikiArticleDTO;
import com.endava.wikiexplorer.service.WikiArticleService;
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

    @RequestMapping("/article/{title}")
    public void getStatistics(@PathVariable String title) {
        log.info("Requested data for article: " + title);
        WikiArticleDTO wikiArticleDTO = wikiArticleService.requestWikiArticle(title);
        wikiArticleService.analyzeArticle(wikiArticleDTO);
    }
}

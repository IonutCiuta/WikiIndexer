package com.endava.wikiexplorer.controller;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.service.WikiArticleService;
import com.endava.wikiexplorer.util.WikiContentAnalysis;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@RestController
public class ArticleDataController {
    private final Logger log = Logger.getLogger(ArticleDataController.class);

    @Autowired
    private WikiArticleService wikiArticleService;

    /*@RequestMapping(name = "/article/{titles}", method = RequestMethod.GET)
    public void getStatistics(@PathVariable String titles) {
        log.info("GET /article/" + titles);
        WikiDTO wikiDTO = wikiArticleService.requestWikiContent(titles);
        WikiContentAnalysis wikiContentAnalysis = wikiArticleService.analyzeWikiContent(wikiDTO);
        wikiContentAnalysis.displayAnalysis();
    }*/

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public void getStatistics(@RequestParam(value = "titles") String titles) {
        log.info("GET /article/" + titles);
        WikiDTO wikiDTO = wikiArticleService.requestWikiContent(titles);
        WikiContentAnalysis wikiContentAnalysis = wikiArticleService.analyzeWikiContent(wikiDTO);
        //wikiContentAnalysis.displayAnalysis();
    }
}

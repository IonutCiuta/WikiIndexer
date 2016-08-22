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

    /**
     *
     * @param titles should be Title1|Title2|Title3
     * @return
     */
    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public WikiContentAnalysis getStatistics(@RequestParam(value = "titles") String titles) {
        log.info("GET /article/" + titles);
        WikiContentAnalysis wikiContentAnalysis=null;
        try{
            log.info("Looking in DB...");
            wikiContentAnalysis=wikiArticleService.requestDbContent(titles.replace("|",", "));
            log.info("Found "+ titles + " in DB");
        }catch (NullPointerException e){
            WikiDTO wikiDTO = wikiArticleService.requestWikiContent(titles);
            log.info(titles + " not in DB. Adding...");
            wikiContentAnalysis=wikiArticleService.analyzeWikiContent(wikiDTO);
            wikiArticleService.addDbContent(wikiContentAnalysis);
        }
        return wikiContentAnalysis;
    }

    @RequestMapping(value = "/article/random", method = RequestMethod.GET)
    public WikiContentAnalysis getRandomStatistics() {
        log.info("GET /article/random");
        WikiDTO wikiDTO = wikiArticleService.requestWikiContent();
        WikiContentAnalysis wikiContentAnalysis=null;
        log.info("Requesting random article");
        wikiContentAnalysis=wikiArticleService.analyzeWikiContent(wikiDTO);
        return wikiContentAnalysis;
    }
}

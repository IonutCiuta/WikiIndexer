package com.endava.wikiexplorer.controller.rest;

import com.endava.wikiexplorer.dto.AnalysisDTO;
import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Analysis;
import com.endava.wikiexplorer.entity.Occurrence;
import com.endava.wikiexplorer.service.PersistenceService;
import com.endava.wikiexplorer.service.WikiService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${wiki.api.url}")
    private String wikiURL;

    @Value("${wiki.api.url.random}")
    private String randomURL;

    @Autowired
    private WikiService wikiService;

    @Autowired
    private PersistenceService persistenceService;

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public AnalysisDTO getStatistics(@RequestParam(value = "titles") String titles, @RequestParam(value = "ignoreCommon") Boolean ignoreCommon) {
        log.info("GET /article/" + titles);
        Analysis analysis = persistenceService.findAnalysis(titles);

        if(analysis == null) {
            log.info("getStatistics: " + titles + " not cached");
            WikiDTO wikiDTO = wikiService.requestWikiContent(titles);
            analysis = wikiService.analyzeWikiContent(wikiDTO);
            persistenceService.saveAnalysis(analysis);
        }

        return convert(analysis);
    }

    @RequestMapping(value = "/article/random", method = RequestMethod.GET)
    public AnalysisDTO getRandomStatistics() {
        log.info("GET /article/random");
        WikiDTO wikiDTO = wikiService.requestRandomWikiContent();
        Analysis analysis = wikiService.analyzeWikiContent(wikiDTO);

        if(persistenceService.findAnalysis(analysis.getTitles()) == null) {
            persistenceService.saveAnalysis(analysis);
        }

        return convert(analysis);
    }

    private AnalysisDTO convert(Analysis analysis) {
        AnalysisDTO result = new AnalysisDTO();
        result.setTitles(analysis.getTitles());
        result.setLength(analysis.getLength());

        for(Occurrence occurrence : analysis.getOccurrences()) {
            result.addOccurrence(new OccurrenceDTO(occurrence.getWord().getValue(), occurrence.getFrequency()));
        }

        return result;
    }
}

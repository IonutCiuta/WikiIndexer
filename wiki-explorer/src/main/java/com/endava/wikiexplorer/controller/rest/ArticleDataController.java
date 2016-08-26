package com.endava.wikiexplorer.controller.rest;

import com.endava.wikiexplorer.dto.AnalysisDTO;
import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Occurrence;
import com.endava.wikiexplorer.entity.Query;
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

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public AnalysisDTO getStatistics(@RequestParam(value = "titles") String titles, @RequestParam(value = "ignoreCommon") Boolean ignoreCommon) {
        log.info("GET /article/" + titles);
        WikiDTO wikiDTO = wikiService.requestWikiContent(titles);
        Query query = wikiService.analyzeWikiContent(wikiDTO);
        return convert(query);
    }

    @RequestMapping(value = "/article/random", method = RequestMethod.GET)
    public AnalysisDTO getRandomStatistics() {
        log.info("GET /article/random");
        WikiDTO wikiDTO = wikiService.requestRandomWikiContent();
        Query query = wikiService.analyzeWikiContent(wikiDTO);
        return convert(query);
    }

    private AnalysisDTO convert(Query query) {
        AnalysisDTO result = new AnalysisDTO();
        result.setTitles(query.getTitles());
        result.setLength(query.getLength());

        for(Occurrence occurrence : query.getOccurrences()) {
            result.addOccurrence(new OccurrenceDTO(occurrence.getWord().getValue(), occurrence.getFrequency()));
        }

        return result;
    }
}

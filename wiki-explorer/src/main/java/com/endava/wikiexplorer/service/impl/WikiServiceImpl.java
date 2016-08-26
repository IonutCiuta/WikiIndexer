package com.endava.wikiexplorer.service.impl;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Query;
import com.endava.wikiexplorer.service.WikiService;
import com.endava.wikiexplorer.util.WikiContentAnalyzer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Ionut Ciuta on 8/26/2016.
 */
@Service
@Primary
public class WikiServiceImpl implements WikiService {
    private Logger log = Logger.getLogger(WikiServiceImpl.class);

    @Value("${wiki.api.url}")
    private String apiURL;

    @Value("${wiki.api.url.random}")
    private String randomURL;

    @Autowired
    private WikiContentAnalyzer wikiContentAnalyzer;

    @Override
    public WikiDTO requestWikiContent(String queryTitles) {
        log.info("requestWikiContent: " + queryTitles);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiURL + queryTitles, WikiDTO.class);
    }

    @Override
    public WikiDTO requestRandomWikiContent() {
        log.info("requestRandomWikiContent");
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(randomURL, WikiDTO.class);
    }

    @Override
    public Query analyzeWikiContent(WikiDTO wikiDTO) {
        log.info("analyzeWikiContent");
        return wikiContentAnalyzer.analyzeContent(wikiDTO);
    }
}

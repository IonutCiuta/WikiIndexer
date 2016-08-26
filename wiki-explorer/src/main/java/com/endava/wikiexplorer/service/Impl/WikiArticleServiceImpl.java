package com.endava.wikiexplorer.service.Impl;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Occurrence;
import com.endava.wikiexplorer.entity.Query;
import com.endava.wikiexplorer.entity.Word;
import com.endava.wikiexplorer.repository.OccurenceRepository;
import com.endava.wikiexplorer.repository.QueryRepository;
import com.endava.wikiexplorer.repository.WordRepository;
import com.endava.wikiexplorer.service.WikiArticleService;
import com.endava.wikiexplorer.util.DTOService;
import com.endava.wikiexplorer.util.WikiContentAnalysis;
import com.endava.wikiexplorer.util.WikiContentAnalyzer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by aciurea on 8/18/2016.
 */
@Service
public class WikiArticleServiceImpl implements WikiArticleService {
    private final Logger log = Logger.getLogger(WikiArticleService.class);

    @Value("${wiki.api.url.random}")
    private String randomURL;

    @Autowired
    private WikiContentAnalyzer wikiContentAnalyzer;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private OccurenceRepository occurenceRepository;

    @Autowired
    private WordRepository wordRepository;

    public WikiContentAnalysis manageRequest(String url) {
        String titles = url.substring(url.lastIndexOf('=') + 1);
        WikiContentAnalysis wikiContentAnalysis = null;
        try {
            if (url.equals(randomURL)) {
                throw new NullPointerException();
            }

            log.info("Looking in DB...");
            wikiContentAnalysis = requestDbContent(titleDbFormat(titles));
            log.info("Found " + titles + " in DB");

        } catch (NullPointerException e) {
            WikiDTO wikiDTO = requestWikiContent(url);
            log.info(titles + " not in DB. Adding...");
            wikiContentAnalysis = analyzeWikiContent(wikiDTO);
            addDbContent(wikiContentAnalysis);
        }
        return wikiContentAnalysis;
    }

    //todo incomplete
    public WikiContentAnalysis requestDbContent(String titles) {
        titles = titleDbFormat(titles);
        Query query = queryRepository.findByTitlesIgnoreCase(titles);
        WikiContentAnalysis wikiContentAnalysis = new WikiContentAnalysis();
        wikiContentAnalysis.setAnalysisTime(query.getLength());
        wikiContentAnalysis.setArticleTitle(titles);
        List<Occurrence> occurrences = (List) query.getOccurrences();
        wikiContentAnalysis.setTopOccurrences(DTOService.toDto(occurrences));
        return wikiContentAnalysis;
    }

    @Transactional
    public void addDbContent(WikiContentAnalysis wikiContentAnalysis) {
        Query query = new Query();
        query.setTitles(titleDbFormat(wikiContentAnalysis.getArticleTitle()));
        System.out.println(wikiContentAnalysis.getArticleTitle());
        query.setLength(wikiContentAnalysis.getAnalysisTime());
        queryRepository.save(query);

        List<OccurrenceDTO> occurrenceDTOs = wikiContentAnalysis.getTopOccurrences();
        for (OccurrenceDTO occurrenceDTO : occurrenceDTOs) {
            Word word = new Word(occurrenceDTO.getWord());

            //add word to db if not exists
            if (wordRepository.findByValue(occurrenceDTO.getWord()) == null) {
                wordRepository.save(word);
            }
            Occurrence occurrence = new Occurrence(query, wordRepository.findByValue(occurrenceDTO.getWord()), occurrenceDTO.getFrequency());
            System.out.println(occurrence.toString());
            occurenceRepository.save(occurrence);
        }
    }

    public WikiDTO requestWikiContent(String url) {
        log.info("Requesting Wikipedia article: " + url);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, WikiDTO.class);
    }

    public WikiContentAnalysis analyzeWikiContent(WikiDTO wikiDTO) {
        log.info("Analyzing articles: " + wikiDTO.getQueryTitles());
        WikiContentAnalysis analysis = new WikiContentAnalysis();

        long start = System.currentTimeMillis();
        List<OccurrenceDTO> occurrences = wikiContentAnalyzer.analyzeArticlesParallel(wikiDTO.getArticles());
        long end = System.currentTimeMillis();

        analysis.setArticleTitle(wikiDTO.getQueryTitles());
        analysis.setTopOccurrences(occurrences);
        analysis.setAnalysisTime(end - start);
        return analysis;
    }

    public String titleDbFormat(String title) {
        String[] titles = title.split("\\|");
        Arrays.sort(titles);
        StringBuilder sb = new StringBuilder();
        sb.append(titles[0]);
        for (int i = 1; i < titles.length; i++) {
            sb.append(", " + titles[i]);
        }
        return sb.toString();
    }
}

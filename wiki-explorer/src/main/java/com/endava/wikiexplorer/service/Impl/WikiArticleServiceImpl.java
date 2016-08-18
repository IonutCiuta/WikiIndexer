package com.endava.wikiexplorer.service.Impl;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.model.Occurence;
import com.endava.wikiexplorer.model.Query;
import com.endava.wikiexplorer.model.Word;
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

import java.util.List;

/**
 * Created by aciurea on 8/18/2016.
 */
@Service
public class WikiArticleServiceImpl implements WikiArticleService {
    private final Logger log = Logger.getLogger(WikiArticleService.class);

    @Value("${wiki.api.url}")
    private String wikiURL;

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private OccurenceRepository occurenceRepository;

    @Autowired
    private WordRepository wordRepository;

    //todo incomplete
    public WikiContentAnalysis requestDbContent(String titles) {
        Query query=queryRepository.findByTitles(titles);
        WikiContentAnalysis wikiContentAnalysis=new WikiContentAnalysis();

        wikiContentAnalysis.setAnalysisTime(query.getTimeMilis());
        wikiContentAnalysis.setArticleTitle(titles);
        List<Occurence> occurences=(List)query.getOccurences();
        wikiContentAnalysis.setTopOccurrences(DTOService.toDto(occurences));

        return wikiContentAnalysis;
    }

    @Transactional
    public void addDbContent(WikiContentAnalysis wikiContentAnalysis){
        Query query=new Query();
        query.setTitles(wikiContentAnalysis.getArticleTitle());
        query.setTimeMilis(wikiContentAnalysis.getAnalysisTime());
        queryRepository.save(query);

        List<OccurrenceDTO> occurrenceDTOs=wikiContentAnalysis.getTopOccurrences();
        for(OccurrenceDTO occurrenceDTO:occurrenceDTOs){
            Word word=new Word(occurrenceDTO.getWord());
            wordRepository.save(word);
            Occurence occurence=new Occurence(query,word,occurrenceDTO.getFrequency());
            System.out.println(occurence.toString());
            occurenceRepository.save(occurence);
        }
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
        List<OccurrenceDTO> occurrences = WikiContentAnalyzer.analyzeArticlesParallel(wikiDTO.getArticles());
        long end = System.currentTimeMillis();

        analysis.setArticleTitle(wikiDTO.getQueryTitles());
        analysis.setTopOccurrences(occurrences);
        analysis.setAnalysisTime(end - start);
        return analysis;
    }
}

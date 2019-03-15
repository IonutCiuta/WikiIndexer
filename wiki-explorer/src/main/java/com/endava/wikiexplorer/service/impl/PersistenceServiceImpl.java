package com.endava.wikiexplorer.service.impl;

import com.endava.wikiexplorer.entity.Analysis;
import com.endava.wikiexplorer.entity.Word;
import com.endava.wikiexplorer.repository.AnalysisRepository;
import com.endava.wikiexplorer.repository.WordRepository;
import com.endava.wikiexplorer.service.PersistenceService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ionut Ciuta on 8/26/2016.
 */
@Service
@Transactional
public class PersistenceServiceImpl implements PersistenceService {
    Logger log = Logger.getLogger(PersistenceService.class);

    @Autowired
    private AnalysisRepository analysisRepository;

    @Autowired
    private WordRepository wordRepository;

    @Override
    public Analysis findAnalysis(String query) {
        return analysisRepository.findByTitlesIgnoreCase(query);
    }

    @Override
    public void saveAnalysis(Analysis analysis) {
        log.info("saveAnalysis: " + analysis.getTitles());
        ensureUniqueOccurrences(analysis);
        analysisRepository.save(analysis);
    }

    private void ensureUniqueOccurrences(Analysis analysis) {
        for(int i = 0; i < analysis.getOccurrences().size(); i++) {
            Word word = wordRepository.findByValue(analysis.getOccurrences().get(i).getWord().getValue());
            if(word != null) {
                log.info("ensureUniqueOccurrences found already existing: " + word.getValue());
                analysis.getOccurrences().get(i).setWord(word);
            }
        }
    }
}

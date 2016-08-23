package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.wiki.WikiArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@Component
public class WikiContentAnalyzer {

    @Value("${words.top.count}")
    private Integer topWordsCount;

    @Value("#{'${words.common}'.split(',')}")
    private List<String> commonWordsList;

    @Autowired
    private WikiContentParser wikiContentParser;

    /**
     * Analyzes articles in a serial way
     * @param articles list of articles receives from the Wikipedia API
     * @return a complete analysis of the article
     */
    public List<OccurrenceDTO> analyzeArticlesSerial(List<WikiArticle> articles) {
        Map<Integer, String[]> articleWordsMap = wikiContentParser.parseArticlesSerial(articles);
        Map<String, Integer> wordCountMap = new HashMap<>();

        for(int key : articleWordsMap.keySet()) {
            countArticleWords(articleWordsMap.get(key), wordCountMap);
        }

        return getTopOccuringWords(wordCountMap);
    }

    /**
     * Analyzes articles in a parallel way
     * @param articles list of articles receives from the Wikipedia API
     * @return a complete analysis of the article
     */
    public List<OccurrenceDTO> analyzeArticlesParallel(List<WikiArticle> articles) {
        ConcurrentHashMap<Integer, String[]> articleWordsMap = wikiContentParser.parseArticlesParallel(articles);
        ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();
        ExecutorService counterExecutor = Executors.newFixedThreadPool(4);

        for(int key : articleWordsMap.keySet()) {
            Runnable counterThread = new CounterThread(key, articleWordsMap.get(key), wordCountMap);
            counterExecutor.execute(counterThread);
        }

        counterExecutor.shutdown();
        while(!counterExecutor.isTerminated()) {

        }

        return getTopOccuringWords(wordCountMap);
    }

    /**
     * Counts articleWords from an arrays of strings; stores result in a map (can be HashMap or ConcurrentHashMap)
     * @param articleWords arrays of articleWords from article
     * @param accumulator word - no. of appearances association
     */
    private void countArticleWords(String[] articleWords, Map<String, Integer> accumulator) {
        for(String word : articleWords) {
            String lowerWord = word.toLowerCase();

            if(!accumulator.containsKey(lowerWord)) {
                accumulator.put(lowerWord, 1);
            } else {
                int previousCount = accumulator.get(lowerWord);
                accumulator.put(lowerWord, ++previousCount);
            }
        }
    }

    /**
     * Sorts a map of word - count pairs and selects top 10 occurring words
     * @param unsortedWordCountMap input map
     * @return list of top 10 occurrences
     */
    private List<OccurrenceDTO> getTopOccuringWords(Map<String, Integer> unsortedWordCountMap) {
        List<OccurrenceDTO> topOccurences = new ArrayList<>();
        Set<String> commonWordsSet = new HashSet<>(commonWordsList);
        List<Map.Entry<String, Integer>> sortedOccurrences = new LinkedList<>(unsortedWordCountMap.entrySet());

        sortedOccurrences.sort((word1, word2) -> word1.getValue().compareTo(word2.getValue()));

        int wordIndex = sortedOccurrences.size() - 1;
        int selectedWords = 0;

        while(selectedWords < topWordsCount && wordIndex > 0) {
            Map.Entry<String, Integer> currentOccurence = sortedOccurrences.get(wordIndex);
            String currentWord = currentOccurence.getKey();

            if(!commonWordsSet.contains(currentWord)) {
                topOccurences.add(new OccurrenceDTO(currentWord, currentOccurence.getValue()));
                selectedWords++;
            }

            wordIndex--;
        }

        return topOccurences;
    }

    /**
     * Thread that counts word appearances in an article; uses a ConcurrentHashMap as accumulator since it needs
     * synchronization
     */
    private class CounterThread implements Runnable {
        private int tid;
        private String[] articleWords;
        private ConcurrentHashMap<String, Integer> accumulator;

        CounterThread(int tid, String[] words, ConcurrentHashMap<String, Integer> accumulator) {
            this.tid = tid;
            this.articleWords = words;
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            countArticleWords(articleWords, accumulator);
        }
    }
}

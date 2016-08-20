package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.wiki.WikiArticle;
import org.springframework.beans.factory.annotation.Value;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiContentAnalyzer {

    @Value("${wiki.commonWords}")
    static String commonWords;

    /**
     * Analyzes articles in a serial way
     * @param articles list of articles receives from the Wikipedia API
     * @return a complete analysis of the article
     */
    public static List<OccurrenceDTO> analyzeArticlesSerial(List<WikiArticle> articles) {
        Map<Integer, String[]> articleWordsMap = WikiContentParser.parseArticlesSerial(articles);
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
    public static List<OccurrenceDTO> analyzeArticlesParallel(List<WikiArticle> articles) {
        ConcurrentHashMap<Integer, String[]> articleWordsMap = WikiContentParser.parseArticlesParallel(articles);
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
    private static void countArticleWords(String[] articleWords, Map<String, Integer> accumulator) {
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
    private static List<OccurrenceDTO> getTopOccuringWords(Map<String, Integer> unsortedWordCountMap) {
        List<Map.Entry<String, Integer>> sortedOccurrences = new LinkedList<>(unsortedWordCountMap.entrySet());
        sortedOccurrences.sort((word1, word2) -> word1.getValue().compareTo(word2.getValue()));

        List<OccurrenceDTO> topOccurences = new ArrayList<>();
        int startIndex = sortedOccurrences.size() - 1;

        for(int i = 0; i < 10; i++) {
            Map.Entry<String, Integer> current = sortedOccurrences.get(startIndex - i);

//            if(!commonWords.contains(current.getKey())){
//                //// TODO: 8/19/2016
//            }
            System.out.println("commonWords "+commonWords);
            topOccurences.add(new OccurrenceDTO(current.getKey(),current.getValue()));
        }

        return topOccurences;
    }

    /**
     * Thread that counts word appearances in an article; uses a ConcurrentHashMap as accumulator since it needs
     * synchronization
     */
    private static class CounterThread implements Runnable {
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

package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.wiki.WikiArticle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiContentAnalyzer {

    /**
     * Analyzes articles in a serial way
     * @param articles list of articles receives from the Wikipedia API
     * @return a complete analysis of the article
     */
    public static WikiContentAnalysis analyzeArticlesSerial(List<WikiArticle> articles) {
        Map<Integer, String[]> articleWordsMap = WikiContentParser.parseArticlesSerial(articles);
        Map<String, Integer> wordCountMap = new HashMap<>();

        for(int key : articleWordsMap.keySet()) {
            countArticleWords(articleWordsMap.get(key), wordCountMap);
        }

        return new WikiContentAnalysis(wordCountMap);
    }

    /**
     * Analyzes articles in a parallel way
     * @param articles list of articles receives from the Wikipedia API
     * @return a complete analysis of the article
     */
    public static WikiContentAnalysis analyzeArticlesParallel(List<WikiArticle> articles) {
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

        WikiContentAnalysis analysis = new WikiContentAnalysis(wordCountMap);
        return analysis;
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
            System.out.println("CounterThread " + tid + " started execution");
            countArticleWords(articleWords, accumulator);
            System.out.println("CounterThread " + tid + " finished execution");
        }
    }
}

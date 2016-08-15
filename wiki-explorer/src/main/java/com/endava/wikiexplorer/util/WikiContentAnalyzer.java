package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.WikiDTO;
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
    private static final String SPACE = " ";

    private static Map<String, Integer> countArticleWords(String articleText) {
        Map<String, Integer> wordCounter = new HashMap<>();
        String[] words = articleText.split(SPACE);

        for(String word : words) {
            String lowerWord = word.toLowerCase();

            if(!wordCounter.containsKey(lowerWord)) {
                wordCounter.put(lowerWord, 1);
            } else {
                int previousCount = wordCounter.get(lowerWord);
                wordCounter.put(lowerWord, ++previousCount);
            }
        }

        return wordCounter;
    }

    public static WikiContentAnalysis analyzeContentSerial(WikiDTO wikiDTO) {
        String cleanText = WikiContentParser.parse(wikiDTO.getWikiFormatContent());
        return new WikiContentAnalysis(countArticleWords(cleanText));
    }

    public static WikiContentAnalysis analyzeContentThreaded(List<WikiArticle> articles) {
        ConcurrentHashMap<Integer, String[]> articleWords = new ConcurrentHashMap<>();
        ExecutorService parsingExecutor = Executors.newFixedThreadPool(4);

        for(int i = 0; i < articles.size(); i++) {
            Runnable parsingThread = new ParsingThread(i, articles.get(i), articleWords);
            parsingExecutor.execute(parsingThread);
        }
        parsingExecutor.shutdown();
        while(!parsingExecutor.isTerminated()) {

        }

        ConcurrentHashMap<String, Integer> wordCounter = new ConcurrentHashMap<>();
        ExecutorService counterExecutor = Executors.newFixedThreadPool(4);

        for(int key : articleWords.keySet()) {
            Runnable counterThread = new CounterThread(key, articleWords.get(key), wordCounter);
            counterExecutor.execute(counterThread);
        }
        counterExecutor.shutdown();
        while(!counterExecutor.isTerminated()) {

        }

        WikiContentAnalysis analysis = new WikiContentAnalysis(wordCounter);
        return analysis;
    }

    private static class ParsingThread implements Runnable {
        private int tid;
        private WikiArticle article;
        private ConcurrentHashMap<Integer, String[]> accumulator;

        ParsingThread(int tid, WikiArticle article, ConcurrentHashMap<Integer, String[]> accumulator) {
            this.tid = tid;
            this.article = article;
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            System.out.println("ParsingThread " + tid + " started execution");
            String articleText = WikiContentParser.parse(article.getWikiEncodedContent());
            String[] articleWords = articleText.split(SPACE);
            accumulator.put(tid, articleWords);
            System.out.println("ParsingThread " + tid + " finished execution");
        }
    }

    private static class CounterThread implements Runnable {
        private int tid;
        private String[] words;
        private ConcurrentHashMap<String, Integer> accumulator;

        CounterThread(int tid, String[] words, ConcurrentHashMap<String, Integer> accumulator) {
            this.tid = tid;
            this.words = words;
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            System.out.println("CounterThread " + tid + " started execution");
            for(String word : words) {
                String lowerWord = word.toLowerCase();

                if(!accumulator.containsKey(lowerWord)) {
                    accumulator.put(lowerWord, 1);
                } else {
                    int previousCount = accumulator.get(lowerWord);
                    accumulator.put(lowerWord, ++previousCount);
                }
            }
            System.out.println("CounterThread " + tid + " finished execution");
        }
    }
}

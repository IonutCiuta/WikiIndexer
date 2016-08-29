package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.entity.Analysis;
import com.endava.wikiexplorer.entity.Occurrence;
import com.endava.wikiexplorer.entity.Word;
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
     * @param content
     * @return
     */
    public Analysis analyzeContent(WikiDTO content) {
        long start, end;
        Analysis analysis = new Analysis();
        ConcurrentHashMap<Integer, String[]> articleWordsMap = wikiContentParser.parseArticlesParallel(content.getArticles());
        ConcurrentHashMap<String, Integer> wordCountMap = new ConcurrentHashMap<>();

        start = System.currentTimeMillis();
        ExecutorService counterExecutor = Executors.newFixedThreadPool(4);
        for(int key : articleWordsMap.keySet()) {
            Runnable counterThread = new CounterThread(key, articleWordsMap.get(key), wordCountMap);
            counterExecutor.execute(counterThread);
        }

        counterExecutor.shutdown();
        while(!counterExecutor.isTerminated()) {

        }

        List<Occurrence> topOccurrences = getTopOccurringWords(analysis, wordCountMap);
        end = System.currentTimeMillis();

        analysis.setTitles(content.getQueryTitles());
        analysis.setLength(end - start);
        analysis.setOccurrences(topOccurrences);

        return analysis;
    }

    /**
     * counts words from an articles; stores in common accumulator (synchronized)
     * @param articleWords array of words
     * @param accumulator map of word - appearances
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
     * sorts accumulator of words; returns top uncommon words
     * @param unsortedWordCountMap
     * @return
     */
    private List<Occurrence> getTopOccurringWords(Analysis analysis, Map<String, Integer> unsortedWordCountMap) {
        List<Occurrence> topOccurrences = new ArrayList<>();
        Set<String> commonWordsSet = new HashSet<>(commonWordsList);
        List<Map.Entry<String, Integer>> sortedOccurrences = new LinkedList<>(unsortedWordCountMap.entrySet());

        sortedOccurrences.sort((word1, word2) -> word1.getValue().compareTo(word2.getValue()));

        int wordIndex = sortedOccurrences.size() - 1;
        int selectedWords = 0;

        while(selectedWords < topWordsCount && wordIndex > 0) {
            Map.Entry<String, Integer> currentOccurence = sortedOccurrences.get(wordIndex);
            String currentWord = currentOccurence.getKey();
            Integer currentFrequency = currentOccurence.getValue();

            if(!commonWordsSet.contains(currentWord)) {
                topOccurrences.add(new Occurrence(analysis, new Word(currentWord), currentFrequency));
                selectedWords++;
            }

            wordIndex--;
        }

        return topOccurrences;
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

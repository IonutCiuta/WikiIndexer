package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.OccurrenceDTO;
import com.endava.wikiexplorer.dto.WikiDTO;
import com.endava.wikiexplorer.dto.wiki.WikiArticle;
import com.endava.wikiexplorer.entity.Occurrence;
import com.endava.wikiexplorer.entity.Query;
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
     * Analyzes a list of articles in a serial manner; intermediary results are stored in a hash map
     * @param articles list of articles
     * @return list of top occuring words
     */
    @Deprecated
    public List<OccurrenceDTO> analyzeArticlesSerial(List<WikiArticle> articles) {
        Map<Integer, String[]> articleWordsMap = wikiContentParser.parseArticlesSerial(articles);
        Map<String, Integer> wordCountMap = new HashMap<>();

        for(int key : articleWordsMap.keySet()) {
            countArticleWords(articleWordsMap.get(key), wordCountMap);
        }

        /*return getTopOccurringWords(wordCountMap);*/
        return null;
    }

    /**
     * @param content
     * @return
     */
    public Query analyzeContent(WikiDTO content) {
        long start, end;
        Query query = new Query();
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

        List<Occurrence> topOccurrences = getTopOccurringWords(query, wordCountMap);
        end = System.currentTimeMillis();

        query.setTitles(content.getQueryTitles());
        query.setLength(end - start);
        query.setOccurrences(topOccurrences);

        return query;
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
    private List<Occurrence> getTopOccurringWords(Query query, Map<String, Integer> unsortedWordCountMap) {
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
                topOccurrences.add(new Occurrence(query, new Word(currentWord), currentFrequency));
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

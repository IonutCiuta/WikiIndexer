package com.endava.wikiexplorer.util;

import java.util.HashMap;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class ArticleAnalysis {
    private final Integer TOP_WORDS_COUNT = 10;
    private final boolean ACCEPTED = true;
    private final boolean DENIED = false;

    private HashMap<String, Integer> topWords;
    private String leastFrequent, nextLessFrequent;

    public ArticleAnalysis() {
        topWords = new HashMap<>();
    }

    public boolean addWord(String word, Integer count) {
        boolean result = false;

        if(topWords.size() < TOP_WORDS_COUNT) {
            result = true;
            topWords.put(word, count);

            if(count <= getMinFrequency()) {
                leastFrequent = word;
            }
        } else {
            if(count >= getMinFrequency()) {
                result = true;
                topWords.remove(leastFrequent);
                leastFrequent = getLeastFrequent();
                topWords.put(word, count);
            }
        }

        return result;
    }

    private Integer getMinFrequency() {
        if(leastFrequent != null) {
            return topWords.get(leastFrequent);
        } else {
            return 0;
        }
    }

    private String getLeastFrequent() {
        int minCount = Integer.MAX_VALUE;
        String minWord = leastFrequent;

        for(String word : topWords.keySet()) {
            int currentCount = topWords.get(word);
            if(currentCount < minCount) {
                minCount = currentCount;
                minWord = word;
            }
        }

        return minWord;
    }

    public HashMap<String, Integer> getTopWords() {
        return topWords;
    }
}

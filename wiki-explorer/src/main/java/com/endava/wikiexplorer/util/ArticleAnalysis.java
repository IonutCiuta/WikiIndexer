package com.endava.wikiexplorer.util;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class ArticleAnalysis {
    private Map<String, Integer> wordCounter;

    public ArticleAnalysis(Map<String, Integer> unorderedWordCounter) {
        this.wordCounter = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String word1, String word2) {
                return unorderedWordCounter.get(word1).compareTo(unorderedWordCounter.get(word2));
            }
        });

        wordCounter.putAll(unorderedWordCounter);
    }

    public void displayAnalysis() {
        for(String word : wordCounter.keySet()) {
            System.out.println(word + " [" + wordCounter.get(word) + "] ");
        }
    }
}

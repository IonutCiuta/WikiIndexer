package com.endava.wikiexplorer.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiContentAnalysis {
    private  List<Map.Entry<String, Integer>> sortedWordCount;

    public WikiContentAnalysis(Map<String, Integer> unorderedWordCounter) {
        sortData(unorderedWordCounter);
    }

    private void sortData(Map<String, Integer> unsortedWordCounter) {
        sortedWordCount = new LinkedList<>(unsortedWordCounter.entrySet());
        sortedWordCount.sort((word1, word2) -> word1.getValue().compareTo(word2.getValue()));
    }

    public Map<String, Integer> getWordsStatisticsAsMap() {
        Map<String, Integer> result = new TreeMap<>();
        for(Map.Entry<String, Integer> wordCount : sortedWordCount) {
            result.put(wordCount.getKey(), wordCount.getValue());
        }
        return result;
    }

    public List<Map.Entry<String, Integer>> getWordsStatisticsAsList() {
        return sortedWordCount;
    }

    public void displayAnalysis() {
        for(Map.Entry<String, Integer> wordCount : sortedWordCount) {
            System.out.println(wordCount.getKey() + " [" + wordCount.getValue() + "] ");
        }
    }
}

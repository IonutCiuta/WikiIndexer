package com.endava.wikiexplorer.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Ionut Ciuta on 8/11/2016.
 */
//todo delete all and use TreeMap
public class WikiContentAnalyzer {
    private static final String SPACE = " ";

    public static Map<String, Integer> countArticleWords(String articleText) {
        Map<String, Integer> wordCounter = new HashMap<>();
        String[] words = articleText.split(SPACE);

        for(String word : words) {
            String lowerWord = word.toLowerCase();

            if(!word.contains(lowerWord)) {
                wordCounter.put(lowerWord, 1);
            } else {
                int previousCount = wordCounter.get(word);
                wordCounter.put(lowerWord, ++previousCount);
            }
        }

        return wordCounter;
    }

    public static ArticleAnalysis computeTopWords(Map<String, Integer> wordCounter) {
        ArticleAnalysis analysis = new ArticleAnalysis();
        for(String word : wordCounter.keySet()) {
            analysis.addWord(word, wordCounter.get(word));
        }
        return analysis;
    }
}

package com.endava.wikiexplorer.util;


import com.endava.wikiexplorer.dto.OccurrenceDTO;

import java.io.Serializable;
import java.util.*;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiContentAnalysis implements Serializable{
    private String articleTitle;
    private Long analysisTime;
    private List<OccurrenceDTO> topOccurrences;

    public WikiContentAnalysis() {
        topOccurrences = new ArrayList<>();
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Long getAnalysisTime() {
        return analysisTime;
    }

    public void setAnalysisTime(Long analysisTime) {
        this.analysisTime = analysisTime;
    }

    public List<OccurrenceDTO> getTopOccurrences() {
        return topOccurrences;
    }

    public void setTopOccurrences(List<OccurrenceDTO> topOccurrences) {
        this.topOccurrences = topOccurrences;
    }

    public void displayAnalysis() {
        topOccurrences.forEach(System.out::println);
    }
}

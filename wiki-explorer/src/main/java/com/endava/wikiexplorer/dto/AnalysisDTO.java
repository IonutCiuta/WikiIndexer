package com.endava.wikiexplorer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ionut Ciuta on 8/26/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AnalysisDTO implements Serializable {
    private String titles;
    private Long length;
    private List<OccurrenceDTO> occurrences;

    public AnalysisDTO() {
        this.occurrences = new ArrayList<>();
    }

    public List<OccurrenceDTO> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<OccurrenceDTO> occurrences) {
        this.occurrences = occurrences;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Long getLength() {
        return length;
    }

    public void setLength(Long length) {
        this.length = length;
    }

    public void addOccurrence(OccurrenceDTO occurrence) {
        this.occurrences.add(occurrence);
    }
}

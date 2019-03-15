package com.endava.wikiexplorer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/16/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OccurrenceDTO implements Serializable {
    private String word;
    private Integer frequency;

    public OccurrenceDTO() {
    }

    public OccurrenceDTO(String word, Integer frequency) {
        this.word = word;
        this.frequency = frequency;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Override
    public String toString() {
        return word + " [" + frequency + "]";
    }
}

package com.endava.wikiexplorer.dto;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/16/2016.
 */
public class Occurrence implements Serializable {
    private String word;
    private Integer frequency;

    public Occurrence(String word, Integer frequency) {
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

package com.endava.wikiexplorer.entity;

import javax.persistence.*;

/**
 * Created by aciurea on 8/17/2016.
 */

@Entity
@Table(name = "occurrence")
public class Occurrence extends AbstractEntity{
    @Column
    Integer frequency;

    @ManyToOne
    @JoinColumn(name="query_id")
    private Analysis analysis;

    @ManyToOne
    @JoinColumn(name="word_id")
    private Word word;

    public Occurrence(){

    }

    public Occurrence(Analysis analysis, Word word, Integer frequency) {
        this.frequency = frequency;
        this.analysis = analysis;
        this.word = word;
    }

    public Analysis getAnalysis() {
        return analysis;
    }

    public void setAnalysis(Analysis analysis) {
        this.analysis = analysis;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
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
        return "Occurrence{" +
                "analysis=" + analysis +
                ", word=" + word +
                ", frequency=" + frequency +
                '}';
    }
}

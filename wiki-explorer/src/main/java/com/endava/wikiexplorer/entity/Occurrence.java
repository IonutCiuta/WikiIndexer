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
    private Query query;

    @ManyToOne
    @JoinColumn(name="word_id")
    private Word word;

    public Occurrence(){

    }

    public Occurrence(Query query, Word word, Integer frequency) {
        this.frequency = frequency;
        this.query = query;
        this.word = word;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
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
                "query=" + query +
                ", word=" + word +
                ", frequency=" + frequency +
                '}';
    }
}

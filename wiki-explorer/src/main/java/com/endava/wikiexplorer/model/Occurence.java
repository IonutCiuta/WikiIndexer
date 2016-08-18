package com.endava.wikiexplorer.model;

import javax.persistence.*;

/**
 * Created by aciurea on 8/17/2016.
 */

@Entity
@Table(name = "Occurences")
public class Occurence {

    @Id
    Long id;

    @ManyToOne
    @JoinColumn(name="query_id")
    private Query query;

    @ManyToOne
    @JoinColumn(name="word_id")
    Word word;

    Long frequency;

    @Override
    public String toString() {
        return "Occurence{" +
                "query=" + query +
                ", word=" + word +
                ", frequency=" + frequency +
                '}';
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

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }
}

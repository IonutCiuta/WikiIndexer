package com.endava.wikiexplorer.model;

import javax.persistence.*;

/**
 * Created by aciurea on 8/17/2016.
 */

@Entity
@Table(name = "occurences")
public class Occurence {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name="query_id")
    private Query query;

    @ManyToOne
    @JoinColumn(name="word_id")
    Word word;

    Integer frequency;

    public Occurence(){

    }

    public Occurence(Query query, Word word,int frequency){
        this.frequency=frequency;
        this.word=word;
        this.query=query;
    }

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

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }
}

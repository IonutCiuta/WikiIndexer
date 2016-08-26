package com.endava.wikiexplorer.entity;

import javax.persistence.*;

/**
 * Created by aciurea on 8/17/2016.
 */
@Entity
@Table(name="word")
public class Word extends AbstractEntity{
    @Column
    private String value;

    public Word(){

    }

    public Word(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }
}

package com.endava.wikiexplorer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by aciurea on 8/17/2016.
 */
@Entity
public class Word {

    @Id
    Long id;

    String value;

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {

        return id;
    }

    public String getValue() {
        return value;
    }
}

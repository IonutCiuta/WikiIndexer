package com.endava.wikiexplorer.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by aciurea on 8/17/2016.
 */

@Entity
public class Occurence {

    @Id
    Long id;

    Long frequency;

    @Override
    public String toString() {
        return "Occurence{" +
                "id=" + id +
                ", frequency=" + frequency +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFrequency() {
        return frequency;
    }

    public void setFrequency(Long frequency) {
        this.frequency = frequency;
    }
}

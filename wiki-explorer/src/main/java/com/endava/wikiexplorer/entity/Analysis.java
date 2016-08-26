package com.endava.wikiexplorer.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by aciurea on 8/17/2016.
 */
@Entity
@Table(name="analysis")
public class Analysis extends AbstractEntity{
    @Column(name = "query_titles")
    private String titles;

    @Column(name = "query_length")
    private Long length;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "query_id")
    private Collection<Occurrence> occurrences;

    public Analysis() {
        this.occurrences = new ArrayList<>();
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

    public Collection<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Collection<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public void addOccurence(Occurrence occurrence) {
        occurrences.add(occurrence);
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "id=" + id +
                ", titles=" + titles +
                '}';
    }
}

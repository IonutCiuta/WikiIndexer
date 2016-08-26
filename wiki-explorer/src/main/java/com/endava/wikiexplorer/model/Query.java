package com.endava.wikiexplorer.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by aciurea on 8/17/2016.
 */
@Entity
@Table(name="query")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String titles;

    @Column(name="time")
    private Long timeMilis;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "query_id")
    private Collection<Occurrence> occurrences;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Long getTimeMilis() {
        return timeMilis;
    }

    public void setTimeMilis(Long timeMilis) {
        this.timeMilis = timeMilis;
    }

    public Collection<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(Collection<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    @Override
    public String toString() {
        return "Query{" +
                "id=" + id +
                ", titles=" + titles +
                '}';
    }
}

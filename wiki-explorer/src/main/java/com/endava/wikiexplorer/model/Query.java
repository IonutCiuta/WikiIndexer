package com.endava.wikiexplorer.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

/**
 * Created by aciurea on 8/17/2016.
 */
@Entity
@Table(name="query")
public class Query {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String titles;

    @Column(name="time")
    private Long timeMilis;

    @OneToMany
    @JoinColumn(name = "query_id")
    private Collection<Occurence> occurences;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public Integer getId() {

        return id;
    }

    public String getTitles() {
        return titles;
    }

    @Override
    public String toString() {
        return "Query{" +
                "id=" + id +
                ", titles=" + titles +
                '}';
    }
}

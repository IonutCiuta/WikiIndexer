package com.endava.wikiexplorer.dto.wiki;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiPagesContainer implements Serializable {
    @JsonProperty("pages")
    private WikiPage page;

    @JsonAnySetter
    public void addPage(String name, WikiPage page) {
        System.out.println(name);
        this.page = page;
    }

    public WikiPage getPage() {
        return page;
    }

    public void setPage(WikiPage page) {
        this.page = page;
    }

    @Override
    public String toString() {
        return "WikiPagesContainer{" +
                "page=" + page +
                '}';
    }
}

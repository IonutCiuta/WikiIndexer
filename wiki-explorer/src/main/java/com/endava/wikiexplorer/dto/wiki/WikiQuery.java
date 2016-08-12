package com.endava.wikiexplorer.dto.wiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiQuery implements Serializable {
    @JsonProperty("pages")
    private Map<String, WikiArticle> pages;

    public Map<String, WikiArticle> getPages() {
        return pages;
    }

    public void setPages(Map<String, WikiArticle> pages) {
        this.pages = pages;
    }

    @Override
    public String toString() {
        return "WikiQuery{" +
                "pages=" + pages +
                '}';
    }
}

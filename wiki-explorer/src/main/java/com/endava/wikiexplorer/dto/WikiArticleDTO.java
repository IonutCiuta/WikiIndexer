package com.endava.wikiexplorer.dto;

import com.endava.wikiexplorer.dto.wiki.WikiQuery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiArticleDTO implements Serializable {
    private WikiQuery query;

    public WikiQuery getQuery() {
        return query;
    }

    public void setQuery(WikiQuery query) {
        this.query = query;
    }

    public String getWikiArticleId() {
        return query.getContent().getPage().getPageId();
    }

    public String getWikiArticleTitle() {
        return query.getContent().getPage().getTitle();
    }

    public String getWikiFormatContent() {
        return query.getContent().getPage().getRevisions().get(0).getContent();
    }

    @Override
    public String toString() {
        return "WikiArticleDTO{" +
                "query=" + query +
                '}';
    }
}

package com.endava.wikiexplorer.dto;

import com.endava.wikiexplorer.dto.wiki.WikiArticle;
import com.endava.wikiexplorer.dto.wiki.WikiQuery;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiDTO implements Serializable {
    private WikiQuery query;

    public WikiQuery getQuery() {
        return query;
    }

    public void setQuery(WikiQuery query) {
        this.query = query;
    }

    public List<WikiArticle> getArticles() {
        return new ArrayList<>(query.getPages().values());
    }

    public String getQueryTitles() {
        String result = "";
        List<WikiArticle> articles = getArticles();

        for(int i = 0; i < articles.size(); i++) {
            if(i > 0) {
                result += ", ";
            }
            result += articles.get(i).getTitle();
        }

        return result;
    }

    @Override
    public String toString() {
        return "WikiDTO{" +
                "query=" + query +
                '}';
    }
}

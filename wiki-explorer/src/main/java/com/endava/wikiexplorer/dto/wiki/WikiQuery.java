package com.endava.wikiexplorer.dto.wiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiQuery implements Serializable {
    @JsonProperty("pages")
    private WikiPagesContainer content;

    public WikiPagesContainer getContent() {
        return content;
    }

    public void setContent(WikiPagesContainer content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WikiQuery{" +
                "content=" + content +
                '}';
    }
}

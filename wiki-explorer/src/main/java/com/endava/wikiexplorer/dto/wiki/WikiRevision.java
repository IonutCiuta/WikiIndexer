package com.endava.wikiexplorer.dto.wiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiRevision implements Serializable{
    @JsonProperty("contentformat")
    private String contentFormat;

    @JsonProperty("contentmodel")
    private String contentModel;

    @JsonProperty("*")
    private String content;

    public String getContentFormat() {
        return contentFormat;
    }

    public void setContentFormat(String contentFormat) {
        this.contentFormat = contentFormat;
    }

    public String getContentModel() {
        return contentModel;
    }

    public void setContentModel(String contentModel) {
        this.contentModel = contentModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WikiRevision{" +
                "contentFormat='" + contentFormat + '\'' +
                ", contentModel='" + contentModel + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}

package com.endava.wikiexplorer.dto.wiki;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Ionut Ciuta on 8/11/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WikiArticle implements Serializable {
    @JsonProperty("pageid")
    private Integer pageId;

    private String title;

    private List<WikiRevision> revisions;

    public String getWikiEncodedContent() {
        return revisions.get(0).getContent();
    }

    public Integer getPageId() {
        return pageId;
    }

    public void setPageId(Integer pageId) {
        this.pageId = pageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<WikiRevision> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<WikiRevision> revisions) {
        this.revisions = revisions;
    }

    @Override
    public String toString() {
        return "WikiArticle{" +
                "pageId=" + pageId +
                ", title='" + title + '\'' +
                ", revisions=" + revisions +
                '}';
    }
}

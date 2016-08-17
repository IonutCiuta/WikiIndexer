package com.endava.wikiexplorer.util;

import com.endava.wikiexplorer.dto.wiki.WikiArticle;
import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Ionut Ciuta on 8/11/2016.
 */
public class WikiContentParser {

    /**
     * Method that parses a list of articles in a serial way
     * @param articleList input articles
     * @return map of article index - word array pairs
     */
    public static Map<Integer, String[]> parseArticlesSerial(List<WikiArticle> articleList) {
        Map<Integer, String[]> articleWordsMap = new HashMap<>();
        for(int i = 0; i < articleList.size(); i++) {
            String plainTextArticle = parse(articleList.get(i).getWikiEncodedContent());
            articleWordsMap.put(i, plainTextArticle.split(" "));
        }
        return articleWordsMap;
    }

    /**
     * Method that parses a list of articles in a serial way
     * @param articleList input articles
     * @return map of article index - word array pairs
     */
    public static ConcurrentHashMap<Integer, String[]> parseArticlesParallel(List<WikiArticle> articleList) {
        ConcurrentHashMap<Integer, String[]> articleWordsMap = new ConcurrentHashMap<>();
        ExecutorService parsingExecutor = Executors.newFixedThreadPool(4);

        for(int i = 0; i < articleList.size(); i++) {
            Runnable parsingThread = new ParsingThread(i, articleList.get(i), articleWordsMap);
            parsingExecutor.execute(parsingThread);
        }
        parsingExecutor.shutdown();
        while(!parsingExecutor.isTerminated()) {

        }

        return articleWordsMap;
    }

    /**
     * Convenience method; converts encoded text to plain text
     * @param encodedText wiki content
     * @return plain text
     */
    private static String parse(String encodedText) {
        return getPlainText(parseWikiEncodedText(encodedText));
    }

    /**
     * Methos used to partially parseSerial wiki encoded text by converting to html encoded text
     * @param wikiEncodedText wiki encoded text
     * @return text partially decoded
     */
    private static String parseWikiEncodedText(String wikiEncodedText) {
        StringWriter writer = new StringWriter();
        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        MarkupParser parser = new MarkupParser(new MediaWikiDialect());

        builder.setEmitAsDocument(false);
        parser.setBuilder(builder);
        parser.parse(wikiEncodedText);

        final String html = writer.toString();
        final StringBuilder cleaned = new StringBuilder();

        HTMLEditorKit.ParserCallback callback = new HTMLEditorKit.ParserCallback() {
            public void handleText(char[] data, int pos) {
                cleaned.append(new String(data)).append(' ');
            }
        };

        try {
            new ParserDelegator().parse(new StringReader(html), callback, false);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return cleaned.toString();
    }

    /**
     * Removes some extra tags and special characters
     * @param partiallyEncodedText text partially encoded
     * @return plain text
     */
    private static String getPlainText(String partiallyEncodedText) {
        final String EMPTY = "";
        final String SPACE = " ";

        return partiallyEncodedText
                .replaceAll("(?s)<ref.*?</ref>",   EMPTY)
                .replaceAll("(?s)\\{\\{.*?\\}\\}", EMPTY)
                .replaceAll("(?s)\\[\\[.*?\\]\\]", EMPTY)
                .replaceAll("[^a-zA-Z0-9 ]+", SPACE)
                .trim().replaceAll(" +", SPACE);
    }

    /**
     * Threads that parses an encoded wiki article as plain text; stores result in a shared ConcurrentHashMap
     */
    private static class ParsingThread implements Runnable {
        private int tid;
        private WikiArticle article;
        private ConcurrentHashMap<Integer, String[]> accumulator;

        ParsingThread(int tid, WikiArticle article, ConcurrentHashMap<Integer, String[]> accumulator) {
            this.tid = tid;
            this.article = article;
            this.accumulator = accumulator;
        }

        @Override
        public void run() {
            String articleText = parse(article.getWikiEncodedContent());
            String[] articleWords = articleText.split(" ");
            accumulator.put(tid, articleWords);
        }
    }
}

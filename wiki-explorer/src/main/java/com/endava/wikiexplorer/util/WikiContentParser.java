package com.endava.wikiexplorer.util;

import net.java.textilej.parser.MarkupParser;
import net.java.textilej.parser.builder.HtmlDocumentBuilder;
import net.java.textilej.parser.markup.mediawiki.MediaWikiDialect;

import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Ionut Ciuta on 8/11/2016.
 */
//todo refactor
public class WikiContentParser {
    private static final String EMPTY = "";
    private static final String SPACE = " ";


    public static String parse(String wikiEncodedContent) {
        StringWriter writer = new StringWriter();

        HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
        builder.setEmitAsDocument(false);

        MarkupParser parser = new MarkupParser(new MediaWikiDialect());
        parser.setBuilder(builder);
        parser.parse(wikiEncodedContent);

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

         String cleanText = cleaned.toString()
                    .replaceAll("(?s)<ref.*?</ref>",   EMPTY)
                    .replaceAll("(?s)\\{\\{.*?\\}\\}", EMPTY)
                    .replaceAll("(?s)\\{\\{.*?\\}\\}", EMPTY)
                    .replaceAll("(?s)\\[\\[.*?\\]\\]", EMPTY)
                    .replaceAll("[^a-zA-Z0-9 ]+", SPACE)
                    .trim().replaceAll(" +", SPACE);

        return cleanText;
    }


}

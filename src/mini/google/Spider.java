/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author Martin
 */
public final class Spider {

    private Set<String> links;
    private final String text;
    private static String initUrl = "https://cs.wikipedia.org";
    private static String allowedPath;
    final static int TIME_OUT = 10; // time out to download page in seconds

    public Spider(String url, String allowedPath) throws IOException {
        this.allowedPath = allowedPath;
        Document doc = this.download(url);
        this.parseLinks(doc);
        this.text = this.parseText(doc);
    }

    protected Document download(String url) throws IOException {
        System.out.println(url);
        return Jsoup.connect(url).timeout(TIME_OUT * 1000).get();
    }

    protected void parseLinks(Document doc) {
        this.links = new TreeSet<>();
        String tempLink;
        String[] tokens;

        Elements docLinks = doc.select("a[href]");
        for (Element link : docLinks) {
            tempLink = link.attr("abs:href");
            if (!tempLink.startsWith(initUrl + this.allowedPath)) {
                continue;
            }
            tokens = tempLink.split("#");
            this.links.add(tokens[0]);
        }

    }

    protected String parseText(Document doc) {
        return doc.head().text() + " " + doc.body().text();
    }

    public Set<String> getLinks() {
        return links;
    }

    protected String getText() {
        return text;
    }

    public Set<String> getKeyWords() {
        String[] text = this.text.replaceAll("[?.&,; %=·(){}“|\\/…•„\":]", " ").toLowerCase().split(" ");;

        Set<String> temp = new TreeSet<>(Arrays.asList(text));

        return temp;
    }

}

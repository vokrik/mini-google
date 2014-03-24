/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.io.IOException;
import java.util.ArrayList;
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

    private ArrayList<String> links;
    private final String text;

    public Spider(String url) throws IOException {
        Document doc = this.download(url);
        this.parseLinks(doc);
        this.text = this.parseText(doc);
    }

    protected Document download(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    protected void parseLinks(Document doc) {
        this.links = new ArrayList<>();
        Elements docLinks = doc.select("a[href]");
        for (Element link : docLinks) {
            this.links.add(link.attr("abs:href"));
        }
    }

    protected String parseText(Document doc) {
        return doc.head().text() + " " + doc.body().text();
    }

    public ArrayList<String> getLinks() {
        return links;
    }

    public String getText() {
        return text;
    }

    public Set<String> getKeyWords() {
        Set<String> temp = new TreeSet<>();
        return temp;
    }

}


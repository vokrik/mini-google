/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private final String heading;
    private static String initUrl = "https://cs.wikipedia.org";
    private static String allowedPath;
    final static int TIME_OUT = 10; // time out to download page in seconds

    public Spider(String url, String allowedPath) throws IOException {
        this.allowedPath = allowedPath;
        Document doc = this.download(url);
        this.heading = this.parseHeading(doc);
        System.out.println(heading);
        this.parseLinks(doc);
        this.text = this.parseText(doc);
    }

    protected String parseHeading(Document doc) {
        return doc.select("#firstHeading > span").text();
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
            //  try {

            String[] tokensLink = link.attr("abs:href").split("/");

            //tokensLink[tokensLink.length - 1] = URLEncoder.encode(tokensLink[tokensLink.length - 1], "UTF-8");
            tempLink = "";
            for (int i = 0; i < tokensLink.length; i++) {
                tempLink += tokensLink[i];
                if (i + 1 != tokensLink.length) {
                    tempLink += "/";
                }
            }
            if (!tempLink.startsWith(initUrl + this.allowedPath)) {
                continue;
            }
            tokens = tempLink.split("#");
            this.links.add(tokens[0]);
            //} catch (UnsupportedEncodingException ex) {
            //  Logger.getLogger(Spider.class.getName()).log(Level.SEVERE, null, ex);
            //}
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

    public String getHeading() {
        return heading;
    }

    public Set<String> getKeyWords() {
        String[] text = this.text.replaceAll("[?.&,; %=·(){}“|\\/…•„\":]", " ").toLowerCase().split(" ");;

        Set<String> temp = new TreeSet<>(Arrays.asList(text));

        return temp;
    }

}

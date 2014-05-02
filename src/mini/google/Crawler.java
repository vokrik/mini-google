/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author Martin
 */
public class Crawler {

    /**
     * @var Queue of links we got by crawling ready to be crawled
     */
    private Queue<String> linksToCrawl;
    /**
     * @var Array, where the key is URL of site we crawled and value is all the
     * links, which the site contained
     */
    private Map<String, Set<String>> indexedMap;

    /**
     * @var Counts how many pages we've already crawled
     */
    private int counter;
    /**
     * @var Table name postfix
     */
    private int tableNumber;
    /**
     * @var Says how many pages should we crawl
     */
    private int maxPages;
    /**
     * @var Array, where key is the URL of a site and value is all the words it
     * contains
     */
    private Map<String, Set<String>> keyWords;

    private Map<String, String> titles;

    /**
     * @var Keeps the crawler on the track and restricts it to go to wrong URLs
     */
    private final String allowedPath;

    public Crawler(String initUrl, String allowedPath, int maxPages, int tableNumber) {
        this.tableNumber = tableNumber;
        this.allowedPath = allowedPath;
        this.linksToCrawl = new LinkedList();
        this.linksToCrawl.add(initUrl);
        this.maxPages = maxPages;
        this.counter = 0;
        this.indexedMap = new HashMap();
        this.keyWords = new HashMap();
        this.titles = new HashMap();
    }

    /**
     * @desc Takes first link in the linksToCrawl and saves its contents into
     * linksToCrawl, keyWords and indexedMap. It ends when ammount of maxPages
     * was crawled.
     */
    public void crawl() throws IOException {

        String url = this.getNextUrl();

        while (url != null) {

            Spider spider = new Spider(url, this.allowedPath);
            Set<String> links = spider.getLinks();
            Set<String> keyWords = spider.getKeyWords();
            this.titles.put(url, spider.getHeading());
            this.saveKeyWords(url, keyWords);
            try {
                this.addToIndex(url, links);
            } catch (Exception ex) {
                System.out.println(ex);
                System.exit(1);
            }

            this.addToQueue(links);

            url = this.getNextUrl();
        }

    }

    /**
     * @desc Saves all the outlinks that the page contains into indexedMap
     */
    protected void addToIndex(String url, Set<String> links) throws Exception {

        if (this.indexedMap.containsKey(url)) {
            throw new Exception("Trying to save links for page, that was already crawled");
        }

        this.indexedMap.put(url, links);// saves url and links to the index

    }

    /**
     * @desc Adds links to the urlsToCrawl
     */
    protected void addToQueue(Set<String> links) {

        for (String link : links) {
            if (this.indexedMap.containsKey(link) || this.linksToCrawl.contains(link)) { // If we have link in the index, it has been crawled so we don't put it into the queue
                continue;
            }
            this.linksToCrawl.add(link);
        }
    }

    /**
     * @return url to be crawled, NULL if limit is reached or the queue is empty
     */
    protected String getNextUrl() {
        if (counter >= maxPages) {
            return null;
        }

        this.counter++; // going to crawl another site
        return this.linksToCrawl.poll();
    }

    protected void saveKeyWords(String url, Set<String> keyWords) {
        this.keyWords.put(url, keyWords);
    }

    /**
     * @desc Saves everything from the memmory into the database
     */
    public void persist() {

        // persist url to url
        for (Map.Entry<String, Set<String>> entry : this.indexedMap.entrySet()) {
            for (String url : entry.getValue()) {
                if (this.indexedMap.containsKey(url)) {
                    DAO.getInstance().saveUrlUrl(this.tableNumber, entry.getKey(), url);
                }
            }
        }

        // persist url to keyword
        for (Map.Entry<String, Set<String>> entry : this.keyWords.entrySet()) {
            for (String word : entry.getValue()) {
                DAO.getInstance().saveUrlWord(this.tableNumber, entry.getKey(), word);
            }
        }

        //persist url to tile
        for (Map.Entry<String, String> entry : this.titles.entrySet()) {
          
                DAO.getInstance().saveTitle(this.tableNumber, entry.getKey(), entry.getValue());
         
        }
    }

}

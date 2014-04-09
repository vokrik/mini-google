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

    private Queue<String> linksToCrawl;
    private Map<String, Set<String>> indexedMap;
    private int counter;
    private int maxPages;
    private Database database;
    private Map<String, Set<String>> keyWords;

    public Crawler(String initUrl, int maxPages, Database database) {
        this.linksToCrawl = new LinkedList();
        this.linksToCrawl.add(initUrl);
        this.maxPages = maxPages;
        this.counter = 0;
        this.database = database;
        this.indexedMap = new HashMap();
        this.keyWords = new HashMap();
    }
    /* 
     * @desc Starts crawling
     */

    public void crawl() throws IOException {

        String url = this.getNextUrl();

        while (url != null) {

            Spider spider = new Spider(url);
            Set<String> links = spider.getLinks();
            Set<String> keyWords = spider.getKeyWords();

            this.saveKeyWords(url, keyWords);
            try {
                this.addToIndex(url, links);
            } catch (Exception ex) {
                System.out.println(ex);
                System.exit(1);
            }
//	    for(String word : keyWords){
//		System.out.println(word + " ");
//	    }

            this.addToQueue(links);

            url = this.getNextUrl();
        }
        for (Map.Entry<String, Set<String>> entry : this.keyWords.entrySet()) {
            System.out.println("pocet slov pro " + entry.getKey()
                    + " je: " + entry.getValue().size());

        }

        for (String word : this.keyWords.get("https://cs.wikipedia.org/wiki/Hlavn%C3%AD_strana")) {
            System.out.println(word);
        }

    }

    /*
     *  PĹ™idĂˇ do fronty links a uloĹľĂ­ do indexu [][] url a links;
     */
    protected void addToIndex(String url, Set<String> links) throws Exception {

        if (this.indexedMap.containsKey(url)) {
            throw new Exception("Trying to save links for page, that was already crawled");
        }

        this.indexedMap.put(url, links);// saves url and links to the index

    }

    protected void addToQueue(Set<String> links) {

        for (String link : links) {
            if (this.indexedMap.containsKey(link) || this.linksToCrawl.contains(link)) { // If we have link in the index, it has been crawled so we don't put it into the queue
                continue;
            }
            this.linksToCrawl.add(link);
        }
    }

    /*
     * Pokud counter < maxPages vratĂ­ url z fronty, jinak NULL
     */
    protected String getNextUrl() {
        if (counter > maxPages) {
            return null;
        }

        this.counter++; // going to crawl another site
        return this.linksToCrawl.poll();
    }

    protected void saveKeyWords(String url, Set<String> keyWords) {
        this.keyWords.put(url, keyWords);
    }

}

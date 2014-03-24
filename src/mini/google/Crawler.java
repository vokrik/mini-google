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

/**
 *
 * @author Martin
 */
public class Crawler {

    private String initUrl;
    private Queue<String> linksToCrawl;
    private Map<String, ArrayList<String>>  linksIndex ;
    private int counter;
    private int maxPages;
    private Database database;

    public Crawler(String initUrl, int maxPages, Database database) {
	this.linksToCrawl =  new LinkedList();
	this.initUrl = initUrl;
	this.maxPages = maxPages;
	this.counter = 0;
	this.database = database;
	this.linksIndex = new HashMap();
    }
    /* 
     * @desc Starts crawling
     */

    public void crawl() throws IOException {

	String url = this.initUrl;

	while (url != null) {

	    Spider spider = new Spider(url);
	    ArrayList<String> links = spider.getLinks();
	    Set<String> keyWords = spider.getKeyWords();

	    this.saveKeyWords(url, keyWords);
	    try{
		this.addToIndex(url, links);
	    } catch (Exception ex){
		System.out.println(ex);
		System.exit(1);
	    }
	    url = this.getNextUrl();
	}

    }

    /*
     *  Přidá do fronty links a uloží do indexu [][] url a links;
     */
    protected void addToIndex(String url, ArrayList<String> links)throws Exception{
	
	if(this.linksIndex.containsKey(url)){
	    throw new Exception("Trying to save links for page, that was already crawled");
	}
	
	this.linksIndex.put(url, links);// saves url and links to the index
	
	for(String link : links){
	    if(this.linksIndex.containsKey(link)) { // If we have link in the index, it has been crawled so we don't put it into the queue
		continue;
	    }
	    this.linksToCrawl.add(link); 
	}
    }

    /*
     * Pokud counter < maxPages vratí url z fronty, jinak NULL
     */
    protected String getNextUrl() {
	if (counter > maxPages) {
	    return null;
	}

	this.counter++; // going to crawl another site
	return this.linksToCrawl.poll(); 
    }
    

    
    protected void saveKeyWords(String url, Set<String> keyWords){
	
    }

}


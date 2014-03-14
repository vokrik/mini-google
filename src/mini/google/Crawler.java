/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Martin
 */
public class Crawler {

    private String initUrl;
    private Queue<String> linksToCrawl;
    private String [][] linksIndex ;
    private int counter;
    private int maxPages;
    private Database database;

    public Crawler(String initUrl, int maxPages, Database database) {
	this.linksToCrawl =  new LinkedList();
	this.initUrl = initUrl;
	this.maxPages = maxPages;
	this.counter = 0;
	this.database = database;
    }
    /* 
     * @desc Starts crawling
     */

    public void crawl() {

	String url = this.initUrl;

	while (url != null) {

	    Spider spider = new Spider(url);
	    String[] links = spider.getLinks();
	    String text = spider.getText();

	    this.saveSource(url, text);
	    this.addToIndex(url, links);
	    url = this.getNextUrl();
	}

    }

    /*
     *  Přidá do fronty links a uloží do indexu [][] url a links;
     */
    protected void addToIndex(String url, String[] links) {
    }

    /*
     * POkud counter < maxPages vratí url z fronty, jinak NULL
     */
    protected String getNextUrl() {
	if (counter > maxPages) {
	    return null;
	}

	String url = "";

	this.counter++;
	return url;
    }
    
    protected void saveSource(String url, String source){
	
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.sql.Array;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author jakub
 */
public class PageRank {

    protected Map<String, Set<String>> links;
    protected Map<String, Set<String>> backLinks;
    protected Map<String, Double> urlPageRank;
    protected int urlsCount;

    PageRank() {
        this.urlPageRank = new HashMap<>();
        this.getUrlLinks();
        this.getUrlBackLinks();
        this.urlsCount = this.links.size();
    }

    protected void getUrlLinks() {
        this.links = new HashMap();

        Set<String> urls = DAO.getInstance().getAllUrls();
        for (String url : urls) {
            this.links.put(url, DAO.getInstance().getUrlsFor(url));
        }
    }

    protected void getUrlBackLinks() {
        Set<String> keys = this.links.keySet();
      this.backLinks = new HashMap<>();
      for(String key : keys){// Prepare reversed index
            this.backLinks.put(key, new HashSet<>());
        }
      for(String key : keys){// Reverse index
         Set<String> values = this.links.get(key);
         for(String value : values){
             if(backLinks.containsKey(value)){
             backLinks.get(value).add(key);
                
             }
         }
        }
    }

    public void init() {
        for (String url : links.keySet()) {
            if (this.links.get(url).isEmpty()) {
                System.out.println("Url: " + url + " has no outlinks links");
                this.links.get(url).addAll(this.links.keySet());
            }
            this.urlPageRank.put(url, (double) (1.0 / (double) this.urlsCount));
        }

    }

    public void count(int count) {

        for (int i = 0; i < count; i++) {
             DAO.getInstance().saveIteration(i, this.urlPageRank);
            

            Map<String, Double> nextIteration = new HashMap();

            for (String url : links.keySet()) {
                nextIteration.put(url, 0.0);
            }

            for (String url : this.links.keySet()) {
                for (String ref : this.backLinks.get(url)) {
                    double previousValue = nextIteration.get(url);
                    nextIteration.put(url, previousValue + this.urlPageRank.get(ref) / this.links.get(ref).size());
                }

            }

            this.urlPageRank = nextIteration;

        }

    }

    public void persist() {

        for (String url : this.urlPageRank.keySet()) {
            DAO.getInstance().savePageRank(url, this.urlPageRank.get(url));
        }

    }

}

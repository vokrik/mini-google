/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

/**
 *
 * @author Martin
 */
public class Spider {
    
    private String url;
    private String [][] links;
    private String text;
    
    public Spider(String url){
	this.url = url;
	String source = this.download();
	this.parseLinks(source);
	this.text = this.parseText(source);
    }
    
    protected String download(){
	
    }
    
    protected void parseLinks(String source){
	
    }
    
    protected String parseText(String source){
	
    }
    
    public String[][] getLinks() {
	return links;
    }

    public String getText() {
	return text;
    }
    
    
    
}

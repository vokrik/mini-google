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
public class MiniGoogle {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
	Crawler c = new Crawler("http://cs.wikipedia.org", 12000);
	c.crawl();
	
		
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.io.IOException;

/**
 *
 * @author Martin
 */
public class MiniGoogle {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {

//        Crawler c = new Crawler("https://cs.wikipedia.org/wiki/Hlavní_strana", "/wiki/", 100);
//        c.crawl();
//        c.persist();
        
        PageRank pr = new PageRank();
        pr.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
        pr.count(50);        
        pr.persist();

    }

}

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
//
//        Crawler c1 = new Crawler("https://cs.wikipedia.org/wiki/Hlavn%C3%AD_strana", "/wiki/", 100, 1);
//        c1.crawl();
//        c1.persist();
//
//        Crawler c2 = new Crawler("https://cs.wikipedia.org/wiki/Hlavn%C3%AD_strana", "/wiki/", 1500, 2);
//        c2.crawl();
//        c2.persist();

//        System.out.println("Counting pageRank 1");
//        PageRank pr1 = new PageRank(1, 1);
//        pr1.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
//        pr1.count(10, 1.0);
//        pr1.persist();
//
//        System.out.println("Counting pageRank 2");
//        PageRank pr2 = new PageRank(1, 2);
//        pr2.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
//        pr2.count(20, 1.0);
//        pr2.persist();
//        
//        System.out.println("Counting pageRank 3");
//        PageRank pr3 = new PageRank(2, 3);
//        pr3.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
//        pr3.count(10, 1.0);
//        pr3.persist();

        System.out.println("Counting pageRank 4");
        PageRank pr4 = new PageRank(2, 4);
        pr4.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
        pr4.count(20, 1.0);
        pr4.persist();
        
        
        System.out.println("Counting pageRank 5");
        PageRank pr5 = new PageRank(2, 5);
        pr5.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
        pr5.count(20, 0.1);
        pr5.persist();
        
        System.out.println("Counting pageRank 6");
        PageRank pr6 = new PageRank(2, 6);
        pr6.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
        pr6.count(20, 0.5);
        pr6.persist();
        
        System.out.println("Counting pageRank 7");
        PageRank pr7 = new PageRank(2, 7);
        pr7.init(); // naplni tabulku seznamem url a jeich inicialiyacniho pageranku        
        pr7.count(20, 0.85);
        pr7.persist();
    }

}

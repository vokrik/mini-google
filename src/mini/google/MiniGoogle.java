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

        Database db = new Database();
        Crawler c = new Crawler(args[0], Integer.parseInt(args[1]), db);
        c.crawl();



    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mini.google;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Martin
 */
public class DAO {

    private final String DB_NAME = "minigoogle";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    private Connection connection;
    private static DAO instance;

    private final PreparedStatement saveUrlsWordsPS;
    private final PreparedStatement saveUrlsUrlsPS;
    private final PreparedStatement getAllUrlsPS;
    private final PreparedStatement getUrlsPS;
    private final PreparedStatement savePageRankPS;
    private final PreparedStatement saveIterationPS;
    private final PreparedStatement saveTitlePS;
    private final PreparedStatement getDeviationsPS;
    private final PreparedStatement saveDeviationsPS;

    public static DAO getInstance() {
        if (instance == null) {
            try {
                instance = new DAO();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return instance;
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DAO() throws SQLException {
        String url = "jdbc:mysql://localhost/" + DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8";

        connection = DriverManager.getConnection(url, LOGIN, PASSWORD);

        saveUrlsWordsPS = connection.prepareStatement(
                "INSERT INTO urls_words? (url, word) "
                + "VALUES (?, ?)"
        );
        saveUrlsUrlsPS = connection.prepareStatement(
                "INSERT INTO urls_urls? (url1, url2) "
                + "VALUES (?, ?)"
        );
        getAllUrlsPS = connection.prepareStatement(
                "SELECT DISTINCT url1 FROM urls_urls?"
        );
        getUrlsPS = connection.prepareStatement(
                "SELECT DISTINCT url2 FROM urls_urls?"
                + " WHERE url1=?"
        );
        savePageRankPS = connection.prepareStatement(
                "INSERT INTO pagerank? (url, pagerank) "
                + "VALUES (?, ?)"
        );
        saveIterationPS = connection.prepareStatement(
                "INSERT INTO iterations? (url, pagerank, iteration) "
                + "VALUES (?, ?, ?)"
        );

        saveTitlePS = connection.prepareStatement(
                "INSERT INTO urls_titles? (url, title) "
                + "VALUES (?, ?)"
        );
        getDeviationsPS = connection.prepareStatement(
                "SELECT SUM(ABS(l.pagerank-p.pagerank)) as deviation, l.iteration as iteration FROM iterations? l JOIN iterations? p ON l.url = p.url AND\n" +
"l.iteration = p.iteration +1 GROUP BY iteration"
        );
        
        saveDeviationsPS = connection.prepareStatement(
        "INSERT INTO deviations? (iteration, deviation) VALUES (?,?)"
        );
                
                
    }

    public void saveUrlWord(int tableNum, String url, String word) {
        try {
            System.out.println("Url: " + url + " Contains: " + word);
            saveUrlsWordsPS.setInt(1, tableNum);
            saveUrlsWordsPS.setString(2, url);
            saveUrlsWordsPS.setString(3, word);
            saveUrlsWordsPS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveIteration(int tableNum, int iteration, Map<String, Double> pageRanks) {

        for (String url : pageRanks.keySet()) {
            try {
                saveIterationPS.setInt(1, tableNum);
                saveIterationPS.setString(2, url);
                saveIterationPS.setDouble(3, pageRanks.get(url));
                saveIterationPS.setInt(4, iteration);
                saveIterationPS.execute();
            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveUrlUrl(int tableNum, String url1, String url2) {
        try {
            saveUrlsUrlsPS.setInt(1, tableNum);
            saveUrlsUrlsPS.setString(2, url1);
            saveUrlsUrlsPS.setString(3, url2);
            saveUrlsUrlsPS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<String> getAllUrls(int tableNum) {
        Set<String> listOfUrls = new HashSet<>();
        try {
            getAllUrlsPS.setInt(1, tableNum);
            ResultSet resultSet = getAllUrlsPS.executeQuery();
            while (resultSet.next()) {
                listOfUrls.add(resultSet.getString(1));

            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return listOfUrls;
    }

    public Set<String> getUrlsFor(int tableNum, String url2) {
        Set<String> listOfUrls = new HashSet<>();
        try {
            getUrlsPS.setInt(1, tableNum);
            getUrlsPS.setString(2, url2);
            ResultSet resultSet = getUrlsPS.executeQuery();
            while (resultSet.next()) {
                listOfUrls.add(resultSet.getString(1));
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return listOfUrls;
    }

    public void savePageRank(int tableNum, String url, double pagerank) {
        try {
            savePageRankPS.setInt(1, tableNum);
            savePageRankPS.setString(2, url);
            savePageRankPS.setDouble(3, pagerank);
            savePageRankPS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveTitle(int tableNum, String url, String title) {
        try {
            saveTitlePS.setInt(1, tableNum);
            saveTitlePS.setString(2, url);
            saveTitlePS.setString(3, title);
            saveTitlePS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void countDeviations(int tableNum){
        try{
            getDeviationsPS.setInt(1, tableNum);
            getDeviationsPS.setInt(2, tableNum);
              ResultSet resultSet = getDeviationsPS.executeQuery();
              while(resultSet.next()){
                  saveDeviationsPS.setInt(1, tableNum);
                  saveDeviationsPS.setInt(2, resultSet.getInt("iteration"));
                  saveDeviationsPS.setDouble(3, resultSet.getDouble("deviation"));
                  saveDeviationsPS.execute();
              }
        }catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }
}

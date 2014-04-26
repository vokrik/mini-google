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
                "INSERT INTO urls_words (url, word) "
                + "VALUES (?, ?)"
        );
        saveUrlsUrlsPS = connection.prepareStatement(
                "INSERT INTO urls_urls (url1, url2) "
                + "VALUES (?, ?)"
        );
        getAllUrlsPS = connection.prepareStatement(
                "SELECT DISTINCT url1 FROM urls_urls"
        );
        getUrlsPS = connection.prepareStatement(
                "SELECT DISTINCT url2 FROM urls_urls"
                + " WHERE url1=?"
        );
        savePageRankPS = connection.prepareStatement(
                "INSERT INTO pagerank (url, pagerank) "
                + "VALUES (?, ?)"
        );
        saveIterationPS = connection.prepareStatement(
                "INSERT INTO iterations (url, pagerank, iteration) "
                + "VALUES (?, ?, ?)"
        );

    }

    public void saveUrlWord(String url, String word) {
        try {
            System.out.println("Url: " + url + " Contains: " + word);
            saveUrlsWordsPS.setString(1, url);

            saveUrlsWordsPS.setString(2, word);
            saveUrlsWordsPS.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveIteration(int iteration, Map<String, Double> pageRanks) {

        for (String url : pageRanks.keySet()) {
            try {
                saveIterationPS.setString(1, url);
                saveIterationPS.setDouble(2, pageRanks.get(url));
                saveIterationPS.setInt(3, iteration);
                saveIterationPS.execute();

            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void saveUrlUrl(String url1, String url2) {
        try {
            saveUrlsUrlsPS.setString(1, url1);
            saveUrlsUrlsPS.setString(2, url2);
            saveUrlsUrlsPS.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<String> getAllUrls() {
        Set<String> listOfUrls = new HashSet<>();
        try {
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

    public Set<String> getUrlsFor(String url2) {
        Set<String> listOfUrls = new HashSet<>();
        try {
            getUrlsPS.setString(1, url2);
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

    public void savePageRank(String url, double pagerank) {
        try {
            savePageRankPS.setString(1, url);
            savePageRankPS.setDouble(2, pagerank);
            savePageRankPS.execute();

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

}

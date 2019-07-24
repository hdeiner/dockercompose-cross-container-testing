package com.deinersoft;

import java.io.*;
import java.util.*;

public class PopulateDatabase {

    public static void main(String[] args) {

        String databaseDriver = getLiquibaseProperty("driver");
        String databaseURL = getLiquibaseProperty("url");
        String databaseUsername = getLiquibaseProperty("username");
        String databasePassword = getLiquibaseProperty("password");

        new ProcessTsvFileNameBasics("data/name.basics.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitleAkas("data/title.akas.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitleBasics("data/title.basics.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitleCrew("data/title.crew.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitleEpisode("data/title.episode.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitlePrincipals("data/title.principals.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        new ProcessTsvFileTitleRatings("data/title.ratings.tsv", databaseDriver, databaseURL, databaseUsername, databasePassword).execute();
        System.exit(0);
    }

    private static String getLiquibaseProperty(String propertyName) {
        String propertyValue = "";
        Properties properties = new Properties();
        InputStream inputStream = null;

        try {
            inputStream = new FileInputStream("liquibase.properties");
            properties.load(inputStream);
            propertyValue = properties.getProperty(propertyName);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return propertyValue;
    }

}
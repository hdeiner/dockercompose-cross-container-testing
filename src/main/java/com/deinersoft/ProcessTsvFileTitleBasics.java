package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileTitleBasics extends ProcessTsvFile {
    public ProcessTsvFileTitleBasics(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO title (tconst, titleType, primaryTitle, originalTitle, isAdult, startYear, endYear, runtimeMinutes) VALUES (";
                sql += "'" + row[0] + "' "; // tconst
                sql += ", '" + processEscapedQuotes(row[1]) + "' "; // titleType
                sql += ", '" + processEscapedQuotes(row[2]) + "' "; // primaryTitle
                sql += ", '" + processEscapedQuotes(row[3]) + "' "; // originalTitle
                sql += ", '" + processBoolean(row[4]) + "' "; // isAdult
                sql += ", '" + processYear(row[5]) + "' "; // startYear
                sql += ", '" + processYear(row[6]) + "' "; // endYear
                sql += ", '" + processInteger(row[7]) + "'"; // runTimeMinutes
                sql += ")";
                statement.execute(sql);

                if (row[8] != null) {
                    String[] genres = row[8].split(",[ ]*");
                    for (String genre : genres) {
                        sql = "INSERT INTO titleGenre (tconst, genre) VALUES (";
                        sql += "'" + row[0] + "', "; // tconst
                        sql += "'" + genre + "'"; // genre
                        sql += ")";
                        statement.execute(sql);
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Got an SQLException! ");
            System.err.println(e.getMessage());
        }
    }
}

package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileTitleRatings extends ProcessTsvFile {
    public ProcessTsvFileTitleRatings(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO titleRating (tconst, averageRating, numberOfVotes) VALUES (";
                sql += "'" + row[0] + "' "; // tconst
                sql += ", '" + row[1] + "' "; // averageRating
                sql += ", '" + processInteger(row[2]) + "'"; // numberOfVotes
                sql += ")";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Got an SQLException! ");
            System.err.println(e.getMessage());
        }
    }
}

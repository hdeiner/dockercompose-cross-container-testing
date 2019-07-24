package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileTitleEpisode extends ProcessTsvFile {
    public ProcessTsvFileTitleEpisode(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO titleEpisode (tconst, tconstParent, seasonNumber, episodeNumber) VALUES (";
                sql += "'" + row[0] + "' "; // tconst
                sql += ", '" + row[1] + "' "; // tconstParent
                sql += ", '" + processInteger(row[2]) + "' "; // seasonNumber
                sql += ", '" + processInteger(row[3]) + "'"; // episodeNumber
                sql += ")";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Got an SQLException! ");
            System.err.println(e.getMessage());
        }
    }
}

package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileTitleAkas extends ProcessTsvFile {
    public ProcessTsvFileTitleAkas(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO titleAka (tconst, ordering, title, region, language, types, attributes, isOriginalTitle) VALUES (";
                sql += "'" + row[0] + "' "; // tconst
                sql += ", '" + processInteger(row[1]) + "' "; // ordering
                sql += ", '" + processEscapedQuotes(row[2]) + "' "; // title
                sql += ", '" + processEscapedQuotes(row[3]) + "' "; // region
                sql += ", '" + processEscapedQuotes(row[4]) + "' "; // language
                sql += ", '" + processEscapedQuotes(row[5]) + "' "; // types
                sql += ", '" + processEscapedQuotes(row[6]) + "' "; // attributes
                sql += ", '" + processBoolean(row[7]) + "'"; // isOriginalTitle
                sql += ")";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Got an SQLException! ");
            System.err.println(e.getMessage());
        }
    }
}

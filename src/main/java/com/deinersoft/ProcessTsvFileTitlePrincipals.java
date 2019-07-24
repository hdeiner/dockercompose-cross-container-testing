package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileTitlePrincipals extends ProcessTsvFile {
    public ProcessTsvFileTitlePrincipals(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO titlePrincipals (tconst, ordering, nconst, category, job, character) VALUES (";
                sql += "'" + row[0] + "' "; // tconst
                sql += ", '" + processInteger(row[1]) + "' "; // ordering
                sql += ", '" + row[2] + "' "; // nconst
                sql += ", '" + processEscapedQuotes(row[3]) + "' "; // category
                sql += ", '" + processEscapedQuotes(row[4]) + "' "; // job
                sql += ", '" + processEscapedQuotes(row[5]) + "'"; // character
                sql += ")";
                statement.execute(sql);
            }
        } catch (SQLException e) {
            System.err.println("Got an SQLException! ");
            System.err.println(e.getMessage());
        }
    }
}

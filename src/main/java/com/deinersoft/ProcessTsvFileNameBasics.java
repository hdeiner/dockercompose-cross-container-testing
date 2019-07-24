package com.deinersoft;

import java.sql.SQLException;

public class ProcessTsvFileNameBasics extends ProcessTsvFile {
    public ProcessTsvFileNameBasics(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {
                sql = "INSERT INTO name (nconst, primaryName, birthYear, deathYear) VALUES (";
                sql += "'" + row[0] + "' "; // nconst
                sql += ", '" + processEscapedQuotes(row[1]) + "' "; // primaryName
                sql += ", '" + processYear(row[2]) + "' "; // birthYear
                sql += ", '" + processYear(row[3]) + "'"; // deathYear
                sql += ")";
                statement.execute(sql);

                if (row[4] != null) {
                    String[] professions = row[4].split(",[ ]*");
                    for (String profession : professions) {
                        sql = "INSERT INTO nameProfession (nconst, profession) VALUES (";
                        sql += "'" + row[0] + "', "; // nconst
                        sql += "'" + profession + "'"; // profession
                        sql += ")";
                        statement.execute(sql);
                    }
                }

                if (row[5] != null) {
                    String[] titles = row[5].split(",[ ]*");
                    for (String title : titles) {
                        sql = "INSERT INTO nameTitle (nconst, tconst) VALUES (";
                        sql += "'" + row[0] + "', "; // nconst
                        sql += "'" + title + "'"; // tconst
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

package com.deinersoft;

import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class ProcessTsvFileTitleCrew extends ProcessTsvFile {

    public ProcessTsvFileTitleCrew(String tsvFileName, String databaseDriver, String databaseURL, String databaseUsername, String databasePassword) {
        super(tsvFileName, databaseDriver, databaseURL, databaseUsername, databasePassword);
    }

    @Override
    public void processRow(String[] row) {
        try {
            String sql;
            if (tsvProcessedCount != 0) {

                if (row[1].equals("\\N")) row[1] = null;
                if (row[1] != null) {
                    String[] directors = row[1].split(",[ ]*");
                    for (String director : directors) {
                        sql = "INSERT INTO titleDirector (tconst, nconst) VALUES (";
                        sql += "'" + row[0] + "', "; // tconst
                        sql += "'" + director + "'"; // nconst
                        sql += ")";
                        statement.execute(sql);
                    }
                }

                if (row[2].equals("\\N")) row[2] = null;
                if (row[2] != null) {
                    String[] writers = row[2].split(",[ ]*");
                    for (String writer : writers) {
                        sql = "INSERT INTO titleWriter (tconst, nconst) VALUES (";
                        sql += "'" + row[0] + "', "; // tconst
                        sql += "'" + writer + "'"; // nconst
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

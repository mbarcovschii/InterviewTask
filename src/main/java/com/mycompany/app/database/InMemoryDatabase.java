package com.mycompany.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class InMemoryDatabase {

    private static Connection connection;

    public static void connect() throws SQLException {

        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        createTable();
    }

    public static void close() throws SQLException {

        connection.close();
    }

    private static void createTable() throws SQLException {

        connection.createStatement().execute("CREATE TABLE interview(\n" +
                "    ID INTEGER PRIMARY KEY, \n" +
                "    A VARCHAR,\n" +
                "    B VARCHAR,\n" +
                "    C VARCHAR,\n" +
                "    D VARCHAR,\n" +
                "    E VARCHAR,\n" +
                "    F VARCHAR,\n" +
                "    G VARCHAR,\n" +
                "    H VARCHAR,\n" +
                "    I VARCHAR,\n" +
                "    J VARCHAR\n" +
                ");");
    }

    public static void insert(List<HumanPOJO> entityList) throws SQLException {

        if (entityList.size() == 0) {
            return;
        }

        Long currentId = 0L;

        if (isNotEmpty()) {
            currentId = getMaxId() + 1;
        }

        for (HumanPOJO entity : entityList) {

            if (entity.containsFullData()) {

                entity.setId(currentId);
                currentId += 1;
                insert(entity);
            } else {
                // TODO logging
            }
        }
    }

    public static void insert(HumanPOJO entity) throws SQLException {

        PreparedStatement insertStatement =
                connection.prepareStatement(
                        "INSERT INTO interview(ID, A, B, C, D, E, F, G, H, I, J) VALUES(?,?,?,?,?,?,?,?,?,?,?)"
                );

        insertStatement.setLong(1, entity.getId());
        insertStatement.setString(2, entity.getFirstName());
        insertStatement.setString(3, entity.getLastName());
        insertStatement.setString(4, entity.getEmail());
        insertStatement.setString(5, entity.getSex());
        insertStatement.setString(6, entity.getImageInfo());
        insertStatement.setString(7, entity.getWorkplace());
        insertStatement.setString(8, "$" + entity.getHourlyPay().toString());
        insertStatement.setString(9, entity.getCool());
        insertStatement.setString(10, entity.getAwesome());
        insertStatement.setString(11, entity.getResidence());

        insertStatement.executeUpdate();
    }

    private static boolean isNotEmpty() throws SQLException {

        return connection.createStatement().
                executeQuery("SELECT COUNT(ID) AS count FROM interview").getInt("count") > 0;
    }

    private static Long getMaxId() throws SQLException {

        return connection.createStatement().
                executeQuery("SELECT MAX(ID) AS max FROM interview").getLong("max");
    }
}

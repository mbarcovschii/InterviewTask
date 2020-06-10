package com.mycompany.app.database;

import com.mycompany.app.core.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabase {

    public static final String DATA_INSERTING_ERROR_LOG = "logs/dataInsertingLevelErrors/bad-data-";

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

    public static void insert(List<HumanPOJO> entityList) throws SQLException, IOException {

        if (entityList.size() == 0) {
            return;
        }

        long currentId = 0L;

        if (isNotEmpty()) {
            currentId = getMaxId() + 1;
        }

        String curTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        String errorsFilePath = DATA_INSERTING_ERROR_LOG + curTimestamp + ".txt";
        FileWriter errorsWriter = new FileWriter(errorsFilePath);

        for (HumanPOJO entity : entityList) {

            if (entity.containsFullData()) {

                entity.setId(currentId);
                currentId += 1;
                insert(entity);
                Main.recordsSuccessful += 1;
            } else {
                errorsWriter.write(entity.toString());
                Main.recordsFailed += 1;
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
        insertStatement.setString(7, entity.getCard());
        insertStatement.setString(8, "$" + entity.getServiceCost().toString());
        insertStatement.setString(9, entity.getCool());
        insertStatement.setString(10, entity.getAwesome());
        insertStatement.setString(11, entity.getResidence());

        insertStatement.executeUpdate();
    }

    public static List<HumanPOJO> getById(Long id) throws SQLException {

        return get("ID", id.toString());
    }

    public static List<HumanPOJO> getByFirstName(String firstName) throws SQLException {

        return get("A", firstName);
    }

    public static List<HumanPOJO> getByServiceCost(Double hourlyPay) throws SQLException {

        return get("G", "$" + hourlyPay.toString());
    }

    private static List<HumanPOJO> get(String columnName, String searchValue) throws SQLException {

        ResultSet queryResult =
                connection.createStatement().
                        executeQuery("" +
                                "SELECT * FROM interview\n" +
                                "WHERE " + columnName + " = \"" + searchValue + "\"");

        if (queryResult.next()) {

            List<HumanPOJO> entityList = new ArrayList<>();
            do {
                entityList.add(new HumanPOJO(queryResult.getString("A"), queryResult.getString("B"),
                        queryResult.getString("C"), queryResult.getString("D"),
                        queryResult.getString("E"), queryResult.getString("F"),
                        Double.parseDouble(queryResult.getString("G").substring(1)),
                        queryResult.getString("H"), queryResult.getString("I"),
                        queryResult.getString("J")));
            } while (queryResult.next());

            return entityList;
        } else {
            return null;
        }
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

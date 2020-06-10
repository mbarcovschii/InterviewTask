package com.mycompany.app.database;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InMemoryDatabaseTest {

    @BeforeClass
    public static void databaseInitializationTest() throws SQLException {

        InMemoryDatabase.connect();
    }

    @Test
    public void dataInsertionTest() throws SQLException {

        List<HumanPOJO> entityList = new ArrayList<>();

        entityList.add(new HumanPOJO("Bob", "Stone", "coolGuy@gmail.com",
                "MALE", "info", "workplace", 20.3, "TRUE",
                "TRUE", "Moldova, Chisinau"));

        entityList.add(new HumanPOJO("Jason", "Brooks", null,
                "MALE", "info", null, 20.3, "TRUE",
                "TRUE", ""));

        try {
            InMemoryDatabase.insert(entityList);
        } catch (SQLException exception) {

            exception.printStackTrace();
            throw exception;
        }
    }

    @AfterClass
    public static void databaseShutdownTest() throws SQLException {

        InMemoryDatabase.close();
    }
}

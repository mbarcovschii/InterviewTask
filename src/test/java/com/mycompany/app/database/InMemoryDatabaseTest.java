package com.mycompany.app.database;

import org.junit.AfterClass;
import org.junit.Assert;
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
    public void dataManipulationTest() throws SQLException {

        List<HumanPOJO> entityList = new ArrayList<>();

        entityList.add(new HumanPOJO("Bob", "Stone", "coolGuy@gmail.com",
                "MALE", "info", "workplace", 20.3, "TRUE",
                "TRUE", "Moldova, Chisinau"));

        entityList.add(new HumanPOJO("Jason", "Brooks", null,
                "MALE", "info", null, 20.3, "TRUE",
                "TRUE", ""));

        entityList.add(new HumanPOJO("Bob", "Lennon", "someMail@gmail.com",
                "MALE", "info", "workplace", 10.14, "False",
                "True", "The Moon"));

        try {
            InMemoryDatabase.insert(entityList);
        } catch (SQLException exception) {

            exception.printStackTrace();
            throw exception;
        }

        entityList = InMemoryDatabase.getById(0L);
        Assert.assertNotNull(entityList);
        Assert.assertEquals(entityList.get(0).getFirstName(), "Bob");
        Assert.assertEquals(entityList.get(0).getLastName(), "Stone");

        entityList = InMemoryDatabase.getById(2L);
        Assert.assertNull(entityList);

        entityList = InMemoryDatabase.getByFirstName("Bob");
        Assert.assertEquals(entityList.size(), 2);

        entityList = InMemoryDatabase.getByHourlyPay(20.3);
        Assert.assertNotNull(entityList);
        Assert.assertEquals(entityList.get(0).getEmail(), "coolGuy@gmail.com");
    }

    @AfterClass
    public static void databaseShutdownTest() throws SQLException {

        InMemoryDatabase.close();
    }
}

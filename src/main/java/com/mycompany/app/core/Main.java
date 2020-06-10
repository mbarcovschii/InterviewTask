package com.mycompany.app.core;

import com.mycompany.app.database.HumanPOJO;
import com.mycompany.app.database.InMemoryDatabase;

import java.io.FileWriter;
import java.util.List;

public class Main {

    public static int recordsReceived = 0;
    public static int recordsSuccessful = 0;
    public static int recordsFailed = 0;

    public static void main(String[] args) {

        try {
            CSVHandler csvHandler = new CSVHandler();
            List<HumanPOJO> documentCsvObjects =
                    csvHandler.getHumanEntityList("csv/Interview-task-data-osh.csv");

            InMemoryDatabase.connect();
            InMemoryDatabase.insert(documentCsvObjects);

            FileWriter statisticsWriter = new FileWriter("logs/statistics.txt");
            statisticsWriter.write(String.format("%d %s\n%d %s\n%d %s\n",
                    recordsReceived, "records received",
                    recordsSuccessful, "records successful",
                    recordsFailed, "records failed"));
            statisticsWriter.close();

            System.out.println("Lets find if we can select data from database");
            System.out.println(InMemoryDatabase.getById(1024L).get(0));

            InMemoryDatabase.close();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

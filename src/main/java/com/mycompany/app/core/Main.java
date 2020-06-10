package com.mycompany.app.core;

import com.mycompany.app.database.HumanPOJO;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            CSVHandler csvHandler = new CSVHandler();
            List<HumanPOJO> documentCsvObjects =
                    csvHandler.getHumanEntityList("csv/Interview-task-data-osh.csv");

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

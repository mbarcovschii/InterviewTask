package com.mycompany.app.core;

import com.mycompany.app.database.HumanPOJO;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVHandler {

    public static final String PARSING_ERROR_LOG_PATH = "logs/scvParsingLevelErrors/bad-data-";
    public static final String JOKES_LOG_PATH = "logs/jokes.txt";

    public List<String[]> parseDocument(Reader reader) throws IOException, CsvException {

        CSVReader csvReader = new CSVReader(reader);
        List<String[]> result = csvReader.readAll();
        reader.close();
        csvReader.close();
        return result;

    }

    public List<HumanPOJO> getHumanEntityList(String scvResourcePath)
            throws IOException, CsvException {

        ClassLoader classLoader = CSVHandler.class.getClassLoader();
        File file = new File(classLoader.getResource(scvResourcePath).getFile());
        FileReader reader = new FileReader(file);

        List<String[]> parsedDocument = parseDocument(reader);
        Iterator<String[]> iterator = parsedDocument.iterator();
        List<HumanPOJO> result = new ArrayList<>(parsedDocument.size() - 1);


        String curTimestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss"));
        String errorsFilePath = PARSING_ERROR_LOG_PATH + curTimestamp + ".scv";
        CSVWriter errorsWriter = new CSVWriter(new FileWriter(errorsFilePath));
        FileWriter jokesWriter = new FileWriter(JOKES_LOG_PATH);

        while (iterator.hasNext()) {

            Main.recordsReceived += 1;
            String[] entityInfo = iterator.next();

            if (entityInfo.length > 10) {
                for (int i = 10; i < entityInfo.length; i++) {
                    jokesWriter.write(entityInfo[i] + " ");
                }
                jokesWriter.write("\n");
                errorsWriter.writeNext(entityInfo);
                Main.recordsFailed += 1;
                continue;
            }

            if (entityInfo.length < 10 || "".equals(entityInfo[0]) || "".equals(entityInfo[1]) ||
                    "".equals(entityInfo[2]) || "".equals(entityInfo[3]) || "".equals(entityInfo[4]) ||
                    "".equals(entityInfo[5]) || "".equals(entityInfo[6]) || !entityInfo[6].contains("$") ||
                    "".equals(entityInfo[7]) || "".equals(entityInfo[8]) || "".equals(entityInfo[9])) {

                errorsWriter.writeNext(entityInfo);
                Main.recordsFailed += 1;
                continue;
            }

            result.add(new HumanPOJO(entityInfo[0], entityInfo[1], entityInfo[2],
                    entityInfo[3], entityInfo[4], entityInfo[5], Double.parseDouble(entityInfo[6].substring(1)),
                    entityInfo[7], entityInfo[8], entityInfo[9]));
        }

        errorsWriter.close();
        jokesWriter.close();

        return result;
    }
}

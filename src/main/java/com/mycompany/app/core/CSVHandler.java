package com.mycompany.app.core;

import com.mycompany.app.database.HumanPOJO;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CSVHandler {

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

        while (iterator.hasNext()) {

            String[] entityInfo = iterator.next();

            if (entityInfo.length < 10 || !entityInfo[2].contains("@") || !entityInfo[4].contains("data:")
                    || !entityInfo[6].contains("$")) {
                continue;
                //TODO onParsingLevelErrors.log
            }

            if (entityInfo.length > 10) {
                for (int i = 10; i < entityInfo.length; i++) {
                    System.out.print(entityInfo[i] + " ");
                }
                System.out.println();
                //TODO jokes.log, remove cycle
            }

            result.add(new HumanPOJO(entityInfo[0], entityInfo[1], entityInfo[2],
                    entityInfo[3], entityInfo[4], entityInfo[5], Double.parseDouble(entityInfo[6].substring(1)),
                    entityInfo[7], entityInfo[8], entityInfo[9]));
        }

        return result;
    }
}

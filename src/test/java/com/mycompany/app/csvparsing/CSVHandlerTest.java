package com.mycompany.app.csvparsing;

import com.mycompany.app.core.CSVHandler;
import com.opencsv.exceptions.CsvException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVHandlerTest {

    @Test
    public void csvParsingTest() throws IOException, CsvException {

        CSVHandler csvHandler = new CSVHandler();
        List<String[]> parsedDocument = csvHandler.parseDocument(
                new FileReader(new File("src/test/testresources/test.csv"))
        );

        String[] firstLine = { "name1", "20.4", "workplace1", "someInfo1" };
        String[] secondLine = { "name2", "", "", "someInfo2" };
        String[] thirdLine = { "", "", "", "" };
        String[] fourthLine = { "name4", "109.23", "workplace41, workplace42, workplace43", "someInfo4" };

        Assert.assertArrayEquals(firstLine, parsedDocument.get(1));
        Assert.assertArrayEquals(secondLine, parsedDocument.get(2));
        Assert.assertArrayEquals(thirdLine, parsedDocument.get(3));
        Assert.assertArrayEquals(fourthLine, parsedDocument.get(4));
    }
}

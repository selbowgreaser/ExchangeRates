package org.selbowgreaser.data.reader;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.ExchangeRatesDataException;
import org.selbowgreaser.data.handler.ExchangeRatesDataHandler;
import org.selbowgreaser.request.parameters.Currency;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Slf4j
public class ExchangeRatesFileReaderImpl implements ExchangeRatesFileReader {
    private static final int SKIP_ROWS = 1;
    private static final Character SEPARATOR = ';';

    @Override
    public List<ExchangeRate> readExchangeRatesFiles() {
        log.debug("Start reading data...");
        List<ExchangeRate> data = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            String fileName = MessageFormat.format("{0}/{1}.csv", getFolderPath(), currency);
            data.addAll(parseFile(fileName));
        }
        log.debug("All data read successfully!");
        return data;
    }

    private List<ExchangeRate> parseFile(String fileName) {
        List<ExchangeRate> data;
        log.debug(MessageFormat.format("Start reading {0}", fileName));
        try (FileReader file = new FileReader(fileName)) {
            data = new CsvToBeanBuilder<ExchangeRate>(file)
                    .withType(ExchangeRate.class)
                    .withSeparator(SEPARATOR)
                    .withSkipLines(SKIP_ROWS)
                    .build()
                    .parse();
            log.debug(MessageFormat.format("{0} read successfully!", fileName));
        } catch (IOException exception) {
            throw new ExchangeRatesDataException(MessageFormat.format("The file \"{0}\" does not exist", fileName));
        }
        return data;
    }

    private String getFolderPath() {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            Properties properties = new Properties();
            properties.load(input);
            return properties.getProperty("dataFolder");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

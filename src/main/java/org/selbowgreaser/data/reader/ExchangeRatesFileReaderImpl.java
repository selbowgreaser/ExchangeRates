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
import java.util.List;
import java.util.Properties;

@Slf4j
public class ExchangeRatesFileReaderImpl implements ExchangeRatesFileReader {
    private final int SKIP_ROWS = 1;
    private final Character SEPARATOR = ';';

    @Override
    public List<ExchangeRate> readExchangeRatesFile(Currency currency) {
        log.info("Start reading data...");

        String fileName = MessageFormat.format("{0}/{1}.csv", getFolderPath(), currency);

        List<ExchangeRate> data;

        data = getExchangeRates(fileName);

        return new ExchangeRatesDataHandler().fillMissingDays(data);
    }

    private List<ExchangeRate> getExchangeRates(String fileName) {
        List<ExchangeRate> data;
        try (FileReader file = new FileReader(fileName)) {
            data = new CsvToBeanBuilder<ExchangeRate>(file)
                    .withType(ExchangeRate.class)
                    .withSeparator(SEPARATOR)
                    .withSkipLines(SKIP_ROWS)
                    .build()
                    .parse();
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

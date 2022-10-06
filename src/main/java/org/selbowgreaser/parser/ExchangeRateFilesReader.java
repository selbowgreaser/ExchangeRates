package org.selbowgreaser.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.selbowgreaser.request.Currency;
import org.selbowgreaser.request.UserRequest;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ExchangeRateFilesReader {
    private final UserRequest request;
    private final int SKIP_ROWS = 1;
    private final int COUNT_LINES = 7;
    private final int FIRST_LINE = 0;
    private final String DATE_PATTERN = "dd.MM.yyyy";


    public ExchangeRateFilesReader(UserRequest request) {
        this.request = request;
    }

    public List<ExchangeRateData> parseExchangeRatesFile() {
        List<ExchangeRateData> exchangeRateDataList = new ArrayList<>();

        for (Currency currency : request.getCurrencies()) {
            String fileName = MessageFormat.format("{0}/{1}.csv", getFolderPath(), currency);

            List<ExchangeRate> beans;

            try (FileReader file = new FileReader(fileName)) {
                beans = new CsvToBeanBuilder<ExchangeRate>(file)
                        .withType(ExchangeRate.class)
                        .withSeparator(';')
                        .withSkipLines(SKIP_ROWS)
                        .build()
                        .parse();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            List<Double> lastSevenData = new ArrayList<>();
            for (int i = 0; i < COUNT_LINES; i++) {
                lastSevenData.add(beans.get(i).getCurs());
            }

            LocalDate lastDate = convertStringToDate(beans.get(FIRST_LINE).getDate());

            exchangeRateDataList.add(new ExchangeRateData(lastDate, lastSevenData, currency));
        }
        return exchangeRateDataList;
    }

    private LocalDate convertStringToDate(String dateInString) {
        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN));
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

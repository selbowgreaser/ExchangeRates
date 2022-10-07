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
    private final Character SEPARATOR = ';';


    public ExchangeRateFilesReader(UserRequest request) {
        this.request = request;
    }

    public List<ExchangeRateData> parseExchangeRatesFile() {
        List<ExchangeRateData> exchangeRateDataList = new ArrayList<>();

        for (Currency currency : request.getCurrencies()) {
            String fileName = MessageFormat.format("{0}/{1}.csv", getFolderPath(), currency);

            List<ExchangeRate> data;

            data = getExchangeRates(fileName);

            List<Double> dataForForecasting = new ArrayList<>();
            for (int i = 0; i < COUNT_LINES; i++) {
                dataForForecasting.add(data.get(i).getExchangeRate());
            }

            LocalDate lastDate = convertStringToDate(data.get(FIRST_LINE).getDate());

            exchangeRateDataList.add(new ExchangeRateData(lastDate, dataForForecasting, lastSevenDenomination, currency));
        }
        return exchangeRateDataList;
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
            throw new ExchangeRateDataException(MessageFormat.format("The file {0} does not exist", fileName));
        }
        return data;
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

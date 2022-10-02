package org.selbowgreaser.parser;

import com.opencsv.bean.CsvToBeanBuilder;
import org.selbowgreaser.command.UserRequest;

import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExchangeRateFilesReader {
    private final UserRequest request;

    public ExchangeRateFilesReader(UserRequest request) {
        this.request = request;
    }

    public ExchangeRateData parseExchangeRatesFile() {
        String fileName = "src/main/resources/" + request.getCurrency().toUpperCase() + ".csv"; //todo используй MessageFormat.format()
        List<ExchangeRate> beans;

        try (FileReader file = new FileReader(fileName)) {
            beans = new CsvToBeanBuilder<ExchangeRate>(file)
                    .withType(ExchangeRate.class)
                    .withSeparator(';')
                    .withSkipLines(1)  //todo вывести в константу и назвать для чего она
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Double> lastSevenData = new ArrayList<>();
        for (int i = 0; i < 7; i++) { //todo вывести в константу и назвать для чего она
            lastSevenData.add(beans.get(i).getCurs());
        }

        LocalDate lastDate = convertStringToDate(beans.get(0).getDate()); //todo вывести в константу и назвать для чего она

        return new ExchangeRateData(lastDate, lastSevenData);
    }

    private LocalDate convertStringToDate(String dateInString) {

        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern("dd.MM.yyyy")); //todo вывести в константу и назвать для чего она
    }
}

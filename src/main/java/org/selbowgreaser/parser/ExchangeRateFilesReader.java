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
    private final int SKIP_ROWS = 1;
    private final int COUNT_LINES = 7;
    private final int FIRST_LINE = 0;
    private final String DATE_PATTERN = "dd.MM.yyyy";


    public ExchangeRateFilesReader(UserRequest request) {
        this.request = request;
    }

    public ExchangeRateData parseExchangeRatesFile() {
        String fileName = MessageFormat.format("src/main/resources/{0}.csv", request.getCurrency().toUpperCase()); //todo используй MessageFormat.format() // done
        List<ExchangeRate> beans;

        try (FileReader file = new FileReader(fileName)) {
            beans = new CsvToBeanBuilder<ExchangeRate>(file)
                    .withType(ExchangeRate.class)
                    .withSeparator(';')
                    .withSkipLines(SKIP_ROWS)  //todo вывести в константу и назвать для чего она // done
                    .build()
                    .parse();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Double> lastSevenData = new ArrayList<>();
        for (int i = 0; i < COUNT_LINES; i++) { //todo вывести в константу и назвать для чего она // done
            lastSevenData.add(beans.get(i).getCurs());
        }

        LocalDate lastDate = convertStringToDate(beans.get(FIRST_LINE).getDate()); //todo вывести в константу и назвать для чего она // done

        return new ExchangeRateData(lastDate, lastSevenData);
    }

    private LocalDate convertStringToDate(String dateInString) {

        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN)); //todo вывести в константу и назвать для чего она // done
    }
}

package org.selbowgreaser.data.handler;

import org.selbowgreaser.data.ExchangeRate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExchangeRatesDataHandler {
    private final String DATE_PATTERN = "dd.MM.yyyy";

    public List<ExchangeRate> fillMissingDays(List<ExchangeRate> data) {
        int size = data.size();
        LocalDate yesterdayDate = convertStringToDate(data.get(size - 1).getDate());
        for (int i = size - 2; i >= 0; i--) {
            ExchangeRate yesterdayData = data.get(i + 1);
            LocalDate todayDate = convertStringToDate(data.get(i).getDate());
            if (!yesterdayDate.plusDays(1).equals(todayDate)) {
                data.add(++i, new ExchangeRate(yesterdayData.getDenomination(),
                        convertDateToString(yesterdayDate.plusDays(1)),
                        yesterdayData.getExchangeRate(),
                        yesterdayData.getCdx()));
            }
            yesterdayDate = yesterdayDate.plusDays(1);
        }
        return data;
    }

    public LocalDate convertStringToDate(String dateInString) {
        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }

    private String convertDateToString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}

package org.selbowgreaser.service.handler;

import org.selbowgreaser.model.parameter.Period;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class DateHandler {
    public static final String DATE_PATTERN = "dd.MM.yyyy";
    private final int DAYS_IN_WEEK = 7;
    private final int DAYS_IN_MONTH = 30;

    public List<LocalDate> processPeriodOrDate(String periodOrDate) {
        if (periodOrDate.equals(Period.TOMORROW.name())) {
            return Collections.singletonList(getDateToday().plusDays(1));
        } else if (periodOrDate.equals(Period.WEEK.name())) {
            return IntStream.rangeClosed(1, DAYS_IN_WEEK).mapToObj(i -> getDateToday().plusDays(i)).collect(Collectors.toList());
        } else if (periodOrDate.equals(Period.MONTH.name())) {
            return IntStream.rangeClosed(1, DAYS_IN_MONTH).mapToObj(i -> getDateToday().plusDays(i)).collect(Collectors.toList());
        } else {
            return Collections.singletonList(convertStringToDate(periodOrDate).plusDays(1));
        }
    }

    public static LocalDate getDateToday() {
        try (FileInputStream input = new FileInputStream("src/main/resources/config.properties")) {
            String dateTodayInString;
            Properties properties = new Properties();
            properties.load(input);
            dateTodayInString = properties.getProperty("today");
            return convertStringToDate(dateTodayInString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDate convertStringToDate(String dateInString) {
        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}

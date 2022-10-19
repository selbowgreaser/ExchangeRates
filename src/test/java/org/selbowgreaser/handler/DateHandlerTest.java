package org.selbowgreaser.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.selbowgreaser.handler.DateHandler.convertStringToDate;
import static org.selbowgreaser.handler.DateHandler.getDateToday;

class DateHandlerTest {

    @Test
    void successfulProcessingPeriodTomorrow() {
        DateHandler dateHandler = new DateHandler();
        List<LocalDate> expectedDates = Collections.singletonList(getDateToday().plusDays(1));
        List<LocalDate> actualDates = dateHandler.processPeriodOrDate("TOMORROW");
        Assertions.assertEquals(expectedDates, actualDates);
    }

    @Test
    void successfulProcessingPeriodWeek() {
        DateHandler dateHandler = new DateHandler();
        List<LocalDate> dates = dateHandler.processPeriodOrDate("WEEK");
        System.out.println(dates);
    }

    @Test
    void successfulProcessingPeriodMonth() {
        DateHandler dateHandler = new DateHandler();
        List<LocalDate> dates = dateHandler.processPeriodOrDate("MONTH");
        System.out.println(dates);
    }

    @Test
    void successfulConvertingStringToDate() {
        LocalDate exampleDate = LocalDate.of(2022, 10, 22);
        Assertions.assertEquals(exampleDate, convertStringToDate("22.10.2022"));
    }
}
package org.selbowgreaser.service.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static org.selbowgreaser.service.handler.DateHandler.convertStringToDate;
import static org.selbowgreaser.service.handler.DateHandler.getDateToday;

class DateHandlerTest {
    DateHandler dateHandler = new DateHandler();

    @Test
    void processPeriodOrDate_shouldGetListWithDateOfTomorrow_whenTomorrowIsRequested() {
        List<LocalDate> expectedDates = Collections.singletonList(getDateToday().plusDays(1));
        List<LocalDate> actualDates = dateHandler.processPeriodOrDate("TOMORROW");
        Assertions.assertEquals(expectedDates, actualDates);
    }

    @Test
    void processPeriodOrDate_shouldGet7Values_whenWeekIsRequested() {
        int expectedSize = 7;
        int actualSize = dateHandler.processPeriodOrDate("WEEK").size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void processPeriodOrDate_shouldGet30Values_whenMonthIsRequested() {
        int expectedSize = 30;
        int actualSize = dateHandler.processPeriodOrDate("MONTH").size();
        Assertions.assertEquals(expectedSize, actualSize);
    }

    @Test
    void processPeriodOrDate_shouldThrowDateTimeParseException_whenWrongPeriodIsRequested() {
        Assertions.assertThrows(DateTimeParseException.class, () -> dateHandler.processPeriodOrDate("KEK"));
    }

    @Test
    void getDateToday_shouldGetDateFromConfig_whenConfigContainsDateToday() {
        LocalDate expectedDate = LocalDate.of(2022, 10, 8);
        LocalDate actualDate = DateHandler.getDateToday();
        Assertions.assertEquals(expectedDate, actualDate);
    }

    @Test
    void convertStringToDate_shouldGetLocalDate_whenDateIsInRightFormat() {
        LocalDate exampleDate = LocalDate.of(2022, 10, 22);
        Assertions.assertEquals(exampleDate, convertStringToDate("22.10.2022"));
    }

    @Test
    void convertStringToDate_shouldThrowDateTimeParseException_whenDateIsNotInRightFormat() {
        Assertions.assertThrows(DateTimeParseException.class, () -> convertStringToDate("22-10-2022"));
    }
}
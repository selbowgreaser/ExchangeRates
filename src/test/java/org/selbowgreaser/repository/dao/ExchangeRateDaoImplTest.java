package org.selbowgreaser.repository.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.selbowgreaser.TestDataGenerator;
import org.selbowgreaser.repository.dao.ExchangeRateDao;
import org.selbowgreaser.repository.dao.ExchangeRateDaoImpl;
import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.service.reader.ExchangeRatesFileReader;
import org.selbowgreaser.model.parameter.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

class ExchangeRateDaoImplTest {
    ExchangeRatesFileReader reader = Mockito.mock(ExchangeRatesFileReader.class);
    TestDataGenerator dataGenerator = new TestDataGenerator();

    @Test
    void getDataForDate_shouldGetRequiredObject_whenThereIsDataForThatDate() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        ExchangeRate expected = new ExchangeRate(BigDecimal.ONE, LocalDate.of(2018, 10, 24), BigDecimal.valueOf(57), "Доллар США");

        ExchangeRate actual = exchangeRateDao.getDataForDate(Currency.USD, LocalDate.of(2018, 10, 24));

        Assertions.assertEquals(expected, actual);
    }


    @Test
    void getDataForDate_shouldGetRequiredObject_whenThereIsNotDataForThatDate() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());


        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        ExchangeRate expected = new ExchangeRate(BigDecimal.ONE, LocalDate.of(2015, 7, 15), BigDecimal.valueOf(45), "Евро");

        ExchangeRate actual = exchangeRateDao.getDataForDate(Currency.EUR, LocalDate.of(2015, 7, 16));

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getDataForDate_shouldGetNoSuchElementException_whenMissingEarlierDates() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        Assertions.assertThrows(NoSuchElementException.class, () -> exchangeRateDao.getDataForDate(Currency.BGN, LocalDate.of(1999, 10, 11)));
    }

    @Test
    void getDataForDatesOfLastYear_shouldGetRequiredObjects_whenDataAvailable() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        List<ExchangeRate> expected = dataGenerator.generateExpectedDataForTests(Currency.BGN);

        Assertions.assertEquals(expected, exchangeRateDao.getDataForDatesOfLastYear(Currency.BGN, dataGenerator.generateDateForTests()));
    }

    @Test
    void getDataForDatesOfRandomYear_shouldGetRequiredObjects_whenDataAvailable() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        List<ExchangeRate> expected = List.of(new ExchangeRate(BigDecimal.ONE, LocalDate.of(2012, 4, 6), BigDecimal.valueOf(39), "Армянский драм"));

        Assertions.assertEquals(expected, exchangeRateDao.getDataForDatesOfRandomYear(Currency.AMD, List.of(LocalDate.of(2022, 10, 22)), new Random(42)));
    }

    @Test
    void getDataForLastNDays_shouldThrowIndexOutOfBoundsException_whenDataHasLessThanThirtyValues() {
        Mockito.when(reader.readExchangeRatesFiles()).thenReturn(dataGenerator.generateDataForTests());

        ExchangeRateDao exchangeRateDao = new ExchangeRateDaoImpl(reader);

        Assertions.assertThrows(IndexOutOfBoundsException.class, () -> exchangeRateDao.getDataForLastNDays(Currency.TRY, 30));
    }
}
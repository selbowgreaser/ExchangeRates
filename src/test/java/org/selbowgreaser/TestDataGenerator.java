package org.selbowgreaser;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.model.parameter.Currency;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestDataGenerator {
    public List<ExchangeRate> generateDataForCurrency(Currency currency) {
        String nameOfCurrency = getNameOfCurrency(currency);
        return List.of(
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2020, 12, 30), BigDecimal.valueOf(70), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2019, 11, 27), BigDecimal.valueOf(63), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2018, 10, 24), BigDecimal.valueOf(57), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2017, 9, 21), BigDecimal.valueOf(52), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2016, 8, 18), BigDecimal.valueOf(48), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2015, 7, 15), BigDecimal.valueOf(45), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2014, 6, 12), BigDecimal.valueOf(43), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2013, 5, 9), BigDecimal.valueOf(42), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2012, 4, 6), BigDecimal.valueOf(39), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2002, 1, 1), BigDecimal.valueOf(38), nameOfCurrency)
        );
    }

    public Map<Currency, List<ExchangeRate>> generateDataForTests() {
        Map<Currency, List<ExchangeRate>> data = new HashMap<>();
        for (Currency currency : Currency.values()) {
            data.put(currency, generateDataForCurrency(currency));
        }
        return data;
    }

    public List<LocalDate> generateDateForTests() {
        return List.of(
                LocalDate.of(2020, 12, 30),
                LocalDate.of(2019, 11, 27),
                LocalDate.of(2018, 10, 24),
                LocalDate.of(2017, 9, 21),
                LocalDate.of(2016, 8, 18)
        );
    }

    public List<ExchangeRate> generateExpectedDataForTests(Currency currency) {
        String nameOfCurrency = getNameOfCurrency(currency);
        return List.of(
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2019, 11, 27), BigDecimal.valueOf(63), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2018, 10, 24), BigDecimal.valueOf(57), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2017, 9, 21), BigDecimal.valueOf(52), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2016, 8, 18), BigDecimal.valueOf(48), nameOfCurrency),
                new ExchangeRate(BigDecimal.ONE, LocalDate.of(2015, 7, 15), BigDecimal.valueOf(45), nameOfCurrency)
        );
    }

    private static String getNameOfCurrency(Currency currency) {
        String nameOfCurrency;
        if (currency.equals(Currency.AMD)) {
            nameOfCurrency = "Армянский драм";
        } else if (currency.equals(Currency.BGN)) {
            nameOfCurrency = "Болгарский лев";
        } else if (currency.equals(Currency.EUR)) {
            nameOfCurrency = "Евро";
        } else if (currency.equals(Currency.TRY)) {
            nameOfCurrency = "Турецкая лира";
        } else {
            nameOfCurrency = "Доллар США";
        }
        return nameOfCurrency;
    }
}

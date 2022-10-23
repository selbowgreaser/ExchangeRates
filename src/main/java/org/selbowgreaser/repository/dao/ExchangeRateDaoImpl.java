package org.selbowgreaser.repository.dao;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.service.reader.ExchangeRatesFileReader;
import org.selbowgreaser.model.parameter.Currency;

import java.time.LocalDate;
import java.util.*;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
    public static final int FIRST_YEAR_IN_DATA = 2005;
    private final Map<Currency, List<ExchangeRate>> data;

    public ExchangeRateDaoImpl(ExchangeRatesFileReader reader) {
        this.data = reader.readExchangeRatesFiles();
    }

    @Override
    public ExchangeRate getDataForDate(Currency currency, LocalDate date) {
        return data.get(currency).stream()
                .filter(unit -> unit.getDate().isBefore(date.plusDays(1)))
                .max(Comparator.comparing(ExchangeRate::getDate))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public List<ExchangeRate> getDataForDatesOfLastYear(Currency currency, List<LocalDate> dates) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            date = date.minusYears(1);
            exchangeRates.add(getDataForDate(currency, date));
        }
        return exchangeRates;
    }

    @Override
    public List<ExchangeRate> getDataForDatesOfRandomYear(Currency currency, List<LocalDate> dates, Random random) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            date = date.minusYears(1);
            int year = date.getYear();
            List<ExchangeRate> valuesForDate = new ArrayList<>();
            while (year >= FIRST_YEAR_IN_DATA) {
                valuesForDate.add(getDataForDate(currency, LocalDate.of(year, date.getMonthValue(), date.getDayOfMonth())));
                year--;
            }
            exchangeRates.add(valuesForDate.get(random.nextInt(valuesForDate.size())));
        }
        return exchangeRates;
    }

    @Override
    public List<ExchangeRate> getDataForLastNDays(Currency currency, int days) {
        return data.get(currency).subList(0, days);
    }
}

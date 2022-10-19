package org.selbowgreaser.data;

import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.util.*;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
    public static final int DAYS_IN_MONTH = 30;
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
    public List<ExchangeRate> getDataForLastYearAlgorithm(Currency currency, List<LocalDate> dates) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            date = date.minusYears(1);
            exchangeRates.add(getDataForDate(currency, date));
        }
        return exchangeRates;
    }

    @Override
    public List<ExchangeRate> getDataForMysticalAlgorithm(Currency currency, List<LocalDate> dates, Random random) {
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            int year = Integer.min(date.getYear(), 2022);
            List<ExchangeRate> valuesForDate = new ArrayList<>();
            while (year > 2001) {
                valuesForDate.add(getDataForDate(currency, LocalDate.of(year, date.getMonthValue(), date.getDayOfMonth())));
                year--;
            }
            exchangeRates.add(valuesForDate.get(random.nextInt(valuesForDate.size())));
        }
        return exchangeRates;
    }

    @Override
    public List<ExchangeRate> getDataForLinearRegressionAlgorithm(Currency currency) {
        return data.get(currency).subList(0, DAYS_IN_MONTH);
    }
}

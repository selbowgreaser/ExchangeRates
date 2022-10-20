package org.selbowgreaser.model.dao;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public interface ExchangeRateDao {
    ExchangeRate getDataForDate(Currency currency, LocalDate date);

    List<ExchangeRate> getDataForLastYearAlgorithm(Currency currency, List<LocalDate> dates);

    List<ExchangeRate> getDataForMysticalAlgorithm(Currency currency, List<LocalDate> dates, Random random);

    List<ExchangeRate> getDataForLinearRegressionAlgorithm(Currency currency);
}

package org.selbowgreaser.repository.dao;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.model.parameter.Currency;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public interface ExchangeRateDao {
    ExchangeRate getDataForDate(Currency currency, LocalDate date);

    List<ExchangeRate> getDataForDatesOfLastYear(Currency currency, List<LocalDate> dates);

    List<ExchangeRate> getDataForDatesOfRandomYear(Currency currency, List<LocalDate> dates, Random random);

    List<ExchangeRate> getDataForLastNDays(Currency currency, int days);
}

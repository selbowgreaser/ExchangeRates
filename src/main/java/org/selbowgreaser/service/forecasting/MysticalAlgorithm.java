package org.selbowgreaser.service.forecasting;

import org.selbowgreaser.repository.dao.ExchangeRateDao;
import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.model.data.PredictedExchangeRate;
import org.selbowgreaser.model.RequestResult;
import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MysticalAlgorithm implements ForecastingAlgorithm {
    public static final int SCALE = 2;
    private final ExchangeRateDao exchangeRateDao;
    private final Random random;

    public MysticalAlgorithm(ExchangeRateDao exchangeRateDao, Random random) {
        this.exchangeRateDao = exchangeRateDao;
        this.random = random;
    }

    @Override
    public RequestResult forecast(Currency currency, List<LocalDate> dates) {
        List<ExchangeRate> exchangeRates = exchangeRateDao.getDataForDatesOfRandomYear(currency, dates, random);
        List<PredictedExchangeRate> predictedExchangeRates = IntStream.range(0, dates.size())
                .mapToObj(i -> new PredictedExchangeRate(dates.get(i),
                        exchangeRates.get(i).getExchangeRate().divide(exchangeRates.get(i).getDenomination(), SCALE, RoundingMode.HALF_UP)))
                .collect(Collectors.toList());
        return new RequestResult(currency, Algorithm.MYSTICAL, predictedExchangeRates);
    }
}
package org.selbowgreaser.forecasting;

import org.selbowgreaser.model.dao.ExchangeRateDao;
import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.model.data.PredictedExchangeRate;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;

import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LastYearAlgorithm implements ForecastingAlgorithm {
    public static final int SCALE = 2;
    private final ExchangeRateDao exchangeRateDao;

    public LastYearAlgorithm(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }


    @Override
    public RequestResult forecast(Currency currency, List<LocalDate> dates) {
        List<ExchangeRate> exchangeRates = exchangeRateDao.getDataForLastYearAlgorithm(currency, dates);
        List<PredictedExchangeRate> predictedExchangeRates = IntStream.range(0, dates.size())
                .mapToObj(i -> new PredictedExchangeRate(dates.get(i),
                        exchangeRates.get(i).getExchangeRate().divide(exchangeRates.get(i).getDenomination(), SCALE, RoundingMode.HALF_UP)))
                .collect(Collectors.toList());
        return new RequestResult(currency, Algorithm.LAST_YEAR, predictedExchangeRates);
    }
}

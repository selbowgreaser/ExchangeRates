package org.selbowgreaser.forecasting;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.PredictedExchangeRate;
import org.selbowgreaser.data.extractor.DataExtractorForMystAlgorithm;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MysticalAlgorithm implements ForecastingAlgorithm {
    private final Currency currency;
    private final List<LocalDate> dates;

    public MysticalAlgorithm(Currency currency, List<LocalDate> dates) {
        this.currency = currency;
        this.dates = dates;
    }

    @Override
    public RequestResult forecast() {
        List<ExchangeRate> exchangeRates = new DataExtractorForMystAlgorithm(currency, dates).extractData();
        List<PredictedExchangeRate> predictedExchangeRates = IntStream.range(0, dates.size())
                .mapToObj(i -> new PredictedExchangeRate(dates.get(i), exchangeRates.get(i).getExchangeRate()))
                .collect(Collectors.toList());
        return new RequestResult(currency, Algorithm.MYSTICAL, predictedExchangeRates);
    }
}
package org.selbowgreaser.forecasting;

import lombok.Getter;
import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.PredictedExchangeRate;
import org.selbowgreaser.data.extractor.DataExtractorForAvgAlgorithm;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.Period;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class AverageAlgorithm implements ForecastingAlgorithm {
    public static final int FIRST_LINE = 0;
    private final Currency currency;
    private final String periodOrDate;
    private final int SCALE = 2;
    private final String DATE_PATTERN = "dd.MM.yyyy";

    public AverageAlgorithm(Currency currency, String periodOrDate) {
        this.currency = currency;
        this.periodOrDate = periodOrDate;
    }

    @Override
    public RequestResult forecast() {

        List<ExchangeRate> exchangeRates = new DataExtractorForAvgAlgorithm(currency).extractData();

        try {
            if (Period.valueOf(periodOrDate).equals(Period.TOMORROW)) {
                return new RequestResult(currency, Algorithm.AVERAGE, forecastTomorrow(exchangeRates));
            } else {
                return new RequestResult(currency, Algorithm.AVERAGE, forecastWeek(exchangeRates));
            }
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
    }

    private List<PredictedExchangeRate> forecastTomorrow(List<ExchangeRate> exchangeRates) {
        LocalDate tomorrowDate = convertStringToDate(exchangeRates.get(FIRST_LINE).getDate()).plusDays(1);
        List<BigDecimal> rates = extractExchangeRate(exchangeRates);
        BigDecimal sum = getSumArray(rates);
        return List.of(new PredictedExchangeRate(tomorrowDate,
                sum.divide(BigDecimal.valueOf(rates.size()), SCALE, RoundingMode.HALF_UP)));
    }

    private List<PredictedExchangeRate> forecastWeek(List<ExchangeRate> exchangeRates) {
        List<BigDecimal> rates = extractExchangeRate(exchangeRates);
        List<PredictedExchangeRate> predictedExchangeRates = new ArrayList<>();
        LocalDate tomorrowDate = convertStringToDate(exchangeRates.get(FIRST_LINE).getDate()).plusDays(1);
        for (int i = exchangeRates.size() - 1; i >= 0; i--) {
            BigDecimal sum = getSumArray(rates);
            BigDecimal predictedValue = sum.divide(BigDecimal.valueOf(rates.size()), SCALE, RoundingMode.HALF_UP);
            rates.set(i, predictedValue);
            predictedExchangeRates.add(new PredictedExchangeRate(tomorrowDate, predictedValue));
            tomorrowDate = tomorrowDate.plusDays(1);
        }
        return predictedExchangeRates;
    }

    private List<BigDecimal> extractExchangeRate(List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .map(exchangeRate -> exchangeRate.getExchangeRate().divide(exchangeRate.getDenomination(), SCALE, RoundingMode.HALF_UP))
                .toList();
    }

    private BigDecimal getSumArray(List<BigDecimal> rates) {
        BigDecimal sum = BigDecimal.ZERO;
        for (BigDecimal value : rates) {
            sum = sum.add(value);
        }
        return sum;
    }

    public LocalDate convertStringToDate(String dateInString) {
        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}

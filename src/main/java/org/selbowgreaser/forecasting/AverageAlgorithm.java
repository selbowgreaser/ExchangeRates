package org.selbowgreaser.forecasting;

import lombok.Getter;
import org.selbowgreaser.request.Period;
import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.parser.ExchangeRateData;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AverageAlgorithm implements ForecastingAlgorithm {
    private final Integer REQUIRED_VALUES = 7;


    @Override
    public List<List<Double>> forecast(UserRequest request, List<ExchangeRateData> exchangeRateDataList) {
        List<List<Double>> allPredictions = new ArrayList<>();
        for (ExchangeRateData exchangeRateData : exchangeRateDataList) {
            List<Double> predictions = new ArrayList<>();
            String period = request.getPeriodOrDate();
            List<Double> lastSevenValues = exchangeRateData.getExchangeRates();

            if (period.equals(Period.TOMORROW.getPeriod())) {
                allPredictions.add(forecastTomorrow(predictions, lastSevenValues));
            } else if (period.equals(Period.WEEK.getPeriod())) {
                allPredictions.add(forecastWeek(lastSevenValues));
            }
        }
        return allPredictions;
    }


    private List<Double> forecastWeek(List<Double> lastSevenValues) {
        lastSevenValues.replaceAll(ignored -> {
            return getSumArray(lastSevenValues) / lastSevenValues.size();
        });
        return lastSevenValues;
    }

    private double getSumArray(List<Double> lastSevenValues) {
        double sum = 0;
        for (Double value : lastSevenValues) {
            sum += value;
        }
        return sum;
    }

    private List<Double> forecastTomorrow(List<Double> predictions, List<Double> lastSevenValues) {
        double sum = getSumArray(lastSevenValues);
        predictions.add(sum / lastSevenValues.size());
        return predictions;
    }
}

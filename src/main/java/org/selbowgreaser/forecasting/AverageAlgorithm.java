package org.selbowgreaser.forecasting;

import org.selbowgreaser.command.UserRequest;
import org.selbowgreaser.parser.ExchangeRateData;

import java.util.ArrayList;
import java.util.List;

public class AverageAlgorithm implements ForecastingAlgorithm {

    @Override
    public List<Double> forecast(UserRequest request, ExchangeRateData exchangeRateData) {
        List<Double> predictions = new ArrayList<>();
        String period = request.getPeriod();
        List<Double> lastSevenValues = exchangeRateData.getData();

        if (period.equals("tomorrow")) {
            return forecastTomorrow(predictions, lastSevenValues);
        } else if (period.equals("week")) {
            return forecastWeek(lastSevenValues);
        }
        return predictions;
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

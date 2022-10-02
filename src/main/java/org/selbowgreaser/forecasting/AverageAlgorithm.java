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

    //todo убери статику методов ниже у методов, она излищне

    private static List<Double> forecastWeek(List<Double> lastSevenValues) {
        for (int i = 0; i < lastSevenValues.size(); i++) { //todo идея подсказывает заменить цикл, попробуй, если отрабатывает как нужно, поменяй
            double sum = getSumArray(lastSevenValues);
            lastSevenValues.set(i, sum / lastSevenValues.size());
        }
        return lastSevenValues;
    }

    private static double getSumArray(List<Double> lastSevenValues) {
        double sum = 0;
        for (Double value : lastSevenValues) {
            sum += value;
        }
        return sum;
    }

    private static List<Double> forecastTomorrow(List<Double> predictions, List<Double> lastSevenValues) {
        double sum = getSumArray(lastSevenValues);
        predictions.add(sum / lastSevenValues.size());
        return predictions;
    }
}

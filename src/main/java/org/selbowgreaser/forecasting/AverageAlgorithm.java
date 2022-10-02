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
        List<Double> lastSevenValue = exchangeRateData.getData(); //todo название lastSevenValues

        if (period.equals("tomorrow")) {
            double sum = 0;
            for (Double value : lastSevenValue) { //todo вынести в приватный метод
                sum += value;
            }
            predictions.add(sum / 7); //todo вывести в константу и назвать для чего она
            return predictions;
        } else if (period.equals("week")) {
            for (int i = 0; i < 7; i++) { //todo вывести в константу и назвать для чего она +
                double sum = 0;
                for (Double value : lastSevenValue) { //todo вынести в приватный метод
                    sum += value;
                }
                lastSevenValue.set(i, sum / 7); //todo вывести в константу и назвать для чего она +
            }
            return lastSevenValue;
        }
        return predictions;
    }
}

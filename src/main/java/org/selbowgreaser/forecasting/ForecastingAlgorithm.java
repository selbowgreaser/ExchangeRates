package org.selbowgreaser.forecasting;

import org.selbowgreaser.command.UserRequest;
import org.selbowgreaser.parser.ExchangeRateData;

import java.util.List;

public interface ForecastingAlgorithm {

    List<Double> forecast(UserRequest request, ExchangeRateData exchangeRateData);
}

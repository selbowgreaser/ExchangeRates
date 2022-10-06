package org.selbowgreaser.forecasting;

import org.selbowgreaser.request.UserRequest;
import org.selbowgreaser.parser.ExchangeRateData;

import java.util.List;

public interface ForecastingAlgorithm {

    List<List<Double>> forecast(UserRequest request, List<ExchangeRateData> exchangeRateData);
}

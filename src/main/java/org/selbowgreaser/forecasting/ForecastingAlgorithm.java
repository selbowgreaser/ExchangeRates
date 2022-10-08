package org.selbowgreaser.forecasting;

import org.selbowgreaser.parser.ExchangeRateData;
import org.selbowgreaser.request.UserRequest;

import java.util.List;

public interface ForecastingAlgorithm {

    List<List<Double>> forecast(UserRequest request, List<ExchangeRateData> exchangeRateData);
}

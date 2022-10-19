package org.selbowgreaser.forecasting;

import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.util.List;

public interface ForecastingAlgorithm {
    RequestResult forecast(Currency currency, List<LocalDate> dates);
}

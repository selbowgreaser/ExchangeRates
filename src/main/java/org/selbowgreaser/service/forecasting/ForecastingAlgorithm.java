package org.selbowgreaser.service.forecasting;

import org.selbowgreaser.model.RequestResult;
import org.selbowgreaser.model.parameter.Currency;

import java.time.LocalDate;
import java.util.List;

public interface ForecastingAlgorithm {
    RequestResult forecast(Currency currency, List<LocalDate> dates);
}

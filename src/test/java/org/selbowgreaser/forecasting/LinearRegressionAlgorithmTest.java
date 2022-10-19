package org.selbowgreaser.forecasting;

import org.junit.jupiter.api.Test;
import org.selbowgreaser.request.parameters.Currency;

import static org.junit.jupiter.api.Assertions.*;

class LinearRegressionAlgorithmTest {

    @Test
    void test() {
        ForecastingAlgorithm forecastingAlgorithm = new LinearRegressionAlgorithm(Currency.USD, "TOMORROW");

        forecastingAlgorithm.forecast();
    }
}
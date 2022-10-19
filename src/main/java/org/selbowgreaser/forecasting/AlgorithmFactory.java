package org.selbowgreaser.forecasting;

import org.selbowgreaser.data.ExchangeRateDao;
import org.selbowgreaser.request.parameters.Algorithm;

import java.util.Random;

public class AlgorithmFactory {
    private final ExchangeRateDao exchangeRateDao;
    private final Random random;

    public AlgorithmFactory(ExchangeRateDao exchangeRateDao, Random random) {
        this.exchangeRateDao = exchangeRateDao;
        this.random = random;
    }

    public ForecastingAlgorithm createAlgorithm(Algorithm algorithm) {
        if (algorithm.equals(Algorithm.MYSTICAL)) {
            return new MysticalAlgorithm(exchangeRateDao, random);
        } else if (algorithm.equals(Algorithm.LAST_YEAR)) {
            return new LastYearAlgorithm(exchangeRateDao);
        } else {
            return new LinearRegressionAlgorithm(exchangeRateDao);
        }
    }
}

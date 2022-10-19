package org.selbowgreaser.forecasting;

import org.selbowgreaser.request.parameters.Algorithm;

public class AlgorithmFactory {
    public ForecastingAlgorithm createAlgorithm(Algorithm algorithm) {
        if (algorithm.equals(Algorithm.MYSTICAL)) {
            return new MysticalAlgorithm();
        } else if (algorithm.equals(Algorithm.LAST_YEAR)) {
            return new LastYearAlgorithm();
        } else if (algorithm.equals(Algorithm.LINEAR_REGRESSION)) {
            //todo: заменить на создание LinearRegressionAlgorithm
            return null;
        }
        //todo: заменить на создание AverageAlgorithm
        return null;
    }
}

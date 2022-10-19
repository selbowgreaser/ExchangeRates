package org.selbowgreaser.forecasting;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.ExchangeRateDao;
import org.selbowgreaser.data.PredictedExchangeRate;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LinearRegressionAlgorithm implements ForecastingAlgorithm {

    public static final int SCALE = 2;
    public final int DAYS_IN_MONTH = 30;
    private BigDecimal intercept, slope;
    private BigDecimal r2;
    private BigDecimal svar0, svar1;

    public LinearRegressionAlgorithm(ExchangeRateDao exchangeRateDao) {
        this.exchangeRateDao = exchangeRateDao;
    }

    private final ExchangeRateDao exchangeRateDao;

    @Override
    public RequestResult forecast(Currency currency, List<LocalDate> dates) {
        List<ExchangeRate> exchangeRates = exchangeRateDao.getDataForLinearRegressionAlgorithm(currency);

        List<BigDecimal> x = genX(dates.size());
        List<BigDecimal> y = extractExchangeRate(exchangeRates);

        trainModel(x, y);

        List<PredictedExchangeRate> predictedExchangeRates = new ArrayList<>();

        for (int i = DAYS_IN_MONTH; i < DAYS_IN_MONTH + dates.size(); i++) {
            predictedExchangeRates.add(new PredictedExchangeRate(dates.get(i), this.slope.multiply(BigDecimal.valueOf(31)).add(this.intercept)));
        }

        return new RequestResult(currency, Algorithm.LINEAR_REGRESSION, predictedExchangeRates);
    }

    private void trainModel(List<BigDecimal> x, List<BigDecimal> y) {
        int n = x.size();

        BigDecimal sumX = BigDecimal.ZERO;
        BigDecimal sumY = BigDecimal.ZERO;
        BigDecimal sumX2 = BigDecimal.ZERO;

        for (int i = 0; i < n; i++) {
            sumX = sumX.add(x.get(i));
            sumX2 = sumX2.add(x.get(i).multiply(x.get(i)));
            sumY = sumY.add(y.get(i));
        }
        BigDecimal xBar = sumX.divide(BigDecimal.valueOf(n), SCALE, RoundingMode.HALF_UP);
        BigDecimal yBar = sumY.divide(BigDecimal.valueOf(n), SCALE, RoundingMode.HALF_UP);

        BigDecimal xxBar = BigDecimal.ZERO;
        BigDecimal yyBar = BigDecimal.ZERO;
        BigDecimal xyBar = BigDecimal.ZERO;
        for (int i = 0; i < n; i++) {
            xxBar = xxBar.add((x.get(i).add(xBar.negate())).multiply((x.get(i).add(xBar.negate()))));
            yyBar = yyBar.add((y.get(i).add(yBar.negate())).multiply((y.get(i).add(yBar.negate()))));
            xyBar = xxBar.add((x.get(i).add(xBar.negate())).multiply((y.get(i).add(yBar.negate()))));
        }
        this.slope = xyBar.divide(xxBar, SCALE, RoundingMode.HALF_UP);
        this.intercept = yBar.add(slope.multiply(xBar).negate());

        BigDecimal rss = BigDecimal.ZERO;
        BigDecimal ssr = BigDecimal.ZERO;
        for (int i = 0; i < n; i++) {
            BigDecimal fit = slope.multiply(x.get(i)).add(intercept);
            rss = rss.add((fit.add(y.get(i).negate())).multiply(fit.add(y.get(i).negate())));
            ssr = ssr.add((fit.add(yBar.negate())).multiply(fit.add(yBar.negate())));
        }

        BigDecimal degreesOfFreedom = BigDecimal.valueOf(n - 2);
        this.r2 = ssr.divide(yyBar, SCALE, RoundingMode.HALF_UP);
        BigDecimal svar = rss.divide(degreesOfFreedom, SCALE, RoundingMode.HALF_UP);
        this.svar1 = svar.divide(xxBar, SCALE, RoundingMode.HALF_UP);
        this.svar0 = svar.divide(BigDecimal.valueOf(n), SCALE, RoundingMode.HALF_UP).add(xBar.multiply(xBar).multiply(svar1));
    }

    private List<BigDecimal> genX(int size) {
        List<BigDecimal> x = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            x.add(BigDecimal.valueOf(i));
        }
        return x;
    }

    private List<BigDecimal> extractExchangeRate(List<ExchangeRate> exchangeRates) {
        return exchangeRates.stream()
                .map(exchangeRate -> exchangeRate.getExchangeRate().divide(exchangeRate.getDenomination(), SCALE, RoundingMode.HALF_UP))
                .toList();
    }
}

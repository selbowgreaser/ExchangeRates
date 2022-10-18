package org.selbowgreaser.data.extractor;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.reader.CashedExchangeRatesFileReader;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;

public class DataExtractorForLinearRegressionAlgorithm implements DataExtractor {

    private final Currency currency;
    private final ExchangeRatesFileReader reader;

    public DataExtractorForLinearRegressionAlgorithm(Currency currency, ExchangeRatesFileReader reader) {
        this.currency = currency;
        this.reader = reader;
    }

    public DataExtractorForLinearRegressionAlgorithm(Currency currency) {
        this.currency = currency;
        this.reader = CashedExchangeRatesFileReader.getInstance();
    }

    @Override
    public List<ExchangeRate> extractData() {
        List<ExchangeRate> exchangeRates = reader.readExchangeRatesFile(currency);
        return exchangeRates.subList(0, 30);
    }
}

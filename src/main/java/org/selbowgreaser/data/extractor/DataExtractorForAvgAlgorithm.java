package org.selbowgreaser.data.extractor;

import lombok.extern.slf4j.Slf4j;
import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.reader.CashedExchangeRatesFileReader;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;

@Slf4j
public class DataExtractorForAvgAlgorithm implements DataExtractor {

    private final Currency currency;
    private final ExchangeRatesFileReader reader;

    public DataExtractorForAvgAlgorithm(Currency currency, ExchangeRatesFileReader reader) {
        this.currency = currency;
        this.reader = reader;
    }

    public DataExtractorForAvgAlgorithm(Currency currency) {
        this.currency = currency;
        this.reader = CashedExchangeRatesFileReader.getInstance();
    }

    @Override
    public List<ExchangeRate> extractData() {
        List<ExchangeRate> exchangeRates = CashedExchangeRatesFileReader.getInstance().readExchangeRatesFile(currency);
        return exchangeRates.subList(0, 7);
    }
}

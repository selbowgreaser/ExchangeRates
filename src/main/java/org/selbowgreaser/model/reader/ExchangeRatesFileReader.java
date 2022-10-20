package org.selbowgreaser.model.reader;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;
import java.util.Map;

public interface ExchangeRatesFileReader {
    Map<Currency, List<ExchangeRate>> readExchangeRatesFiles();
}



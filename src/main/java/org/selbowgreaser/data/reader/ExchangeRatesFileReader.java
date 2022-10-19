package org.selbowgreaser.data.reader;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;
import java.util.Map;

public interface ExchangeRatesFileReader {
    Map<Currency, List<ExchangeRate>> readExchangeRatesFiles();
}



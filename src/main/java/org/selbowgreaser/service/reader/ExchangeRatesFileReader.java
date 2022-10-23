package org.selbowgreaser.service.reader;

import org.selbowgreaser.model.data.ExchangeRate;
import org.selbowgreaser.model.parameter.Currency;

import java.util.List;
import java.util.Map;

public interface ExchangeRatesFileReader {
    Map<Currency, List<ExchangeRate>> readExchangeRatesFiles();
}



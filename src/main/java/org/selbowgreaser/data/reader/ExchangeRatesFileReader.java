package org.selbowgreaser.data.reader;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;

public interface ExchangeRatesFileReader {
    List<ExchangeRate> readExchangeRatesFiles();
}



package org.selbowgreaser.data.reader;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.request.parameters.Currency;

import java.util.HashMap;
import java.util.List;

public final class CashedExchangeRatesFileReader implements ExchangeRatesFileReader {
    private static volatile ExchangeRatesFileReader instance;
    private final HashMap<Currency, List<ExchangeRate>> cache = new HashMap<>();

    private CashedExchangeRatesFileReader() {
    }

    ;

    @Override
    public List<ExchangeRate> readExchangeRatesFile(Currency currency) {
        if (!cache.containsKey(currency)) {
            cache.put(currency, new ExchangeRatesFileReaderImpl().readExchangeRatesFile(currency));
        }
        return cache.get(currency);
    }

    public static ExchangeRatesFileReader getInstance() {
        ExchangeRatesFileReader result = instance;
        if (result != null) {
            return result;
        }
        synchronized (ExchangeRatesFileReader.class) {
            if (instance == null) {
                instance = new CashedExchangeRatesFileReader();
            }
            return instance;
        }
    }
}
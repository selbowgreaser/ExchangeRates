package org.selbowgreaser.data.extractor;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.reader.CashedExchangeRatesFileReader;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.selbowgreaser.handler.DateHandler.convertStringToDate;

public class DataExtractorForLastYearAlgorithm implements DataExtractor {
    private final int FIRST_LINE = 0;
    private final Currency currency;
    private final List<LocalDate> dates;
    private final ExchangeRatesFileReader reader;

    public DataExtractorForLastYearAlgorithm(Currency currency, List<LocalDate> dates, ExchangeRatesFileReader reader) {
        this.currency = currency;
        this.dates = dates;
        this.reader = reader;
    }

    public DataExtractorForLastYearAlgorithm(Currency currency, List<LocalDate> dates) {
        this.currency = currency;
        this.dates = dates;
        this.reader = CashedExchangeRatesFileReader.getInstance();
    }

    @Override
    public List<ExchangeRate> extractData() {
        List<ExchangeRate> readExchangeRatesData = reader.readExchangeRatesFile(currency);
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            exchangeRates.add(filterByDayAndMonth(readExchangeRatesData, date).get(FIRST_LINE));
        }
        return exchangeRates;
    }

    private List<ExchangeRate> filterByDayAndMonth(List<ExchangeRate> exchangeRates, LocalDate date) {
        return exchangeRates.stream()
                .filter(exchangeRate ->
                        convertStringToDate(exchangeRate.getDate()).getDayOfMonth() == date.getDayOfMonth())
                .filter(exchangeRate ->
                        convertStringToDate(exchangeRate.getDate()).getMonth().equals(date.getMonth()))
                .toList();
    }
}

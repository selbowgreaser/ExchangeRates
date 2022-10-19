package org.selbowgreaser.data.extractor;

import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.reader.CashedExchangeRatesFileReader;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataExtractorForMystAlgorithm implements DataExtractor {
    private final String DATE_PATTERN = "dd.MM.yyyy";
    private final Currency currency;
    private final List<LocalDate> dates;
    private final ExchangeRatesFileReader reader;
    private final Random random = new Random();

    public DataExtractorForMystAlgorithm(Currency currency, List<LocalDate> dates, ExchangeRatesFileReader reader) {
        this.currency = currency;
        this.dates = dates;
        this.reader = reader;
    }

    public DataExtractorForMystAlgorithm(Currency currency, List<LocalDate> dates) {
        this.currency = currency;
        this.dates = dates;
        this.reader = CashedExchangeRatesFileReader.getInstance();
    }

    @Override
    public List<ExchangeRate> extractData() {
        List<ExchangeRate> readExchangeRatesData = reader.readExchangeRatesFile(currency);
        List<ExchangeRate> exchangeRates = new ArrayList<>();
        for (LocalDate date : dates) {
            List<ExchangeRate> exchangeRatesForDifferentYears = filterByDayAndMonth(readExchangeRatesData, date);
            int randomIndex = random.nextInt(0, exchangeRatesForDifferentYears.size());
            exchangeRates.add(exchangeRatesForDifferentYears.get(randomIndex));
        }
        return exchangeRates;
    }

    private LocalDate convertStringToDate(String dateInString) {
        return LocalDate.parse(dateInString, DateTimeFormatter.ofPattern(DATE_PATTERN));
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
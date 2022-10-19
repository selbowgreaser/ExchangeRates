package org.selbowgreaser.data;

import org.selbowgreaser.data.reader.ExchangeRatesFileReader;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class ExchangeRateDaoImpl implements ExchangeRateDao {
    private final List<ExchangeRate> data;

    public ExchangeRateDaoImpl(ExchangeRatesFileReader reader) {
        this.data = reader.readExchangeRatesFiles();
    }

    @Override
    public ExchangeRate getDataForDate(LocalDate date) {
        return data.stream()
                .filter(unit -> unit.getDate().isBefore(date))
                .max(Comparator.comparing(ExchangeRate::getDate))
                .orElseThrow(NoSuchElementException::new);
    }


}

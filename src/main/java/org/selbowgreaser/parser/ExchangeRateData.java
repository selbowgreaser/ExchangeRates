package org.selbowgreaser.parser;

import java.time.LocalDate;
import java.util.List;

public class ExchangeRateData {
    private final LocalDate lastDate;
    private final List<Double> data;

    public ExchangeRateData(LocalDate lastDate, List<Double> data) {
        this.lastDate = lastDate;
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }


}

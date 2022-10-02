package org.selbowgreaser.parser;

import java.time.LocalDate;
import java.util.List;

public class ExchangeRateData {
    private final LocalDate lastDate;
    private final List<Double> data;

    public ExchangeRateData(LocalDate lastDate, List<Double> data) { //todo используй lombok
        this.lastDate = lastDate;
        this.data = data;
    }

    public List<Double> getData() {
        return data;
    } //todo используй lombok

    public LocalDate getLastDate() {
        return lastDate;
    } //todo используй lombok


}

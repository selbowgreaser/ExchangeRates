package org.selbowgreaser.parser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.selbowgreaser.request.Currency;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class ExchangeRateData {
    private final LocalDate lastDate;
    private final List<Double> data;
    private final Currency currency;
}

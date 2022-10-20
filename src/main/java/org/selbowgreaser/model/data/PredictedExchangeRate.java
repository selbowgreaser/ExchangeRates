package org.selbowgreaser.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class PredictedExchangeRate {
    private final LocalDate date;
    private final BigDecimal exchangeRate;
}




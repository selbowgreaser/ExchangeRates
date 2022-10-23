package org.selbowgreaser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.selbowgreaser.model.data.PredictedExchangeRate;
import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;

import java.util.List;

@AllArgsConstructor
@Getter
public class RequestResult {
    private final Currency currency;
    private final Algorithm algorithm;
    private final List<PredictedExchangeRate> predictedExchangeRates;
}

package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.selbowgreaser.data.PredictedExchangeRate;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;

@AllArgsConstructor
@Getter
public class RequestResult {
    private final Currency currency;
    private final Algorithm algorithm;
    private final List<PredictedExchangeRate> predictedExchangeRates;
}

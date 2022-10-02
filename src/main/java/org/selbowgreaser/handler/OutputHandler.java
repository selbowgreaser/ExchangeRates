package org.selbowgreaser.handler;

import org.apache.commons.lang3.StringUtils;
import org.selbowgreaser.parser.ExchangeRateData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputHandler {
    private final String RU = "ru";
    private final String DATE_PATTERN = "dd.MM.yyyy";

    public String processing(ExchangeRateData exchangeRateData, List<Double> predictions) {
        int predictionSize = predictions.size();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < predictionSize; i++) {
            LocalDate day = exchangeRateData.getLastDate().plusDays(i);

            result.append(StringUtils.capitalize(day.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, new Locale(RU))));
            result.append(" ");
            result.append(day.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
            result.append(" – ");
            result.append(String.format("%.2f", predictions.get(i)));
            result.append("\n");
        }
        return result.toString();
    }
}

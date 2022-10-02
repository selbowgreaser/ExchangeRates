package org.selbowgreaser.handler;

import org.apache.commons.lang3.StringUtils;
import org.selbowgreaser.parser.ExchangeRateData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputHandler {

    public String processing(ExchangeRateData exchangeRateData, List<Double> predictions) {
        int predictionSize = predictions.size();
        StringBuilder resultString = new StringBuilder();

        for (int i = 0; i < predictionSize; i++) {
            LocalDate day = exchangeRateData.getLastDate().plusDays(i);

            resultString.append(StringUtils.capitalize(day.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, new Locale("ru"))));
            resultString.append(" ");
            resultString.append(day.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            resultString.append(" – ");
            resultString.append(String.format("%.2f", predictions.get(i)));
            resultString.append("\n");
        }
        return resultString.toString();
    }
}

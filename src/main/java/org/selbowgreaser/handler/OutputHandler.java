package org.selbowgreaser.handler;

import org.apache.commons.lang3.StringUtils;
import org.selbowgreaser.parser.ExchangeRateData;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

public class OutputHandler {
    private final String RU = "ru";
    private final String DATE_PATTERN = "dd.MM.yyyy";

    public String processing(List<ExchangeRateData> exchangeRateDataList, List<List<Double>> predictions) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < exchangeRateDataList.size(); i++) {
            result.append(MessageFormat.format("Prediction for {0}\n", exchangeRateDataList.get(i).getCurrency()));
            int predictionSize = predictions.get(i).size();
            for (int j = 0; j < predictionSize; j++) {
                LocalDate day = exchangeRateDataList.get(i).getLastDate().plusDays(j);

                result.append(StringUtils.capitalize(day.getDayOfWeek()
                        .getDisplayName(TextStyle.SHORT, new Locale(RU))));
                result.append(" ");
                result.append(day.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
                result.append(" – ");
                result.append(String.format("%.2f", predictions.get(i).get(j)));
                result.append("\n");
            }
        }
        return result.toString();
    }
}

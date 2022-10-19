package org.selbowgreaser.handler;

import org.apache.commons.lang3.StringUtils;
import org.selbowgreaser.data.PredictedExchangeRate;
import org.selbowgreaser.request.RequestResult;

import java.text.MessageFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class CommandLineOutputHandler implements OutputHandler {
    public static final String PATTERN_OUTPUT_STRING = "{0} {1} – {2}\n";
    private final String RU = "ru";
    private final String DATE_PATTERN = "dd.MM.yyyy";

    @Override
    public String processing(RequestResult requestResult) {
        StringBuilder result = new StringBuilder();
        for (PredictedExchangeRate predictedExchangeRate : requestResult.getPredictedExchangeRates()) {
            String resultForOneDay = MessageFormat.format(PATTERN_OUTPUT_STRING,
                    StringUtils.capitalize(predictedExchangeRate.getDate().getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale(RU))),
                    predictedExchangeRate.getDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)),
                    String.format("%.2f", predictedExchangeRate.getExchangeRate()));
            result.append(resultForOneDay);
        }
        return result.toString();
    }
}

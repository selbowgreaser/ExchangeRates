package org.selbowgreaser.request;

import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.OutputMode;

import java.time.LocalDate;
import java.util.List;

public interface UserRequest {
    List<Currency> getCurrencies();

    List<LocalDate> getDates();

    List<Algorithm> getAlgorithms();

    OutputMode getOutputMode();
}

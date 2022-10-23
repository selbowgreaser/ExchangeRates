package org.selbowgreaser.model;

import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;
import org.selbowgreaser.model.parameter.OutputMode;

import java.time.LocalDate;
import java.util.List;

public interface UserRequest {
    List<Currency> getCurrencies();

    List<LocalDate> getDates();

    List<Algorithm> getAlgorithms();

    OutputMode getOutputMode();
}

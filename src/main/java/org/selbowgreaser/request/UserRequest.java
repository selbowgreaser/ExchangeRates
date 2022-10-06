package org.selbowgreaser.request;

import java.util.List;

public interface UserRequest {
    List<Currency> getCurrencies();

    String getPeriodOrDate();

    List<Algorithm> getAlgorithms();

    OutputMode getOutputMode();
}

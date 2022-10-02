package org.selbowgreaser.command;

import org.selbowgreaser.forecasting.Algorithm;

public interface UserRequest {
    String getCurrency();

    String getPeriod();

    Algorithm getAlgorithm();
}

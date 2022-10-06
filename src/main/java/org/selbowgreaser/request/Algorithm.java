package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Algorithm {
    AVERAGE("AVG"),
    MYSTICAL("MYST"),
    LAST_YEAR("LY"),
    LOGISTIC_REGRESSION("LR");

    private final String algorithm;
}

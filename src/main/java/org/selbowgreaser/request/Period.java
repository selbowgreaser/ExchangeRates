package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Period {
    TOMORROW("TOMORROW"),
    WEEK("WEEK"),
    MONTH("MONTH");

    private final String period;
}

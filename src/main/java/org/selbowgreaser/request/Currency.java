package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD("USD"),
    TRY("TRY"),
    EUR("EUR");

    private final String currency;
}

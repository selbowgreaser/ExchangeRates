package org.selbowgreaser.request.parameters;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Currency {
    USD("USD"),
    TRY("TRY"),
    EUR("EUR"),
    BGN("BGN"),
    AMD("AMD");

    private final String currency;
}

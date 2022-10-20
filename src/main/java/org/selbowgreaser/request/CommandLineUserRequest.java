package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.selbowgreaser.request.parameters.Algorithm;
import org.selbowgreaser.request.parameters.Currency;
import org.selbowgreaser.request.parameters.OutputMode;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class CommandLineUserRequest implements UserRequest {

    private final List<Currency> currencies;
    private final List<LocalDate> dates;
    private final List<Algorithm> algorithms;
    private final OutputMode outputMode;
}

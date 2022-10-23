package org.selbowgreaser.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.selbowgreaser.model.UserRequest;
import org.selbowgreaser.model.parameter.Algorithm;
import org.selbowgreaser.model.parameter.Currency;
import org.selbowgreaser.model.parameter.OutputMode;

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

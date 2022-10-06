package org.selbowgreaser.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OutputMode {
    LIST("LIST"),
    GRAPH("GRAPH");

    private final String outputMode;
}

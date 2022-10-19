package org.selbowgreaser.request.parameters;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum Algorithm {
    AVERAGE("AVG"),
    MYSTICAL("MYST"),
    LAST_YEAR("LY"),
    LINEAR_REGRESSION("LR");

    private final String label;

    private static final Map<String, Algorithm> BY_LABEL = new HashMap<>();

    static {
        for (Algorithm a: values()) {
            BY_LABEL.put(a.label, a);
        }
    }


    // ... fields, constructor, methods

    public static Algorithm valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}

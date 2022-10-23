package org.selbowgreaser.model.parameter;

import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public enum Algorithm {
    LAST_YEAR("LY"),
    MYSTICAL("MYST"),
    LINEAR_REGRESSION("LR");

    private final String label;

    private static final Map<String, Algorithm> BY_LABEL = new HashMap<>();

    static {
        for (Algorithm a : values()) {
            BY_LABEL.put(a.label, a);
        }
    }

    public static Algorithm valueOfLabel(String label) {
        return BY_LABEL.get(label);
    }
}

package org.selbowgreaser.model;

import lombok.Data;
import org.selbowgreaser.model.parameter.OutputMode;

import java.util.List;

@Data
public class BotResponse {

    private String message;
    private OutputMode outputMode;
    private List<RequestResult> requestResults;

    public BotResponse(OutputMode outputMode, List<RequestResult> requestResults) {
        this.message = "";
        this.outputMode = outputMode;
        this.requestResults = requestResults;
    }

    public BotResponse(String message) {
        this.message = message;
    }
}

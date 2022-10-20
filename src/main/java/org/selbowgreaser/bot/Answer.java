package org.selbowgreaser.bot;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.selbowgreaser.request.RequestResult;
import org.selbowgreaser.request.parameters.OutputMode;

import java.util.List;

@Data
public class Answer {

    private String message;
    private OutputMode outputMode;
    private List<RequestResult> requestResults;

    public Answer(OutputMode outputMode, List<RequestResult> requestResults) {
        this.message = "";
        this.outputMode = outputMode;
        this.requestResults = requestResults;
    }

    public Answer(String message) {
        this.message = message;
    }
}

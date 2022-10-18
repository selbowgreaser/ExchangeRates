package org.selbowgreaser.data.extractor;

import org.selbowgreaser.data.ExchangeRate;

import java.util.List;

public interface DataExtractor {
    List<ExchangeRate> extractData();
}

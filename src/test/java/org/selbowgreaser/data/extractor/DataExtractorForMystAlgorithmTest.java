package org.selbowgreaser.data.extractor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.selbowgreaser.data.ExchangeRate;
import org.selbowgreaser.data.reader.ExchangeRatesFileReader;
import org.selbowgreaser.request.parameters.Currency;

import java.util.List;

@Slf4j
class DataExtractorForMystAlgorithmTest {

    @Test
    public void extractingDataForUSDForWeek() {

        Currency currency = Currency.USD;
        log.debug("Start reading data...");

        ExchangeRatesFileReader reader = Mockito.mock(ExchangeRatesFileReader.class);
        Mockito.when(reader.readExchangeRatesFile(currency)).thenReturn(genData());

        List<ExchangeRate> actual = new DataExtractorForMystAlgorithm(Currency.USD, "TOMORROW", reader).extractData();
        List<ExchangeRate> expected = List.of( new ExchangeRate(1, "09.08.2016", 65, "Доллар США"), new ExchangeRate(1, "09.08.2015", 64, "Доллар США"));

        Assertions.assertEquals(expected, actual);
    }


    private List<ExchangeRate> genData() {
        return List.of(new ExchangeRate(1, "08.08.2020", 70, "Доллар США"),
                new ExchangeRate(1, "09.08.2016", 65, "Доллар США"),
                new ExchangeRate(1, "09.08.2015", 64, "Доллар США"),
                new ExchangeRate(1, "15.08.2014", 82, "Доллар США"),
                new ExchangeRate(1, "14.08.2013", 90, "Доллар США"));
    }
}
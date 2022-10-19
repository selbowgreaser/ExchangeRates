package org.selbowgreaser.data;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvNumber;
import com.opencsv.bean.CsvNumbers;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class ExchangeRate {
    @CsvBindByPosition(position = 0)
    @CsvNumbers({
            @CsvNumber("#"),
            @CsvNumber("##"),
            @CsvNumber("###"),
            @CsvNumber("# ###"),
            @CsvNumber("## ###"),
            @CsvNumber("### ###"),
            @CsvNumber("# ### ###"),
            @CsvNumber("## ### ###"),
            @CsvNumber("### ### ###")
    })
    private BigDecimal denomination;

    public ExchangeRate(BigDecimal denomination, LocalDate date, BigDecimal exchangeRate, String cdx) {
        this.denomination = denomination;
        this.date = date;
        this.exchangeRate = exchangeRate;
        this.cdx = cdx;
    }

    @CsvBindByPosition(position = 1)
    private LocalDate date;

    @CsvBindByPosition(position = 2)
    private BigDecimal exchangeRate;

    @CsvBindByPosition(position = 3)
    private String cdx;

    @Override
    public String toString() {
        return String.format("ExchangeRate{denomination=%f, date=\"%s\", exchange rate=%f, cdx=\"%s\"}", denomination, date, exchangeRate, cdx);
    }
}

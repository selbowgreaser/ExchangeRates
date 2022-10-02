package org.selbowgreaser.parser;

import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvNumber;
import com.opencsv.bean.CsvNumbers;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private Integer nominal;

    @CsvBindByPosition(position = 1)
    private String date;

    @CsvBindByPosition(position = 2)
    private Double curs;

    @CsvBindByPosition(position = 3)
    private String cdx;

    @Override
    public String toString() {
        return String.format("ExchangeRate{nominal=%d, date=\"%s\", curs=%f, cdx=\"%s\"}", nominal, date, curs, cdx);
    }
}

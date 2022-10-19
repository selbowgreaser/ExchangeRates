package org.selbowgreaser.data;

import java.time.LocalDate;

public interface ExchangeRateDao {
    ExchangeRate getDataForDate(LocalDate date);
}

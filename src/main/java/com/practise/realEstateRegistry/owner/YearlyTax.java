package com.practise.realEstateRegistry.owner;

import java.math.BigDecimal;
import java.time.LocalDate;

public class YearlyTax {
    private BigDecimal yearlyTax;
    private LocalDate taxesCalculationDate;

    public YearlyTax(LocalDate taxesCalculationDate, BigDecimal yearlyTax) {
        this.taxesCalculationDate = taxesCalculationDate;
        this.yearlyTax = yearlyTax;
    }

    public BigDecimal getYearlyTax() {
        return yearlyTax;
    }

    public void setYearlyTax(BigDecimal yearlyTax) {
        this.yearlyTax = yearlyTax;
    }
}

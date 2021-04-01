package com.practise.realEstateRegistry.taxrate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.practise.realEstateRegistry.property.PropertyType;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TaxRateDto {
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate validFrom = LocalDate.now();
    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate validTo;
    private BigDecimal rate;
    private PropertyType propertyType;

    public TaxRateDto() {
    }

    public TaxRateDto(LocalDate validFrom, LocalDate validTo, BigDecimal rate, PropertyType propertyType) {
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.rate = rate;
        this.propertyType = propertyType;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
}

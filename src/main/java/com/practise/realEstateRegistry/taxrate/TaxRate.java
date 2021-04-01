package com.practise.realEstateRegistry.taxrate;

import com.practise.realEstateRegistry.property.PropertyType;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tax_rates")
public class TaxRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valid_from")
    private LocalDate validFrom = LocalDate.now();

    @Column(name = "valid_to")
    private LocalDate validTo;

    @Column(name = "rate")
    private BigDecimal rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    public TaxRate() {
    }

    public TaxRate(Long id, LocalDate validFrom, LocalDate validTo, BigDecimal rate, PropertyType propertyType) {
        this.id = id;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.rate = rate;
        this.propertyType = propertyType;
    }

    public TaxRate(TaxRateDto taxRateDto) {
        this.validFrom = taxRateDto.getValidFrom();
        this.validTo = taxRateDto.getValidTo();
        this.rate = taxRateDto.getRate();
        this.propertyType = taxRateDto.getPropertyType();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public TaxRateDto toDto() {
        return new TaxRateDto(
                validFrom,
                validTo,
                rate,
                propertyType
        );
    }
}

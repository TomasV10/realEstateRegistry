package com.practise.realEstateRegistry.property;


import com.practise.realEstateRegistry.owner.Owner;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "properties")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "area_size")
    private BigDecimal areaSize;

    @Column(name = "market_value")
    private BigDecimal marketValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_type")
    private PropertyType propertyType;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    public Property() {
    }

    public Property(Owner owner, String address, BigDecimal areaSize, BigDecimal marketValue, PropertyType propertyType) {
        this.owner = owner;
        this.address = address;
        this.areaSize = areaSize;
        this.marketValue = marketValue;
        this.propertyType = propertyType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getAreaSize() {
        return areaSize;
    }

    public void setAreaSize(BigDecimal areaSize) {
        this.areaSize = areaSize;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }

    public PropertyDto toDto() {
        return new PropertyDto(
                owner.getId(),
                address,
                areaSize,
                marketValue,
                propertyType
        );
    }
}

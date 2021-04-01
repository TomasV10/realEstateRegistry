package com.practise.realEstateRegistry.property;


import java.math.BigDecimal;

public class PropertyDto {
    private Long ownerId;
    private String address;
    private BigDecimal areaSize;
    private BigDecimal marketValue;
    private PropertyType propertyType;

    public PropertyDto() {
    }

    public PropertyDto(Long ownerId, String address, BigDecimal areaSize, BigDecimal marketValue,
                       PropertyType propertyType) {
        this.ownerId = ownerId;
        this.address = address;
        this.areaSize = areaSize;
        this.marketValue = marketValue;
        this.propertyType = propertyType;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
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

}

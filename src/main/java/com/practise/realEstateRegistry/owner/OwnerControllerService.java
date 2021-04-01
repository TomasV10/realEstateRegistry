package com.practise.realEstateRegistry.owner;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.util.stream.Collectors.toList;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.practise.realEstateRegistry.property.PropertyDto;
import com.practise.realEstateRegistry.property.Property;
import com.practise.realEstateRegistry.taxrate.TaxRate;
import com.practise.realEstateRegistry.property.PropertyType;
import com.practise.realEstateRegistry.taxrate.TaxRatesRepository;
import org.springframework.stereotype.Service;

@Service
public class OwnerControllerService {
    private final OwnerRepository ownerRepository;
    private final TaxRatesRepository taxRatesRepository;

    public OwnerControllerService(OwnerRepository ownerRepository, TaxRatesRepository taxRatesRepository) {
        this.ownerRepository = ownerRepository;
        this.taxRatesRepository = taxRatesRepository;
    }

    public void createAndSaveOwner(OwnerDto ownerDto) {
        Owner owner = new Owner(ownerDto.getFullName());
        ownerRepository.save(owner);
    }

    public Owner getOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Owner not found"));
    }

    public List<OwnerDto> getAllOwners(){
        return ownerRepository.findAll()
                .stream()
                .map(Owner::toDto)
                .collect(toList());
    }

    public List<PropertyDto> propertyRecordsByOwnerId(Long id) {
        return ownerRepository.findById(id)
                .map(Owner::getProperties)
                .stream()
                .flatMap(List::stream)
                .map(Property::toDto)
                .collect(toList());
    }

    public BigDecimal calculateTaxes(Long ownerId, LocalDate when) {
        return getOwnerById(ownerId)
                .getProperties()
                .stream()
                .map(property -> calculateTaxesForSingleProperty(when, property))
                .reduce(ZERO, BigDecimal::add)
                .setScale(2, HALF_UP);
    }

    private BigDecimal calculateTaxesForSingleProperty(LocalDate when, Property property) {
        return getPropertyTaxRate(property.getPropertyType(), when).multiply(property.getMarketValue());
    }

    private BigDecimal getPropertyTaxRate(PropertyType propertyType, LocalDate when) {
        return taxRatesRepository.findByPropertyTypeForDate(propertyType, when)
                .map(TaxRate::getRate)
                .orElseThrow(() -> new RuntimeException("Tax rates not found for given property type and date"));
    }
}

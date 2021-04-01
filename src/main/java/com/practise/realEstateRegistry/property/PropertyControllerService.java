package com.practise.realEstateRegistry.property;

import java.util.List;
import java.util.stream.Collectors;

import com.practise.realEstateRegistry.owner.Owner;
import com.practise.realEstateRegistry.owner.OwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class PropertyControllerService {
    private final PropertyRepository propertyRepository;
    private final OwnerRepository ownerRepository;

    public PropertyControllerService(PropertyRepository propertyRepository, OwnerRepository ownerRepository) {
        this.propertyRepository = propertyRepository;
        this.ownerRepository = ownerRepository;
    }

    public PropertyDto getPropertyById(Long id) {
        return propertyRepository.findById(id)
                .map(Property::toDto)
                .orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public List<PropertyDto> getAllProperties() {
        return propertiesListToDto();
    }

    public void createProperty(PropertyDto propertyDto) {
        Owner owner = getOwner(propertyDto.getOwnerId());
        Property property = toProperty(propertyDto, owner);
        propertyRepository.save(property);
    }

    private Property toProperty(PropertyDto propertyDto, Owner owner) {
        return new Property(owner,
                propertyDto.getAddress(),
                propertyDto.getAreaSize(),
                propertyDto.getMarketValue(),
                propertyDto.getPropertyType());
    }

    private Owner getOwner(Long ownerId) {
        return ownerRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Owner with given ID not found"));
    }

    public void updateProperty(Long id, PropertyDto propertyDto) {
        Property property = propertyRepository.getOne(id);
        property.setAddress(propertyDto.getAddress());
        property.setAreaSize(propertyDto.getAreaSize());
        property.setMarketValue(propertyDto.getMarketValue());
        property.setPropertyType(propertyDto.getPropertyType());
        propertyRepository.save(property);
    }

    public void deleteProperty(Long id) {
        propertyRepository.deleteById(id);
    }


    private List<PropertyDto> propertiesListToDto() {
        return propertyRepository.findAll().stream()
                .map(Property::toDto)
                .collect(Collectors.toList());
    }
}

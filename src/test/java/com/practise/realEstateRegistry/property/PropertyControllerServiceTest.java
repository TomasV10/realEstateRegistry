package com.practise.realEstateRegistry.property;

import com.practise.realEstateRegistry.owner.Owner;
import com.practise.realEstateRegistry.owner.OwnerRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.practise.realEstateRegistry.property.PropertyType.APARTMENT;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Optional.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class PropertyControllerServiceTest {

    private PropertyRepository propertyRepository = mock(PropertyRepository.class);

    private OwnerRepository ownerRepository = mock(OwnerRepository.class);

    private PropertyControllerService service = new PropertyControllerService(propertyRepository, ownerRepository);

    @Test
    void shouldReturnPropertyById() {
        Owner owner1 = new Owner(1L, "Mark Marco", emptyList());
        Property property = new Property(owner1, "Kaunas, Savanoriu pr. 266",
                new BigDecimal("150.43"), new BigDecimal("130000.00"), APARTMENT);
        when(propertyRepository.findById(property.getId())).thenReturn(of(property));

        PropertyDto propertyDto = service.getPropertyById(property.getId());
        Property property2 = toProperty(propertyDto, owner1);

        assertThat(property2.getOwner().getFullName()).isEqualTo("Mark Marco");
        assertThat(property2.getPropertyType()).isEqualTo(APARTMENT);
    }

    @Test
    void shouldReturnAllProperties() {
        Owner owner1 = new Owner(5L, "Mark Marco", emptyList());
        Owner owner2 = new Owner(8L, "Paul Marco", emptyList());

        Property property1 = new Property(owner1, "Kaunas, Savanoriu  pr.288",
                new BigDecimal("188.88"), new BigDecimal("245000.00"), PropertyType.HOUSE);
        Property property2 = new Property(owner2, "Vilnius, Saltoniskiu  g.3",
                new BigDecimal("558.12"), new BigDecimal("1435550.00"), PropertyType.APARTMENT);

        when(propertyRepository.findAll()).thenReturn(asList(property1, property2));

        List<PropertyDto> allProperties = service.getAllProperties();

        assertThat(allProperties.size()).isEqualTo(2);
        assertThat(allProperties.get(0).getOwnerId()).isEqualTo(5L);
        assertThat(allProperties.get(1).getOwnerId()).isEqualTo(8L);
    }

    @Test
    void shouldCreateProperty() {
        Owner owner1 = new Owner(1L, "Mark Marco", emptyList());
        PropertyDto propertyDto = new PropertyDto(1L, "Kaunas, Savanoriu  pr.288",
                new BigDecimal("188.88"), new BigDecimal("245000.00"), PropertyType.HOUSE);
        Property property = toProperty(propertyDto, owner1);
        when(ownerRepository.findById(owner1.getId())).thenReturn(of(owner1));
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        service.createProperty(propertyDto);

        verify(propertyRepository, times(1)).save(any(Property.class));
    }

    @Test
    void shouldUpdateAndSaveProperty() {
        Owner owner1 = new Owner(1L, "Mark Marco", emptyList());
        PropertyDto propertyDto = new PropertyDto(1L, "Kaunas, Savanoriu  pr.288",
                new BigDecimal("188.88"), new BigDecimal("245000.00"), PropertyType.HOUSE);
        Property property = toProperty(propertyDto, owner1);
        when(propertyRepository.getOne(property.getId())).thenReturn(property);
        when(propertyRepository.save(any(Property.class))).thenReturn(property);

        service.updateProperty(property.getId(), propertyDto);

        verify(propertyRepository, times(1)).getOne(property.getId());
        verify(propertyRepository, times(1)).save(any(Property.class));

    }

    @Test
    void shouldDeleteProperty() {
        Owner owner1 = new Owner(1L, "Mark Marco", emptyList());
        Property property = new Property(owner1, "Kaunas, Savanoriu pr. 266",
                new BigDecimal("150.43"), new BigDecimal("130000.00"), APARTMENT);
        service.deleteProperty(property.getId());

        verify(propertyRepository, times(1)).deleteById(property.getId());
    }

    private Property toProperty(PropertyDto propertyDto, Owner owner) {
        return new Property(owner,
                propertyDto.getAddress(),
                propertyDto.getAreaSize(),
                propertyDto.getMarketValue(),
                propertyDto.getPropertyType());
    }

}

package com.practise.realEstateRegistry.owner;

import com.practise.realEstateRegistry.property.Property;
import com.practise.realEstateRegistry.property.PropertyDto;
import com.practise.realEstateRegistry.property.PropertyType;
import com.practise.realEstateRegistry.taxrate.TaxRate;
import com.practise.realEstateRegistry.taxrate.TaxRatesRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.practise.realEstateRegistry.property.PropertyType.APARTMENT;
import static com.practise.realEstateRegistry.property.PropertyType.MANSION;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OwnerControllerServiceTest {

    private OwnerRepository ownerRepository = mock(OwnerRepository.class);

    private TaxRatesRepository taxRatesRepository = mock(TaxRatesRepository.class);

    private OwnerControllerService service = new OwnerControllerService(ownerRepository, taxRatesRepository);


    @Test
    void shouldReturnAllOwners() {
        Owner owner1 = new Owner(5L, "Mark Marco", emptyList());
        Owner owner2 = new Owner(8L, "Paul Marco", emptyList());
        when(ownerRepository.findAll()).thenReturn(asList(owner1, owner2));

        List<OwnerDto> allOwners = service.getAllOwners();

        assertThat(allOwners.size()).isEqualTo(2);
        assertThat(allOwners.get(0).getFullName()).isEqualTo("Mark Marco");
        assertThat(allOwners.get(1).getFullName()).isEqualTo("Paul Marco");
    }

    @Test
    void shouldReturnOwnerById() {
        Owner owner1 = new Owner(5L, "Mark Marco", emptyList());
        when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));
        Owner expectedOwner = service.getOwnerById(5L);
        assertThat(expectedOwner.getFullName()).isEqualTo("Mark Marco");
        assertThat(expectedOwner.getProperties().size()).isEqualTo(0);
    }

    @Test
    void shouldSaveOwner() {
        OwnerDto ownerDto1 = new OwnerDto(5L, "Mark Marco");
        Owner owner1 = new Owner(ownerDto1.getFullName());
        when(ownerRepository.save(any(Owner.class))).thenReturn(owner1);
        service.createAndSaveOwner(ownerDto1);

        verify(ownerRepository, times(1)).save(any(Owner.class));
    }

    @Test
    void shouldReturnPropertyRecordsByOwnerId() {
        Owner owner1 = new Owner(5L, "Mark Marco", emptyList());
        when(ownerRepository.findById(owner1.getId())).thenReturn(Optional.of(owner1));
        createProperties(owner1);

        List<PropertyDto> propertyRecords = service.propertyRecordsByOwnerId(owner1.getId());

        assertThat(propertyRecords.size()).isEqualTo(2);
        assertThat(propertyRecords.get(0).getMarketValue()).isEqualTo(new BigDecimal("130000.00"));
        assertThat(propertyRecords.get(1).getPropertyType()).isEqualTo(MANSION);
    }


    @Test
    void shouldCalculateTaxes() {
        Owner owner1 = new Owner(5L, "Mark Marco", emptyList());
        createProperties(owner1);
        LocalDate currentDate = of(2021, 1, 12);

        TaxRate taxRate1 = createTaxRate(1L, currentDate,
                new BigDecimal("0.04"), APARTMENT);

        TaxRate taxRate2 = createTaxRate(2L, currentDate,
                new BigDecimal("0.15"), MANSION);


        when(taxRatesRepository.findByPropertyTypeForDate(APARTMENT, currentDate))
                .thenReturn(Optional.of(taxRate1));
        when(taxRatesRepository.findByPropertyTypeForDate(MANSION, currentDate))
                .thenReturn(Optional.of(taxRate2));
        when(ownerRepository.findById(owner1.getId()))
                .thenReturn(Optional.of(owner1));

        BigDecimal taxes = service.calculateTaxes(owner1.getId(), currentDate);
        assertThat(taxes)
                .isEqualByComparingTo(BigDecimal.valueOf(33100));

    }

    private TaxRate createTaxRate(Long id, LocalDate validFrom, BigDecimal rate, PropertyType propertyType) {
        return new TaxRate(id, validFrom, null, rate, propertyType);
    }

    private void createProperties(Owner owner1) {
        List<Property> properties = Arrays.asList(
                new Property(owner1, "Kaunas, Savanoriu pr. 266",
                        new BigDecimal("150.43"), new BigDecimal("130000.00"), APARTMENT),
                (new Property(owner1, "Vilnius, Saltoniskiu g. 100",
                        new BigDecimal("111.33"), new BigDecimal("186000.00"), MANSION)));
        owner1.setProperties(properties);
    }
}
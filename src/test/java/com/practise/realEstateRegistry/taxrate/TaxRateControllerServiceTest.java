package com.practise.realEstateRegistry.taxrate;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.practise.realEstateRegistry.property.PropertyType.APARTMENT;
import static com.practise.realEstateRegistry.property.PropertyType.HOUSE;
import static java.math.BigDecimal.*;
import static java.time.LocalDate.of;
import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class TaxRateControllerServiceTest {
    private TaxRatesRepository taxRatesRepository = mock(TaxRatesRepository.class);

    private TaxRateControllerService service = new TaxRateControllerService(taxRatesRepository);

    @Test
    void shouldAddTaxRate() {
        TaxRateDto taxRateDto = new TaxRateDto(of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        TaxRateDto taxRateDto1 = new TaxRateDto(of(2021, 1, 12),
                of(2021, 2, 25), new BigDecimal("0.02"), HOUSE);

        TaxRate taxRate = new TaxRate(taxRateDto);
        TaxRate taxRate1 = new TaxRate(taxRateDto1);

        when(taxRatesRepository.save(any(TaxRate.class))).thenReturn(taxRate);
        when(taxRatesRepository.save(any(TaxRate.class))).thenReturn(taxRate1);

        service.addTaxRate(taxRateDto);
        service.addTaxRate(taxRateDto1);

        verify(taxRatesRepository, times(2)).save(any(TaxRate.class));
    }

    @Test
    void shouldReturnAllRates() {
        TaxRate taxRate1 = new TaxRate(1L, of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        TaxRate taxRate2 = new TaxRate(2L, of(2021, 1, 12),
                of(2021, 2, 25), new BigDecimal("0.02"), HOUSE);

        when(taxRatesRepository.findAll()).thenReturn(asList(taxRate1, taxRate2));

        List<TaxRateDto> rates = service.getAllRates();

        assertThat(rates.size()).isEqualTo(2);
        assertThat(rates.get(0).getPropertyType()).isEqualTo(APARTMENT);
        assertThat(rates.get(1).getPropertyType()).isEqualTo(HOUSE);
    }

    @Test
    void shouldReturnRateById(){
        TaxRate taxRate = new TaxRate(1L, of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        when(taxRatesRepository.findById(taxRate.getId())).thenReturn(Optional.of(taxRate));

        TaxRateDto taxRateDto = service.getRateById(taxRate.getId());

        assertThat(taxRateDto.getPropertyType()).isEqualTo(APARTMENT);
        assertThat(taxRateDto.getRate()).isEqualByComparingTo(valueOf(0.04));
    }

    @Test
    void shouldUpdateAndSaveRate(){
        TaxRateDto taxRateDto = new TaxRateDto(of(2021, 1, 12),
                of(2021, 2, 25), new BigDecimal("0.02"), HOUSE);
        TaxRate taxRate = new TaxRate(taxRateDto);

        when(taxRatesRepository.getOne(taxRate.getId())).thenReturn(taxRate);

        service.updateRate(taxRate.getId(), taxRateDto);

        verify(taxRatesRepository, times(1)).save(any(TaxRate.class));
    }
}

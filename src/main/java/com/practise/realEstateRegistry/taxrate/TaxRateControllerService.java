package com.practise.realEstateRegistry.taxrate;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaxRateControllerService {
    private final TaxRatesRepository taxRatesRepository;

    public TaxRateControllerService(TaxRatesRepository taxRatesRepository) {
        this.taxRatesRepository = taxRatesRepository;
    }

    public void addTaxRate(TaxRateDto rateDto) {
        TaxRate taxRate = toTaxRate(rateDto);
        taxRatesRepository.save(taxRate);
    }

    public List<TaxRateDto> getAllRates() {
        return taxRatesListToDto();
    }

    public TaxRateDto getRateById(Long id) {
        return taxRatesRepository.findById(id)
                .map(TaxRate::toDto)
                .orElseThrow(() -> new RuntimeException("Tax rate not found"));
    }

    public void updateRate(Long id, TaxRateDto taxRateDto) {
        TaxRate taxRate = taxRatesRepository.getOne(id);
        taxRate.setValidFrom(taxRateDto.getValidFrom());
        taxRate.setValidTo(taxRateDto.getValidTo());
        taxRate.setRate(taxRateDto.getRate());
        taxRate.setPropertyType(taxRateDto.getPropertyType());
        taxRatesRepository.save(taxRate);
    }

    private TaxRate toTaxRate(TaxRateDto rateDto) {
        return new TaxRate(rateDto);
    }

    private List<TaxRateDto> taxRatesListToDto() {
        return taxRatesRepository.findAll().stream()
                .map(TaxRate::toDto)
                .collect(Collectors.toList());
    }
}

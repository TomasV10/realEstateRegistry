package com.practise.realEstateRegistry.taxrate;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/taxRates")
public class TaxRateController {

    public final TaxRateControllerService taxRateControllerService;

    public TaxRateController(TaxRateControllerService taxRateControllerService) {
        this.taxRateControllerService = taxRateControllerService;
    }

    @GetMapping
    public List<TaxRateDto> getAllRates() {
        return taxRateControllerService.getAllRates();
    }

    @GetMapping("/{id}")
    public TaxRateDto getRateById(@PathVariable Long id) {
        return taxRateControllerService.getRateById(id);
    }

    @PostMapping("/")
    public void addTaxRate(@RequestBody TaxRateDto taxRateDto) {
        taxRateControllerService.addTaxRate(taxRateDto);
    }

    @PutMapping("/{id}")
    public void updateRate(@PathVariable Long id, @RequestBody TaxRateDto taxRateDto) {
        taxRateControllerService.updateRate(id, taxRateDto);
    }
}

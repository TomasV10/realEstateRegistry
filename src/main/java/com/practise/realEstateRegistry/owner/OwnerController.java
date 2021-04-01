package com.practise.realEstateRegistry.owner;

import com.practise.realEstateRegistry.property.PropertyDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.format.annotation.DateTimeFormat.ISO.DATE;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerControllerService ownerControllerService;

    public OwnerController(OwnerControllerService ownerControllerService) {
        this.ownerControllerService = ownerControllerService;
    }

    @PostMapping("/")
    public void addNewOwner(@RequestBody OwnerDto ownerDto) {
        ownerControllerService.createAndSaveOwner(ownerDto);
    }

    @GetMapping("/{ownerId}/properties")
    public List<PropertyDto> getPropertiesRecordsByOwnerId(@PathVariable Long ownerId) {
        return ownerControllerService.propertyRecordsByOwnerId(ownerId);
    }

    @GetMapping("/")
    public List<OwnerDto> getAllOwners() {
        return ownerControllerService.getAllOwners();
    }

    @GetMapping("/{ownerId}/taxes")
    public YearlyTax calculateTaxes(@PathVariable Long ownerId,
                                    @RequestParam(name = "when", required = false)
                                    @DateTimeFormat(iso = DATE) LocalDate when) {
        LocalDate taxesCalculationDate = ofNullable(when).orElseGet(LocalDate::now);
        BigDecimal yearlyTax = ownerControllerService.calculateTaxes(ownerId, taxesCalculationDate);
        return new YearlyTax(taxesCalculationDate, yearlyTax);
    }
}

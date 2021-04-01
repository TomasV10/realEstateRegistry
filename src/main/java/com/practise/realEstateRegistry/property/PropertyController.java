package com.practise.realEstateRegistry.property;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyControllerService propertyControllerService;

    public PropertyController(PropertyControllerService propertyControllerService) {
        this.propertyControllerService = propertyControllerService;
    }

    @GetMapping
    public List<PropertyDto> getAllProperties() {
        return propertyControllerService.getAllProperties();
    }

    @GetMapping("/{id}")
    public PropertyDto getPropertyById(@PathVariable Long id) {
        return propertyControllerService.getPropertyById(id);
    }

    @PostMapping("/")
    public void createProperty(@RequestBody PropertyDto propertyDto) {
        propertyControllerService.createProperty(propertyDto);
    }

    @PutMapping("/{id}")
    public void updateProperty(@PathVariable Long id, @RequestBody PropertyDto propertyDto) {
        propertyControllerService.updateProperty(id, propertyDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProperty(@PathVariable Long id) {
        propertyControllerService.deleteProperty(id);
    }
}

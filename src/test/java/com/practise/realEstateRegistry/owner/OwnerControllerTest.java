package com.practise.realEstateRegistry.owner;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.practise.realEstateRegistry.property.Property;
import com.practise.realEstateRegistry.property.PropertyDto;
import com.practise.realEstateRegistry.taxrate.TaxRate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.practise.realEstateRegistry.property.PropertyType.APARTMENT;
import static com.practise.realEstateRegistry.property.PropertyType.MANSION;
import static java.time.LocalDate.of;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OwnerController.class)
public class OwnerControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private OwnerControllerService service;


    @Test
    void getAllOwnersTest() throws Exception {
        OwnerDto ownerDto1 = new OwnerDto(1L, "Mark Marco");
        OwnerDto ownerDto2 = new OwnerDto(2L, "Steve McKenzie");

        List<OwnerDto> allOwners = Arrays.asList(ownerDto1, ownerDto2);
        when(service.getAllOwners()).thenReturn(allOwners);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/owners/")
                .accept(APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].fullName", is(ownerDto1.getFullName())));
    }

    @Test
    void shouldAddNewOwnerTest() throws Exception {
        OwnerDto ownerDto = new OwnerDto(1L, "Mark Marco");
        ObjectMapper mapper = new ObjectMapper();
        service.createAndSaveOwner(ownerDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/owners/")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(ownerDto));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).createAndSaveOwner(ownerDto);
    }

    @Test
    void shouldCalculateTaxes() throws Exception {
        Owner owner = new Owner(1L, "Mark Marco", emptyList());
        createProperties(owner);
        LocalDate currentDate = of(2021, 2, 12);
        List<TaxRate>rates = createTaxRatesList();
        BigDecimal yearlyTax = new BigDecimal("8920");
        when(service.calculateTaxes(owner.getId(), currentDate)).thenReturn(yearlyTax);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/owners/{ownerId}/taxes", owner.getId())
                .param("when", "2021-02-12");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.yearlyTax").value(yearlyTax));


    }

    @Test
    void shouldReturnPropertiesRecordsByOwnerIdTest() throws Exception {
        Owner owner = new Owner(1L, "Mark Marco", emptyList());
        createProperties(owner);
        List<PropertyDto> properties = convertPropertiesToDtos(owner.getProperties());

        when(service.propertyRecordsByOwnerId(owner.getId())).thenReturn(properties);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/owners/{ownerId}/properties", owner.getId());

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].propertyType", is(properties.get(0).getPropertyType().name())));
    }

    private void createProperties(Owner owner) {
        List<Property> properties = Arrays.asList(
                new Property(owner, "Kaunas, Savanoriu pr. 266",
                        new BigDecimal("150.43"), new BigDecimal("130000.00"), APARTMENT),
                (new Property(owner, "Vilnius, Saltoniskiu g. 100",
                        new BigDecimal("111.33"), new BigDecimal("186000.00"), MANSION)));
        owner.setProperties(properties);
    }

    private List<PropertyDto> convertPropertiesToDtos(List<Property> properties) {
        return properties.stream()
                .map(Property::toDto)
                .collect(Collectors.toList());
    }

    private List<TaxRate> createTaxRatesList() {
        return Arrays.asList(
                new TaxRate(1L, of(2021, 2, 12), null,
                        new BigDecimal("0.04"), APARTMENT),
                new TaxRate(2L, of(2021, 1, 12),
                        of(2021, 2, 25), new BigDecimal("0.02"), MANSION));
    }


}

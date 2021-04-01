package com.practise.realEstateRegistry.property;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.Arrays;
import java.util.List;

import static com.practise.realEstateRegistry.property.PropertyType.HOUSE;
import static com.practise.realEstateRegistry.property.PropertyType.MOBILE_HOME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    PropertyControllerService service;

    @Test
    void shouldRetrieveAllPropertiesTest() throws Exception {
        List<PropertyDto> properties = createProperties();
        when(service.getAllProperties()).thenReturn(properties);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/properties/")
                .accept(APPLICATION_JSON);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].propertyType", is(properties.get(0).getPropertyType().name())));
    }

    @Test
    void retrievePropertyByIdTest() throws Exception {
        PropertyDto propertyDto = new PropertyDto(1L, "Kaunas, Laso g. 2", new BigDecimal("87.43"),
                new BigDecimal("67500"), HOUSE);
        when(service.getPropertyById(1L)).thenReturn(propertyDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/properties/{id}", 1L);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertyType", is(propertyDto.getPropertyType().name())))
                .andExpect(jsonPath("$.address", is(propertyDto.getAddress())));
    }

    @Test
    void shouldCreatePropertyTest() throws Exception {
        PropertyDto propertyDto = new PropertyDto(1L, "Kaunas, Laso g. 2", new BigDecimal("87.43"),
                new BigDecimal("67500"), HOUSE);
        ObjectMapper mapper = new ObjectMapper();
        service.createProperty(propertyDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/properties/")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyDto));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).createProperty(propertyDto);
    }

    @Test
    void shouldUpdatePropertyTest() throws Exception {
        PropertyDto propertyDto = new PropertyDto(1L, "Kaunas, Laso g. 2", new BigDecimal("87.43"),
                new BigDecimal("67500"), HOUSE);
        ObjectMapper mapper = new ObjectMapper();
        service.updateProperty(1L, propertyDto);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/properties/{id}", 1L)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(propertyDto));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).updateProperty(1L, propertyDto);
    }

    @Test
    void shouldDeletePropertyTest() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/api/properties/{id}", 1L);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).deleteProperty(1L);
    }

    private List<PropertyDto> createProperties() {
        PropertyDto propertyDto1 = new PropertyDto(1L, "Kaunas, Laso g. 2", new BigDecimal("87.43"),
                new BigDecimal("67500"), HOUSE);
        PropertyDto propertyDto2 = new PropertyDto(2L, "Kaunas, Kanto g. 6", new BigDecimal("47.45"),
                new BigDecimal("17500"), MOBILE_HOME);
        return Arrays.asList(propertyDto1, propertyDto2);
    }
}

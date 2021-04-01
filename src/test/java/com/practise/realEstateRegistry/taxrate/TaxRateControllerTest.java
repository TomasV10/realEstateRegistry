package com.practise.realEstateRegistry.taxrate;

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

import static com.practise.realEstateRegistry.property.PropertyType.APARTMENT;
import static com.practise.realEstateRegistry.property.PropertyType.MANSION;
import static java.time.LocalDate.of;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TaxRateController.class)
public class TaxRateControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaxRateControllerService service;

    @Test
    void shouldRetrieveAllRatesTest() throws Exception {
        List<TaxRateDto> rates = createTaxRates();
        when(service.getAllRates()).thenReturn(rates);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/taxRates");

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].propertyType", is(rates.get(0).getPropertyType().name())));
    }

    @Test
    void getRateByIdTest() throws Exception {
        TaxRateDto rate = new TaxRateDto(of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        when(service.getRateById(1L)).thenReturn(rate);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/api/taxRates/{id}", 1L);

        mvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.propertyType", is(rate.getPropertyType().name())));
    }

    @Test
    void addTaxRateTest() throws Exception {
        TaxRateDto rate = new TaxRateDto(of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        ObjectMapper mapper = new ObjectMapper();
        service.addTaxRate(rate);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/taxRates/")
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(rate));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).addTaxRate(rate);
    }

    @Test
    void updateRateTest() throws Exception {
        TaxRateDto rate = new TaxRateDto(of(2021, 2, 12), null,
                new BigDecimal("0.04"), APARTMENT);
        ObjectMapper mapper = new ObjectMapper();
        service.updateRate(1L, rate);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/api/taxRates/{id}", 1L)
                .contentType(APPLICATION_JSON)
                .content(mapper.writeValueAsString(rate));

        mvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(service, times(1)).updateRate(1L, rate);
    }

    private List<TaxRateDto> createTaxRates() {
        return Arrays.asList(
                new TaxRateDto(of(2021, 2, 12), null,
                        new BigDecimal("0.04"), APARTMENT),
                new TaxRateDto(of(2021, 1, 12),
                        of(2021, 2, 25), new BigDecimal("0.02"), MANSION));
    }
}

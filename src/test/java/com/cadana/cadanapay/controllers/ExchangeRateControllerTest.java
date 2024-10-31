package com.cadana.cadanapay.controllers;

import com.cadana.cadanapay.api.RateRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ExchangeRateControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    public void testExchangeRate_successful() throws Exception {
        RateRequest request = RateRequest.builder()
                .currencyPair("EUR-USD")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rates/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testExchangeRate_badRequest() throws Exception {
        RateRequest request = RateRequest.builder()
                .currencyPair("EURUSD")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/rates/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

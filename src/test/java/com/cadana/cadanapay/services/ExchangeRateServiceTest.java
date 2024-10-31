package com.cadana.cadanapay.services;

import com.cadana.cadanapay.api.ExchangeRateApiResponse;
import com.cadana.cadanapay.api.OpenExchangeRatesApiResponse;
import com.cadana.cadanapay.services.http.ExchangeRateApi;
import com.cadana.cadanapay.services.http.ExchangeRateApiClient;
import com.cadana.cadanapay.services.http.OpenExchangeRatesApi;
import com.cadana.cadanapay.services.http.OpenExchangeRatesApiClient;
import com.cadana.cadanapay.util.AWSSecretManagerConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ExchangeRateServiceTest {
    @MockBean
    private ExchangeRateApiClient exchangeRateApiClient;
    @MockBean
    private OpenExchangeRatesApiClient openExchangeRatesApiClient;
    @MockBean
    private ExchangeRateApi exchangeRateApi;
    @MockBean
    private OpenExchangeRatesApi openExchangeRatesApi;
    @MockBean
    private AWSSecretManagerConfig awsSecretManagerConfig;
    @MockBean
    private Call<ExchangeRateApiResponse> exchangeRateApiCall;
    @MockBean
    private Call<OpenExchangeRatesApiResponse> openExchangeRatesApiResponseCall;

    @Autowired
    private ExchangeRateService exchangeRateService;
    @Test
    public void testGetFirstAvailableRate_ReturnsFirstCompleted() throws IOException {
        when(exchangeRateApiClient.getExchangeRate("USD", "EUR")).thenReturn(0.92);
        when(openExchangeRatesApiClient.getExchangeRate("USD", "EUR")).thenReturn(0.93);
        when(awsSecretManagerConfig.getSecret(anyString())).thenReturn("secret");

        when(exchangeRateApi.getExchangeRate(anyString(), anyString(), anyString())).thenReturn(exchangeRateApiCall);
        when(exchangeRateApiCall.execute()).thenReturn(Response.success(ExchangeRateApiResponse.builder()
                .conversionRate(0.92)
                .build()));
        when(openExchangeRatesApi.getLatestRates(anyString())).thenReturn(openExchangeRatesApiResponseCall);
        when(openExchangeRatesApiResponseCall.execute()).thenReturn(Response.success(OpenExchangeRatesApiResponse.builder()
                .base("USD")
                .rates(new HashMap<>(){{
                    put("USD", 0.92);
                    put("EUR", 0.93);
                }})
                .build()));

        Double rate = exchangeRateService.getFirstAvailableRate("USD", "EUR");

        assertNotNull(rate);
        assertTrue(Arrays.asList(0.92, 0.93).contains(rate));
    }
}

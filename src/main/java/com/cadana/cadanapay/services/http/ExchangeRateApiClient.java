package com.cadana.cadanapay.services.http;

import com.cadana.cadanapay.api.ExchangeRateApiResponse;
import com.cadana.cadanapay.util.AWSSecretManagerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateApiClient {
    private final ExchangeRateApi exchangeRateApi;
    private final AWSSecretManagerConfig awsSecretManagerConfig;

    public Double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            Call<ExchangeRateApiResponse> call = exchangeRateApi.getExchangeRate(awsSecretManagerConfig.getSecret("API_KEY_1"), baseCurrency, targetCurrency);
            Response<ExchangeRateApiResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                Double rate = response.body().getConversionRate();
                log.info("Successfully retrieved the exchange rate for {}-{}: {}", baseCurrency, targetCurrency, rate);

                return rate;
            }

            log.error("Exchange rate API call failed to return a valid response");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return 0.0;
    }
}

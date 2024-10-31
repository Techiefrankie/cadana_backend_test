package com.cadana.cadanapay.services.http;

import com.cadana.cadanapay.api.OpenExchangeRatesApiResponse;
import com.cadana.cadanapay.util.AWSSecretManagerConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenExchangeRatesApiClient {
    private final OpenExchangeRatesApi openExchangeRatesApi;
    private final AWSSecretManagerConfig awsSecretManagerConfig;

    public Double getExchangeRate(String baseCurrency, String targetCurrency) {
        try {
            Call<OpenExchangeRatesApiResponse> call = openExchangeRatesApi.getLatestRates(awsSecretManagerConfig.getSecret("API_KEY_2"));
            Response<OpenExchangeRatesApiResponse> response = call.execute();

            if (response.isSuccessful() && response.body() != null) {
                Map<String, Double> rates = response.body().getRates();

                Double baseToUsd = baseCurrency.equals("USD") ? 1.0 : rates.get(baseCurrency);
                Double targetToUsd = targetCurrency.equals("USD") ? 1.0 : rates.get(targetCurrency);
                Double rate = targetToUsd / baseToUsd;

                log.info("Successfully retrieved the open exchange rate for {}-{}: {}", baseCurrency, targetCurrency, rate);

                return rate;
            }

            log.error("Open exchange rate API call failed to return a valid response");
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }

        return 0.0;
    }
}

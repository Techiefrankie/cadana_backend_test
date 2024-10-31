package com.cadana.cadanapay.services;

import com.cadana.cadanapay.services.http.ExchangeRateApiClient;
import com.cadana.cadanapay.services.http.OpenExchangeRatesApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExchangeRateService {
    private final ExchangeRateApiClient exchangeRateApiClient;
    private final OpenExchangeRatesApiClient openExchangeRatesApiClient;

    public Double getFirstAvailableRate(String baseCurrency, String quoteCurrency) {
        log.info("Getting first available rate for the {}-{} pair", baseCurrency, quoteCurrency);

        CompletableFuture<Double> firstRate = CompletableFuture.supplyAsync(() -> exchangeRateApiClient.getExchangeRate(baseCurrency, quoteCurrency));
        CompletableFuture<Double> secondRate = CompletableFuture.supplyAsync(() -> openExchangeRatesApiClient.getExchangeRate(baseCurrency, quoteCurrency));

        return CompletableFuture.anyOf(firstRate, secondRate)
                .thenApply(rate -> (Double) rate)
                .exceptionally(ex -> {
                    throw new RuntimeException(String.format("Both services failed to fetch the exchange rate for %s-%s", baseCurrency, quoteCurrency), ex);
                }).join();
    }
}

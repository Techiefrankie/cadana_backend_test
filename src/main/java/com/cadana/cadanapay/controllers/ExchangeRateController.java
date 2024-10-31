package com.cadana.cadanapay.controllers;

import com.cadana.cadanapay.api.RateRequest;
import com.cadana.cadanapay.services.DataManipulationService;
import com.cadana.cadanapay.services.ExchangeRateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/rates")
@RequiredArgsConstructor
public class ExchangeRateController {
    private final ExchangeRateService exchangeRateService;

    @PostMapping("/conversion")
    public ResponseEntity<Map<String, Double>> getExchangeRate(@RequestBody RateRequest request) {
        DataManipulationService dataManipulationService = new DataManipulationService();

        String currencyPair = request.getCurrencyPair();
        String[] pair = currencyPair.split("-");

        if (pair.length != 2) {
            log.error("Invalid currency pair: {}", currencyPair);
            return ResponseEntity.badRequest().body(Collections.emptyMap());
        }

        String baseCurrency = pair[0];
        String quoteCurrency = pair[1];

        log.info("Fetching exchange rate for the currency pair: {}", currencyPair);
        Double rate = exchangeRateService.getFirstAvailableRate(baseCurrency, quoteCurrency);

        // Data manipulation task: filter all the USD currencies using the DataManipulationService
        log.info("People with USD currency: {}", dataManipulationService.filterBySalaryCurrency("USD"));

        return ResponseEntity.ok(Collections.singletonMap(currencyPair, rate));
    }
}

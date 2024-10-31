package com.cadana.cadanapay.services.http;

import com.cadana.cadanapay.api.ExchangeRateApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ExchangeRateApi {
    @GET("/v6/{apiKey}/pair/{base}/{target}")
    Call<ExchangeRateApiResponse> getExchangeRate(
            @Path("apiKey") String apiKey,
            @Path("base") String baseCurrency,
            @Path("target") String targetCurrency
    );
}

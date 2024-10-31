package com.cadana.cadanapay.services.http;

import com.cadana.cadanapay.api.OpenExchangeRatesApiResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenExchangeRatesApi {
    @GET("/api/latest.json")
    Call<OpenExchangeRatesApiResponse> getLatestRates(@Query("app_id") String apiKey);
}

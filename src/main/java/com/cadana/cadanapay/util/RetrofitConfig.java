package com.cadana.cadanapay.util;

import com.cadana.cadanapay.services.http.ExchangeRateApi;
import com.cadana.cadanapay.services.http.OpenExchangeRatesApi;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Configuration
public class RetrofitConfig {
    @Bean
    public ExchangeRateApi exchangeRateApi() {
        return getRetrofitBuilder(ExchangeRateApi.class, "https://v6.exchangerate-api.com");
    }

    @Bean
    public OpenExchangeRatesApi openExchangeRatesApi() {
        return getRetrofitBuilder(OpenExchangeRatesApi.class, "https://openexchangerates.org");
    }

    private <T> T getRetrofitBuilder(Class<T> clazz, String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(new OkHttpClient())
                .addConverterFactory(JacksonConverterFactory.create())
                .build()
                .create(clazz);
    }
}

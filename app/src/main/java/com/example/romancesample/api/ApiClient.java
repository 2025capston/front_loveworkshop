package com.example.romancesample.api;

import retrofit2.Retrofit;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8080/";
    private static Retrofit retrofit;
    private static ApiService apiService;  // 추가

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    // ApiService 반환 메서드 추가
    public static ApiService getApiService() {
        return getClient().create(ApiService.class);
    }
}
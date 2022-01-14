package com.dorofeev.testemployees.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static ApiFactory apiFactory;
    private static final Object LOCK = new Object();

    private static final String BASE_URL = "http://gitlab.65apps.com/65gb/static/raw/master/";

    private static Retrofit retrofit;

    private ApiFactory() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    };

    public static ApiFactory getInstance() {
        synchronized (LOCK) {
            if (apiFactory == null) {
                apiFactory = new ApiFactory();
            }
        }
        return apiFactory;
    }

    public ApiService getApiService() {
        return retrofit.create(ApiService.class);
    }
}

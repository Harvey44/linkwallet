package com.mobile.linkwallet.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

// private static final String BASE_URL = "http://linkwallet.flashmediatech.com/backend/access/app/source/";
 private static final String BASE_URL = "http://chainlink-wallet.com/backend/access/app/source/";
   // private static final String BASE_URL = "http://on-custodian.com/backend/access/app/source/";
//private static final String BASE_URL = "http://chainlink.highbreedtech.com/backend/access/app/source/";

    private static ApiClient mInstance;
    private Retrofit retrofit;

    OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30,    TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS).build();

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static synchronized  ApiClient getInstance(){
        if (mInstance == null){
            mInstance = new ApiClient();
        }
        return mInstance;
    }
    public Api getApi(){
        return retrofit.create(Api.class);
    }
}

package com.ecocustomerapp.data.remote;


import com.ecocustomerapp.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GoogleConnection {

    private static GoogleConnection connect;
    private GoogleServices clientService;

    public static synchronized GoogleConnection getInstance() {

        if (connect == null) {
            connect = new GoogleConnection();
        }
        return connect;
    }


    GoogleServices createService(HttpLoggingInterceptor.Level level) {

        if (clientService == null) {

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            // set your desired log level
            logging.level(level);

            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
            // add your other interceptors …

            ConnectionPool pool = new ConnectionPool(5, 5, TimeUnit.MINUTES);

            // add logging as last interceptor
            httpClient.addInterceptor(logging)
                    .connectionPool(pool)
                    .retryOnConnectionFailure(true)
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .writeTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(2, TimeUnit.MINUTES)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()


                    .baseUrl(BuildConfig.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .build();

            clientService = retrofit.create(GoogleServices.class);
        }
        return clientService;
    }
}

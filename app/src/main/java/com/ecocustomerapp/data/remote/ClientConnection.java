package com.ecocustomerapp.data.remote;

import com.ecocustomerapp.BuildConfig;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.AnnotationStrategy;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.strategy.Strategy;

import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//
// * Created by SAURABH on 26/08/16.
//

public class ClientConnection {

    private static ClientConnection connect;
    private ClientService clientService;

    public static synchronized ClientConnection getInstance() {

        if (connect == null) {
            connect = new ClientConnection();
        }
        return connect;
    }


    ClientService createService(HttpLoggingInterceptor.Level level) {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.level(HttpLoggingInterceptor.Level.BODY);


        Strategy strategy = new AnnotationStrategy();

        Serializer serializer = new Persister(strategy);

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

        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()


                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        clientService = retrofit.create(ClientService.class);

        return clientService;
    }
}

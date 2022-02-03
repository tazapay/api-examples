package com.core;

import lombok.RequiredArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class ServiceGenerator<S> {

    private final ClientConfig clientConfig;
    private static int i = 0;

    protected S createService(final Class<S> serviceClass) {
        return retrofit().create(serviceClass);
    }

    private OkHttpClient okHttpClient(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(clientConfig.getConnectTimeoutInMilliSecs(), TimeUnit.MILLISECONDS)
                .readTimeout(clientConfig.getReadTimeOutInMilliSecs(), TimeUnit.MILLISECONDS)
                .writeTimeout(clientConfig.getWriteTimeOutInMilliSecs(), TimeUnit.MILLISECONDS);
        interceptorList(4000 + ++i).forEach(builder::addInterceptor);
        return builder.build();
    }

    private Retrofit retrofit(){
        return new Retrofit.Builder()
                .baseUrl(clientConfig.getBaseUrl())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient())
                .validateEagerly(true)
                .build();
    }

    protected List<Interceptor> interceptorList(long time){
        return new ArrayList<>();
    }
}

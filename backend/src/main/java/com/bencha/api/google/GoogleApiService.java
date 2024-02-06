package com.bencha.api.google;

import com.bencha.api.google.beans.CaptchaVerifyResponse;
import com.bencha.services.configuration.ConfigurationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Singleton
@Slf4j
public class GoogleApiService {
    private final GoogleApi googleApi;
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String APPLICATION_FORM_ENCODED = "application/x-www-form-urlencoded";

    @Inject
    public GoogleApiService(ObjectMapper objectMapper, ConfigurationService configurationService) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        this.googleApi = new Retrofit.Builder().baseUrl(configurationService.getGoogleReCaptchaVerificationBaseUrl()).client(
            new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).addNetworkInterceptor(
                new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)).addInterceptor(chain -> {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url =
                    originalHttpUrl
                        .newBuilder()
                        .build();

                Request.Builder requestBuilder =
                    original
                        .newBuilder()
                        .url(url)
                        .addHeader(CONTENT_TYPE_HEADER, APPLICATION_FORM_ENCODED);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }).build()).addConverterFactory(JacksonConverterFactory.create(objectMapper)).build().create(GoogleApi.class);
    }

    public CaptchaVerifyResponse verifyCaptchaResponse(String secret, String response) {
        CaptchaVerifyResponse googleVerifyResponse = executeRequest(googleApi.verifyCaptcha(secret, response));
        return googleVerifyResponse;
    }
    private static <T> T executeRequest(Call<T> apiCall) {
        Response<T> response;
        try {
            response = apiCall.execute();
            if (response.isSuccessful()) {
                T result = response.body();
                return result;
            } else {
                String exception = response.code() + " " + response.message() + ":\n" + response.errorBody().string();
                throw new RuntimeException(exception);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

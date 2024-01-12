package com.bencha.api.google;

import com.bencha.api.google.beans.CaptchaVerifyResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface GoogleApi {

    @POST("siteverify")
    @FormUrlEncoded
    Call<CaptchaVerifyResponse> verifyCaptcha(
        @Field("secret") String secret,
        @Field("response") String response
        );
}

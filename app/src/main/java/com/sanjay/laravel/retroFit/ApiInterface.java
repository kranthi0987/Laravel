package com.sanjay.laravel.retroFit;

import com.sanjay.laravel.models.*;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.*;

import java.util.HashMap;
import java.util.Map;

public interface ApiInterface {


    @POST("api/auth/login")
    Observable<LoginResponse> LOGIN_RESPONSE_OBSERVABLE(@Body LoginPassRequest login);

    @POST("api/auth/signup")
    Observable<RegisterSuccessResponse> REGISTER_SUCCESS_RESPONSE_OBSERVABLE(@Body RegisterRequest register);

    @GET("api/auth/user")
    Observable<UserSuccessResponse> USER_SUCCESS_RESPONSE_OBSERVABLE(@Header("Authorization") String headers);

    @GET("api/auth/logout")
    Observable<LogoutSuccessResponse> LOGOUT_SUCCESS_RESPONSE_OBSERVABLE(@Header("Authorization") String headers);


}

package com.sanjay.laravel.retroFit;

import com.sanjay.laravel.models.LoginPassRequest;
import com.sanjay.laravel.models.LoginResponse;
import com.sanjay.laravel.models.LogoutSuccessResponse;
import com.sanjay.laravel.models.UserSuccessResponse;
import com.sanjay.laravel.models.products.ProductsResponse;
import com.sanjay.laravel.models.registrationModel.RegisterRequest;
import com.sanjay.laravel.models.registrationModel.RegisterSuccessResponse;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

import java.util.List;

public interface ApiInterface {


    @POST("api/auth/login")
    Observable<LoginResponse> LOGIN_RESPONSE_OBSERVABLE(@Body LoginPassRequest login);

    @POST("api/auth/signup")
    Observable<RegisterSuccessResponse> REGISTER_SUCCESS_RESPONSE_OBSERVABLE(@Body RegisterRequest register);

    @GET("api/auth/user")
    Observable<UserSuccessResponse> USER_SUCCESS_RESPONSE_OBSERVABLE(@Header("Authorization") String headers);

    @GET("api/auth/logout")
    Observable<LogoutSuccessResponse> LOGOUT_SUCCESS_RESPONSE_OBSERVABLE(@Header("Authorization") String headers);

    @GET("api/products")
    Observable<List<ProductsResponse>> PRODUCTS_RESPONSE_OBSERVABLE();

}

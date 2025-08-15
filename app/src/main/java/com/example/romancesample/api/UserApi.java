package com.example.romancesample.api;

import com.example.romancesample.model.UserDTO;
import com.example.romancesample.model.UserRegisterRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;



public interface UserApi {
    @POST("/auth/register")
    Call<String> registerUser(@Body UserDTO userDTO);

    @POST("/auth/email")
    Call<Void> sendEmailVerification(@Query("email") String email);

    @POST("/auth/verify")
    Call<Boolean> verifyCode(@Query("email") String email, @Query("code") String code);


    Call<Void> sendAuthEmail(String email);
}

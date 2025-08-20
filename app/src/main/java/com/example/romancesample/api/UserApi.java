package com.example.romancesample.api;

import com.example.romancesample.model.IdealMatchListResponse;
import com.example.romancesample.model.UserDTO;
import com.example.romancesample.model.UserProfileDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.GET;
import retrofit2.http.Part;



public interface UserApi {
    @POST("/auth/register")
    Call<String> registerUser(@Body UserDTO userDTO);

    @POST("/auth/email")
    Call<Void> sendEmailVerification(@Query("email") String email);

    @POST("/auth/verify")
    Call<Boolean> verifyCode(@Query("email") String email, @Query("code") String code);

    //api 명세서 4번
    @Multipart
    @POST("/api/ideal/register")
    Call<ResponseBody> registerIdeal(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part photo,
            @Part("matchingGender") RequestBody matchingGender,
            @Part("olderThan") RequestBody olderThan,
            @Part("youngerThan") RequestBody youngerThan
    );

    @GET("/api/ideal/matches")
    Call<IdealMatchListResponse> getMatches(@Query("userId") String userId);

    //api 명세서 1번
    @GET("api/users/{userId}/profile")
    Call<UserProfileDTO> getProfile(@Path("userId") int userId);

    @PUT("api/users/{userId}/profile")
    Call<UserProfileDTO> updateProfile(
            @Path("userId") int userId,
            @Body UserProfileDTO profile
    );

    // api 명세서 매칭 이후 process

    Call<Void> sendAuthEmail(String email);
}

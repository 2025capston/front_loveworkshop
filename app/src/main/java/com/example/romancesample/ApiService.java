package com.example.romancesample;

import com.example.romancesample.model.LoginRequest;
import com.example.romancesample.model.LoginResponse;
import com.example.romancesample.model.ProfileRequest;
import com.example.romancesample.model.RegisterRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ApiService {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<String> addUser(@Body User user);

    @GET("api/hello")  // 서버의 실제 엔드포인트를 적절히 설정합니다.
    Call<MyResponse> testConnection();  // 반환 타입을 MyResponse로 수정
    // 1. 인증코드 이메일 전송
    @POST("/auth/send-code")
    @FormUrlEncoded
    Call<String> sendCode(@Field("email") String email);

    // 2. 인증코드 확인
    @POST("/auth/verify")
    @FormUrlEncoded
    Call<Boolean> verifyCode(@Field("email") String email, @Field("code") String code);

    // 3. 회원가입
    @POST("/auth/register")
    Call<String> register(@Body RegisterRequest request);

    // 4. 로그인
    @POST("/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    // 5. 프로필 등록
    @POST("/profile")
    Call<String> saveProfile(@Header("Authorization") String token, @Body ProfileRequest profile);

    // 6. 프로필 조회
    @GET("/profile")
    Call<ProfileRequest> getProfile(@Header("Authorization") String token);

    // 7. 프로필 수정
    @PUT("/profile")
    Call<String> updateProfile(@Header("Authorization") String token, @Body ProfileRequest profile);
}
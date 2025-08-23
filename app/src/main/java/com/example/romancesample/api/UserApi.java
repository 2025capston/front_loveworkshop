package com.example.romancesample.api;

import com.example.romancesample.model.IdealMatchListResponse;
import com.example.romancesample.model.PhotoResponse;
import com.example.romancesample.model.UserDTO;
import com.example.romancesample.model.UserProfileDTO;
import com.example.romancesample.model.ReportRequest;
import com.example.romancesample.model.ReportResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    // ✅ 회원가입
    @POST("/auth/register")
    Call<String> registerUser(@Body UserDTO userDTO);

    // ✅ 이메일 인증 요청
    @POST("/auth/email")
    Call<Void> sendEmailVerification(@Query("email") String email);

    // ✅ 이메일 인증 코드 확인
    @POST("/auth/verify")
    Call<Boolean> verifyCode(@Query("email") String email, @Query("code") String code);


    // ✅ 이상형 등록 (사진 포함)
    @Multipart
    @POST("/api/ideal/register")
    Call<ResponseBody> registerIdeal(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part photo,
            @Part("matchingGender") RequestBody matchingGender,
            @Part("olderThan") RequestBody olderThan,
            @Part("youngerThan") RequestBody youngerThan
    );

    // ✅ 이상형 매칭 조회
    @GET("/api/ideal/matches")
    Call<IdealMatchListResponse> getMatches(@Query("userId") String userId);


    // ✅ 프로필 조회
    @GET("api/users/{userId}/profile")
    Call<UserProfileDTO> getProfile(@Path("userId") int userId);

    // ✅ 프로필 수정
    @PUT("api/users/{userId}/profile")
    Call<UserProfileDTO> updateProfile(
            @Path("userId") int userId,
            @Body UserProfileDTO profile
    );


    // ✅ 사진 업로드 (isProfile true → 대표 사진)
    @Multipart
    @POST("/api/users/{userId}/photos")
    Call<PhotoResponse> uploadPhoto(
            @Path("userId") int userId,
            @Part MultipartBody.Part file,
            @Part("isProfile") RequestBody isProfile
    );

    // ✅ 사진 목록 조회
    @GET("/api/users/{userId}/photos")
    Call<List<PhotoResponse>> getUserPhotos(@Path("userId") int userId);

    // ✅ 대표 사진 변경
    @PATCH("/api/users/{userId}/photos/{photoId}/profile")
    Call<ResponseBody> setProfilePhoto(
            @Path("userId") int userId,
            @Path("photoId") int photoId
    );

    // ✅ 사진 삭제
    @DELETE("/api/users/{userId}/photos/{photoId}")
    Call<ResponseBody> deletePhoto(
            @Path("userId") int userId,
            @Path("photoId") int photoId
    );


    // ✅ 신고 등록
    @POST("/api/reports")
    Call<ReportResponse> createReport(@Body ReportRequest reportRequest);

    // ✅ 신고 목록 조회 (관리자용)
    @GET("/api/reports")
    Call<List<ReportResponse>> getReports();
}

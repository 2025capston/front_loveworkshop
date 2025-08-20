package com.example.romancesample.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FaceApiService {

    // 얼굴 업로드 (front, left, right)
    @Multipart
    @POST("/api/face/upload")
    Call<ResponseBody> uploadFace(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part front,
            @Part MultipartBody.Part left,
            @Part MultipartBody.Part right
    );

    // 얼굴 비교 (프로필 사진)
    @Multipart
    @POST("/api/face/compare")
    Call<ResponseBody> compareFace(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part profile,
            @Part("isProfile") RequestBody isProfile
    );
}

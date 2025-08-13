package com.example.romancesample.api;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface FaceApiService {

    @Multipart
    @POST("/face/register")
    Call<Void> registerFace(
            @Part("userId") RequestBody userId,
            @Part MultipartBody.Part file
    );
}

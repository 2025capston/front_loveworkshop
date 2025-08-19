package com.example.romancesample.api;

import com.example.romancesample.model.ReportRequest;
import com.example.romancesample.model.ReportResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ReportApi {

    // 신고 등록 (로그인 사용자 전용)
    @POST("/api/reports")
    Call<ReportResponse> createReport(@Body ReportRequest reportRequest);

    // 신고 목록 조회 (관리자 전용)
    @GET("/api/reports")
    Call<List<ReportResponse>> getReportList();
}

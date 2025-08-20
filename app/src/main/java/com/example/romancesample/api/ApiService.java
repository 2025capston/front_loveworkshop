package com.example.romancesample.api;

import com.example.romancesample.MyResponse;
import com.example.romancesample.User;
import com.example.romancesample.model.LoginRequest;
import com.example.romancesample.model.LoginResponse;
import com.example.romancesample.model.ProfileRequest;
import com.example.romancesample.model.RegisterRequest;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


    // ===================== 매칭 관련 =====================
    // 매칭 신청 생성
    @POST("/api/match-requests")
    Call<MatchResponse> createMatchRequest(
            @Header("Authorization") String token,
            @Body MatchRequest request
    );

    // 매칭 신청 목록 조회
    @GET("/api/match-requests")
    Call<List<MatchResponse>> getMatchRequests(
            @Header("Authorization") String token
    );

    // 3) 서버에서 받은 신청 거절
    @POST("/api/match-requests/{matchId}/reject")
    Call<Void> rejectMatch(
            @Header("Authorization") String token,
            @Path("matchId") Long matchId
    );

    // 필요하면 수락 API도 추가 가능
    @POST("/api/match-requests/{matchId}/accept")
    Call<Void> acceptMatch(
            @Header("Authorization") String token,
            @Path("matchId") Long matchId
    );

    // 일정 제안
    @POST("/api/match-requests/{requestId}/propose-schedule")
    Call<Void> proposeSchedule(
            @Header("Authorization") String token,
            @Path("requestId") Long requestId,
            @Body ScheduleProposal proposal
    );

    @GET("/api/match-requests/{requestId}/schedule-options")
    Call<ArrayList<ScheduleOption>> getProposedSchedule(
            @Header("Authorization") String token,
            @Path("requestId") Long requestId
    );

    // 일정 확정
    @POST("/api/match-requests/{requestId}/confirm-schedule")
    Call<Void> confirmSchedule(
            @Header("Authorization") String token,
            @Path("requestId") Long requestId,
            @Query("dateOptionId") Long dateOptionId,
            @Query("timeOptionId") Long timeOptionId,
            @Query("placeOptionId") Long placeOptionId
    );


    // 확정된 일정 가져오기
    @GET("/api/match-requests/{requestId}/confirmed-schedule")
    Call<ScheduleOption> getConfirmedSchedule(
            @Header("Authorization") String token,
            @Path("requestId") Long requestId
    );

    // 매칭 신청 취소
    @POST("/api/match-requests/{requestId}/cancel")
    Call<Void> cancelMatchRequest(
            @Header("Authorization") String token,
            @Path("requestId") Long requestId,
            @Query("userId") Long userId
    );

    // ===================== 데일리 미션 =====================
    // 데일리 미션 등록
    @POST("/daily-missions")
    Call<DailyMission> createDailyMission(
            @Header("Authorization") String token,
            @Body DailyMission mission
    );

    // 오늘 배정된 미션 조회
    @GET("/daily-missions/today")
    Call<DailyMission> getTodayMission(
            @Header("Authorization") String token,
            @Query("matchId") Long matchId  // 특정 매칭에 대한 오늘 미션
    );


    // 미션 응답 제출
    @POST("/mission-response")
    Call<MissionResponse> submitMissionResponse(
            @Header("Authorization") String token,
            @Query("matchMissionId") Long matchMissionId,
            @Query("userId") Long userId,
            @Query("answer") String answer
    );

    // 미션 응답 조회
    @GET("/mission-response")
    Call<MissionComparison> getMissionResponses(
            @Header("Authorization") String token,
            @Query("matchMissionId") Long matchMissionId,
            @Query("userId") Long userId
    );

    // ===================== 착장 미션 =====================
    // 착장 미션 예약
    @POST("/meeting-missions/schedule")
    Call<MeetingMission> scheduleMeetingMission(
            @Header("Authorization") String token,
            @Query("matchRequestId") Long matchRequestId,
            @Query("meetingTime") String meetingTime
    );

    // ---------------------------
    // 착장 제출 (이미지 파일 포함 Multipart)
    // ---------------------------
    @Multipart
    @POST("/outfit-submissions")
    Call<OutfitSubmission> submitOutfit(
            @Header("Authorization") String token,
            @Part("matchRequestId") long matchRequestId,
            @Part("userId") Long userId,
            @Part MultipartBody.Part imageFile
    );

    // ---------------------------
    // 착장 조회
    // ---------------------------
    @GET("/outfit-submissions/{matchRequestId}/{userId}")
    Call<OutfitSubmission> getOutfit(
            @Header("Authorization") String token,
            @Path("matchRequestId") Long matchRequestId,
            @Path("userId") Long userId
    );

}
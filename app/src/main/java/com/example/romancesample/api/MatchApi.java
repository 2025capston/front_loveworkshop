package com.example.romancesample.api;

import com.example.romancesample.model.MatchKeepStatusDTO;
import com.example.romancesample.model.MeetingResultResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface MatchApi {
    @PATCH("api/match-requests/{requestId}/keep-status")
    Call<MeetingResultResponseDTO> updateMatchKeepStatus(
            @Path("requestId") int requestId,
            @Body MatchKeepStatusDTO keepStatus
    );

}
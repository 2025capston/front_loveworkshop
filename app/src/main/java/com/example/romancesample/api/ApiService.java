package com.example.romancesample.api;

import com.example.romancesample.model.MatchKeepStatusRequestDTO;
import com.example.romancesample.model.MatchKeepStatusResponseDTO;
import com.example.romancesample.model.MeetingResultRequestDTO;
import com.example.romancesample.model.MeetingResultResponseDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface ApiService {
    @PATCH("api/match-requests/{requestId}/meeting-result")
    Call<MeetingResultResponseDTO> updateMeetingResult(
            @Path("requestId") int requestId,
            @Body MeetingResultRequestDTO meetingResultRequest
    );
    @PATCH("api/match-requests/{requestId}/keep-status")
    Call<MatchKeepStatusResponseDTO> updateMatchKeepStatus(
            @Path("requestId") int requestId,
            @Body MatchKeepStatusRequestDTO request
    );

}

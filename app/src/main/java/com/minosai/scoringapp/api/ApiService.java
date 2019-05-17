package com.minosai.scoringapp.api;

import com.minosai.scoringapp.model.ResponseModel;
import com.minosai.scoringapp.model.ResponseModelPayload;
import com.minosai.scoringapp.model.payload.EmployeePayload;
import com.minosai.scoringapp.model.payload.EventsPayload;
import com.minosai.scoringapp.model.payload.GroupsPayload;
import com.minosai.scoringapp.model.payload.LeaderboardPayload;
import com.minosai.scoringapp.model.payload.LoginPayload;
import com.minosai.scoringapp.model.requestbody.EmpIdRequestModel;
import com.minosai.scoringapp.model.requestbody.LoginRequestModel;
import com.minosai.scoringapp.model.requestbody.RegisterRequestModel;
import com.minosai.scoringapp.model.requestbody.VoteRequestModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiService {

    @POST("api/auth/register")
    Call<ResponseModelPayload<EmployeePayload>> registerEmployee(@Body RegisterRequestModel registerRequestModel);

    @POST("api/auth/login")
    Call<ResponseModelPayload<LoginPayload>> loginEmployee(@Body LoginRequestModel loginRequestModel);

    @GET("api/employee/profile")
    Call<ResponseModelPayload<EmployeePayload>> fetchEmployeeDetails();

    @GET("api/employee/groups")
    Call<ResponseModelPayload<GroupsPayload>> fetchGroupsList();

    @GET("api/employee/events")
    Call<ResponseModelPayload<EventsPayload>> fetchEventsList();

    @GET("api/employee/leaderboard")
    Call<ResponseModelPayload<LeaderboardPayload>> fetchLeaderboard(@Query(value = "event_id") String eventId);

    @PUT("api/employee/score")
    Call<ResponseModel> putGroupVote(@Body VoteRequestModel voteRequestModel);

    @PUT("api/employee/id/new")
    Call<ResponseModel> updateEmployeeId(@Body EmpIdRequestModel empIdRequestModel);
}

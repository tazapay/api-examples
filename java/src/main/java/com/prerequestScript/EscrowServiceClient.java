package com.prerequestScript;

import com.createEscrow.CreateEscrowRequest;
import com.createEscrow.CreateEscrowResponse;
import com.createUser.CreateUserRequest;
import com.createUser.CreateUserResponse;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface EscrowServiceClient {

    @POST("/v1/escrow")
    @Step("Create Escrow initiated by")
    public Call<CreateEscrowResponse> createEscrow(@Body CreateEscrowRequest request);

    @POST("/v1/user")
    @Step("Create User")
    public Call<CreateUserResponse> createUser(@Body CreateUserRequest request);
}

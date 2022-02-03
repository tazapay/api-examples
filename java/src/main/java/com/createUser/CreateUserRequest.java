package com.createUser;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateUserRequest {
    private String email;
    private String firstName;
    private String lastName;
    private String country;
    private String indBusType;
    private String businessName;
    private String contactCode;
    private String contactNumber;


}

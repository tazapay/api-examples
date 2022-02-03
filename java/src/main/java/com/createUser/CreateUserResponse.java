package com.createUser;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateUserResponse {

    private String status;
    private String message;
    private Data data;

    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    @NoArgsConstructor
    @Getter@Setter
    public class Data{
        private String accountId;
        private String customerId;
    }


}

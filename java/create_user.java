import retrofit2.http.Body;
import retrofit2.http.POST;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

public class createUser {
    public static void main (String[] args) {
        @POST("/v1/user")
        public Call<CreateUserResponse> createUser(@Body CreateUserRequest request);
            try {
                CreateUserRequest buyerRequest = CreateUserRequest
                .builder()
                .email("//email")
                .firstName("//firstName")
                .lastName("//lastName")
                .country("//country")
                .indBusType("//indBusType")
                .businessName("//businessName")
                .build();

        Call<CreateUserResponse> escrow = escrowService.createUser(buyerRequest);
        Response<CreateUserResponse> response = escrow.execute();

    } catch(Exception e) {
        e.printStackTrace;
    }
}
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
@Data
@NoArgsConstructor
@Getter@Setter
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

}
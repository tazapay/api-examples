import retrofit2.http.Body;
import retrofit2.http.POST;
import io.qameta.allure.Step;
import retrofit2.Call;
import retrofit2.Response;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

public class createEscrow {
    public static void main (String[] args) {
        @POST("/v1/escrow")
        Call<CreateEscrowResponse> createEscrow(@Body CreateEscrowRequest request);
            try {
                CreateEscrowRequest escrowRequest = CreateEscrowRequest
                .builder()
                .txnType("goods")
                .releaseMechanism("tazapay")
                .initiatedBy("bcabf0d5-59a7-490f-bfc4-8b8b9cc47790")
                .buyerId("bcabf0d5-59a7-490f-bfc4-8b8b9cc47790")
                .sellerId("de7692f6-033e-4595-8532-636b3ae15222")
                .txnDescription("Positive transaction")
                .invoiceCurrency("SGD")
                .invoiceAmount(1000)
                .feeTier("standard")
                .feePaidBy("buyer")
                .feePercentage(100)
                .build();
        Call<CreateEscrowResponse> escrow = escrowService.createEscrow(escrowRequest);
        Response<CreateEscrowResponse> response = escrow.execute();
        
    } catch(Exception e) {
        e.printStackTrace;
    }
}
@Data
@Builder
@JsonNaming(value= PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CreateEscrowRequest {
    private String txnType;
    private String releaseMechanism;
    private String buyerId;
    private String sellerId;
    private String txnDescription;
    private String invoiceCurrency;
    private double invoiceAmount;
    private String feeTier;
    private String feePaidBy;
    private float feePercentage;
    private String initiatedBy;
}
@Data
@NoArgsConstructor
@Getter@Setter
public class CreateEscrowResponse {
    private Data data;
    private String status;
    private String message;

@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@NoArgsConstructor
@Getter@Setter
    public class Data{
        private String accountId;
        private String customerId;
    }

}

}
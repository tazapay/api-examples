package tests;

import com.commons.BaseTest;
import com.commons.Utility;
import com.createEscrow.CreateEscrowRequest;
import com.createEscrow.CreateEscrowResponse;
import com.prerequestScript.EscrowServiceClient;
import com.prerequestScript.EscrowServiceGenerator;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

public class CreateEscrowTest extends BaseTest {
    EscrowServiceClient escrowService;

@BeforeClass(description = "Instantiating Escrow Client", alwaysRun = true)
public void testSetup() {
        System.out.println("Test log");
        escrowService = new EscrowServiceGenerator().createService();
        }

@SneakyThrows
@Test(description = "To verify the escrow created with all the required fields entered ", groups = "test")
public void createEscrow(){
        CreateEscrowRequest escrowRequest = CreateEscrowRequest
        .builder()
        .txnType("")                    //"Goods" or "Service"
        .releaseMechanism("")           //"Marketplace name"
        .initiatedBy("")                //buyerId or sellerId
        .buyerId("")                    //Provide buyerId
        .sellerId("")                   //Provide sellerId
        .txnDescription("")             //Provide description
        .invoiceCurrency("")            //Provide the currency
        .invoiceAmount(1)                //Provide invoice amount
        .feeTier("")                     //Provide feeTier
        .feePaidBy("")                   //Provide "buyer" or "seller"
        .feePercentage(1)                //Provide feePercentage
        .build();
        Call<CreateEscrowResponse> escrow = escrowService.createEscrow(escrowRequest);
        Response<CreateEscrowResponse> response = escrow.execute();
        CreateEscrowResponse createEscrowResponse = response.body();
        System.out.println(Utility.prettyPrintJson(createEscrowResponse));

        }
        }

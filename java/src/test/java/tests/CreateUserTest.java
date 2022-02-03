package tests;

import com.commons.BaseTest;
import com.commons.Utility;
import com.createUser.CreateUserRequest;
import com.createUser.CreateUserResponse;
import com.prerequestScript.EscrowServiceClient;
import com.prerequestScript.EscrowServiceGenerator;
import lombok.SneakyThrows;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.Response;

import static org.testng.Assert.assertEquals;

public class CreateUserTest extends BaseTest {
    EscrowServiceClient escrowService;

    @BeforeClass(description = "Instantiating Escrow Client", alwaysRun = true)
    public void testSetup() {
        System.out.println("Test log");
        escrowService = new EscrowServiceGenerator().createService();
    }

    @SneakyThrows
    @Test(description = "To verify that user is created, when all Mandatory fields are entered and the request is sent. ", groups = "test")
    public void createUser() {
        CreateUserRequest buyerRequest = CreateUserRequest
                .builder()
                .email("")                          //Provide email
                .firstName("")                      //firstName is mandatory if indBusType = Individual
                .lastName("")                //lastName is mandatory if indBusType = Individual
                .country("")                        //Provide Country
                .indBusType("")                     //Provide "Business" or "Individual"
                .businessName("")                   //businessName is mandatory if indBusType = Business
                .contactCode("")                    //Provide contactCode
                .contactNumber("")                  //Provide contactNumber
                .build();
        Call<CreateUserResponse> escrow = escrowService.createUser(buyerRequest);
        Response<CreateUserResponse> response = escrow.execute();
        assertEquals(response.code(), 200);
        CreateUserResponse createUserResponse = response.body();
        System.out.println(Utility.prettyPrintJson(createUserResponse));
        assertEquals(createUserResponse.getStatus(), "success");
        assertEquals(createUserResponse.getMessage(), "User already exists");

    }
}

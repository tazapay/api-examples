// Create Payment
async function createPayment() {
    try {
      const body = {
        txn_no: "3181",
        percentage: 50,
      };
      const result = await makeRequest("POST", "/v1/session/payment", body);
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  createPayment();
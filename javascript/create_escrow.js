// Create Escrow
async function createEscrow() {
    try {
      const body = {
        initiated_by: "<valid uuid of escrow initiator>",
        buyer_id: "valid uuid of buyer",
        seller_id: "valid uuid of seller",
        txn_description: "Description",
        is_milestone: true,
        invoice_amount: 1000,
        invoice_currency: "USD",
      };

      const result = await makeRequest("POST", "/v1/escrow", body);
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  createEscrow();
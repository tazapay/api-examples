// Get Escrow Status
async function getEscrowStatus(txnNo) {
    try {
      const result = await makeRequest("GET", `/v1/escrow/${txnNo}`);
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  getEscrowStatus(3181);
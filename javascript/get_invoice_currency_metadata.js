// Get Invoice currency
async function getInvoiceCurrency(buyer_country, seller_country) {
    try {
      const result = await makeRequest(
        "GET",
        `/v1/metadata/invoicecurrency?buyer_country=${buyer_country}&seller_country=${seller_country}`
      );
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  getInvoiceCurrency("SG", "IN");
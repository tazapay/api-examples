// Get Country Config
async function getCountryConfig(country) {
    try {
      const result = await makeRequest(
        "GET",
        `/v1/metadata/countryconfig?country=${country}`
      );
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  getCountryConfig("US");
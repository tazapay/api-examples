// Create User
async function createUser() {
    try {
      const body = {
        email: "johndoe@gmail.com",
        country: "India",
        ind_bus_type: "Business",
        business_name: "My Business",
      };
      const result = await makeRequest("POST", "/v1/user", body);
  
      console.log(JSON.stringify(result));
    } catch (error) {
      console.error("Error completing request", error);
    }
  }
  
  createUser();
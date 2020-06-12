package zoho;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CustomerOperations 
{	String accessToken = "1000.52f5d55add952da528193def7199baa6.0524c19ebbea774316825afaaa09f79e";
	String organizationalId = "717071898";
	
	File jsonFile = new File("testData/customerCreation.json");
	File jsonFileUpdate = new File("testData/customerCreation.json");
	
	@Test
	void CustomerActions()
	{
		RestAssured.baseURI = "https://subscriptions.zoho.com";
		
		RestAssured.authentication = RestAssured.oauth2(accessToken);
		
		Response postResponse = RestAssured
							.given()
							.contentType(ContentType.JSON)
							.queryParam("Authorization", "Zoho-oauthtoken "+accessToken)
							.queryParam("X-com-zoho-subscriptions-organizationid", organizationalId)
							.queryParam("Content-Type", "application/json;charset=UTF-8")						
							.body(jsonFile)
							.post("/api/v1/customers");
		
				
		JsonPath jsonPostResponse = postResponse.jsonPath();
		String customerId = jsonPostResponse.getString("customer.customer_id");
		System.out.println("The customer Id-->"+customerId);
		
		//postResponse.prettyPrint();
		if(postResponse.getStatusCode()==201)
			System.out.println("The request is successfull and Customer with CustomerId: "+customerId+" is created!!");
		else
			System.out.println("The request is not successfull and Customer is not created!!");
		
		
		Response getResponse = RestAssured
							.given()
							.contentType(ContentType.JSON)
							.queryParam("Authorization", "Zoho-oauthtoken "+accessToken)
							.queryParam("X-com-zoho-subscriptions-organizationid", organizationalId)
							.queryParam("Content-Type", "application/json;charset=UTF-8")						
							.get("/api/v1/customers/"+customerId);
		
		//getResponse.prettyPrint();
		
		if(getResponse.getStatusCode()==200)
			System.out.println("The request is successfull and Customer with CustomerId: "+customerId+" is retrieved!!");
		else
			System.out.println("The request is successfull and Customer is not retrieved!!");
		
		Response putResponse = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.queryParam("Authorization", "Zoho-oauthtoken "+accessToken)
				.queryParam("X-com-zoho-subscriptions-organizationid", organizationalId)
				.queryParam("Content-Type", "application/json;charset=UTF-8")						
				.body(jsonFileUpdate)
				.put("/api/v1/customers/"+customerId);
		
		//putResponse.prettyPrint();
		if(putResponse.getStatusCode()==200)
			System.out.println("The request is successfull and Customer with CustomerId: "+customerId+" is updated!!");
		else
			System.out.println("The request is successfull and Customer is not updated!!");
		
		Response deleteResponse = RestAssured
				.given()					
				.delete("/api/v1/customers/"+customerId);
		
		//deleteResponse.prettyPrint();
		if(deleteResponse.getStatusCode()==200)
			System.out.println("The request is successfull and Customer with CustomerId: "+customerId+" is deleted!!");
		else
			System.out.println("The request is successfull and Customer is not deleted!!");
		
		Response getResponse2 = RestAssured
				.given()
				.contentType(ContentType.JSON)
				.queryParam("Authorization", "Zoho-oauthtoken "+accessToken)
				.queryParam("X-com-zoho-subscriptions-organizationid", organizationalId)
				.queryParam("Content-Type", "application/json;charset=UTF-8")						
				.get("/api/v1/customers/"+customerId);

		//getResponse.prettyPrint();

		if(getResponse2.getStatusCode()==400)
			System.out.println("The Customer with CustomerId: "+customerId+" is deleted so it cannot be retrived anymore!!");
		else
			System.out.println("The request is successfull and Customer is not deleted!!");
	}
}

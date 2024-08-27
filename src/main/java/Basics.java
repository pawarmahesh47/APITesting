import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.Response.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.core.IsEqual.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.http.util.Asserts;
import org.testng.Assert;

import Files.ReusableMethod;
import Files.payLoad;

public class Basics {

	public static void main(String[] args) throws IOException {

		// given - all input details
		// when - submit the API
		// then - validate the response
		
		//add place > update address > get new address and validate new address
		//Content of the file to String > content of file convent into Byte > then convent byte to String
		
		//Add place

		RestAssured.baseURI = "https://rahulshettyacademy.com";

		String responce =given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("D:\\Mahesh\\Java\\APITesting\\AddPlace1.json"))))
				.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(responce);
		
		 JsonPath js =ReusableMethod.rowToJson(responce);
		
		String place = js.getString("place_id");
		System.out.println(place);
		
		//update place
		
		String newAddress = "70 Summer walk, USA";
		
		given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+place+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		
		//get place
		String getPlaceAddess = given().queryParam("key", "qaclick123").queryParam("place_id", place).when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 =ReusableMethod.rowToJson(getPlaceAddess);
		String actualAddress = js1.getString("address");
		
		
		
		System.out.println(actualAddress);
		
		Assert.assertEquals(actualAddress, newAddress);
		
		

	}
}

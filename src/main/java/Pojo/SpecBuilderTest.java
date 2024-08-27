package Pojo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class SpecBuilderTest {

	public static void main(String[] args) {
		RestAssured.baseURI = "https://rahulshettyacademy.com";

		AddPlace add = new AddPlace();
		add.setAccuracy(50);
		add.setLanguage("French-IN");
		add.setName("Frontline house");
		add.setPhone_number("(+91) 983 893 3937");
		add.setTypes(null);
		add.setWebsite("http://google.com");

		Location l = new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);

		add.setLocation(l);

		List<String> list = new ArrayList<String>();
		list.add("shoe park");
		list.add("shop");

		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
		ResponseSpecification responceSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		RequestSpecification request = given().spec(requestSpec).body(add);
		String responce = request.when().post("maps/api/place/add/json").then().spec(responceSpec).log().all().extract()
				.response().asString();
		
		System.out.println(responce);
	}

}

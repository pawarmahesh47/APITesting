package Pojo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import io.restassured.RestAssured;

public class SerializeTest {
	
	public static void main(String[] args)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
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
		
		
		String responce =given().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(add)
				.when().post("maps/api/place/add/json").then().log().all().assertThat().statusCode(200)
				.extract().response().asString();		
	}
	

}

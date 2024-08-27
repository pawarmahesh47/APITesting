import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;

import Pojo.Api;
import Pojo.GetCourses;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;
public class ClientCredentialOAuth {

	public static void main(String[] args) {

		// Authorization from Authorization Server
		String responce = given().log().all().formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().statusCode(200).extract().asString();

		JsonPath js = new JsonPath(responce);
		String token = js.get("access_token").toString();
		System.out.println(token);
		
		
		//get Course Details
		GetCourses gs = given().queryParam("access_token", token).log().all()
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourses.class);
		
		System.out.println(gs.getLinkedIn());
		System.out.println(gs.getServices());
		
		
		
		List<Api> apiCourses =gs.getCourses().getApi();
		
		
		for(int i=0;i<apiCourses.size(); i++)
		{
			if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUi Webservices testing"))
			{
				System.out.println(apiCourses.get(i).getPrice());
			}
		}
		
		//Compare expected & actual courses
		String[] expectedCourses = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		
		ArrayList<String> actualCourses = new ArrayList<String>();
		
		
		
		List<WebAutomation> webAutomationCourses= gs.getCourses().getWebAutomation();
		
		for(int i=0; i<webAutomationCourses.size(); i++)
		{
			actualCourses.add(webAutomationCourses.get(i).getCourseTitle());
		}
		
		List<String> expectedwebAutomationCourses =Arrays.asList(expectedCourses);
		
		Assert.assertTrue(actualCourses.equals(expectedwebAutomationCourses));
	}
	
	

}

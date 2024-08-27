import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JiraTest {

	public static void main(String[] args) {

		RestAssured.baseURI = "http://localhost:8080";

		// Login into JIRA session

		SessionFilter session = new SessionFilter();

		given().log().all().header("content-type", "application/json")
				.body("{ \"username\": \"pawar.mahesh47\", \"password\": \"Trimurti@3767\" }").filter(session).when()
				.post("/rest/auth/1/session").then().log().all().statusCode(200);

		// Add comment into project bug

		String addMessage = "Comments added through RestAssured automated API";
		String addCommentString = given().log().all().header("content-type", "application/json")
				.pathParam("Key", "10101")
				.body("{\r\n" + "    \"body\": \"" + addMessage + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{Key}/comment").then().log().all().statusCode(201)
				.extract().asString();

		JsonPath js = new JsonPath(addCommentString);
		String commentID = js.get("id");

		// add attachment to bug
		given().header("X-Atlassian-Token", "no-check").filter(session).pathParam("Key", "10101")
				.header("content-type", "multipart/form-data").multiPart("file", new File("Jira.txt")).when()
				.post("/rest/api/2/issue/{Key}/attachments").then().log().all().statusCode(200);

		// get issue details
		String issuDetails = given().filter(session).pathParam("Key", "10101").queryParam("fields", "comment").log()
				.all().when().get("/rest/api/2/issue/{Key}").then().log().all().extract().asString();

		System.out.println("Comments are");
		System.out.println(issuDetails);

		JsonPath js1 = new JsonPath(issuDetails);
		int totalCommentCount = js1.get("fields.comment.comments.size()");

		for (int i = 0; i < totalCommentCount; i++) {
			String commentIDIssue = js1.get("fields.comment.comments[" + i + "].id").toString();
			if (commentIDIssue.equalsIgnoreCase(commentID)) {
				String commentMessage = js1.get("fields.comment.comments[" + i + "].body").toString();
				System.out.println(commentMessage);
				Assert.assertEquals(commentMessage, addMessage);
			}

		}

	}

}

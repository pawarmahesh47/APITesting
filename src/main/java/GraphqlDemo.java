import static io.restassured.RestAssured.*;

import io.restassured.path.json.JsonPath;

public class GraphqlDemo {

	public static void main(String[] args) {
		
		String locationName= "wai";
		String characterName= "Mahesh";
		String episodeName="Nirvi Bday";

			String responce = given().log().all().header("content-type", "application/json")
			.body("{\"query\":\"mutation($locationName:String!, $characterName:String!, $episodeName:String!)\\n{\\n  createLocation(location:{name:$locationName, type:\\\"satara\\\",dimension:\\\"1235\\\"})\\n  {\\n    id\\n  }\\n  createCharacter(character:{name:$characterName, type:\\\"Macho\\\", status:\\\"dead\\\",species:\\\"xyz\\\",gender:\\\"Male\\\", image:\\\"png\\\", originId:12813, locationId:12813})\\n  {\\n    id\\n  }\\n  createEpisode(episode:{name:$episodeName, air_date:\\\"07Nov\\\", episode:\\\"prime\\\"})\\n  {\\n    id\\n  }\\n}\",\"variables\":{\"locationName\":\""+locationName+"\",\"characterName\":\""+characterName+"\",\"episodeName\":\""+episodeName+"\"}}")
			.when().post("https://rahulshettyacademy.com/gq/graphql")
			.then().extract().response().asString();
			
			System.out.println(responce);
			JsonPath js = new JsonPath(responce);
			String characterId = js.getString("data.createLocation.id");
			System.out.println(characterId);
			
			
	}

}

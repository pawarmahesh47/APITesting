package Files;

import io.restassured.path.json.JsonPath;

public class ReusableMethod {
	
	public static JsonPath rowToJson(String responce)
	{
		JsonPath js = new JsonPath(responce);
		
		return js;
	
	}

}

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Files.ReusableMethod;
import Files.payLoad;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class DynamicJson {

	@Test(dataProvider ="BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI="http://216.10.245.166";
		
		String response = given().log().all().header("Content-Type", "application/json")
		.body(payLoad.addBook(isbn, aisle))
		.when().post("Library/Addbook.php")
		.then().log().all().statusCode(200).extract().asString();
		
		JsonPath js =ReusableMethod.rowToJson(response);
		js.get("ID");
		
		
	}
	
	@DataProvider(name ="BooksData")
	public Object[][] getData()
	{
		 String[][] data ={{"asda","6546"},{"ddfd","5656"},{"dxvx","9899"}};
		 
		 return data;
		//return new Object[][] {{"asda","6546"},{"ddfd","5656"},{"dxvx","9899"}};
	}
}

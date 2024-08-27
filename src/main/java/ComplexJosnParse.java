import Files.payLoad;
import io.restassured.path.json.JsonPath;

public class ComplexJosnParse {

	public static void main(String[] args) {
		
		
		JsonPath js = new JsonPath(payLoad.CoursePrice());
		
		//Print No of courses returned by API
		int couses = js.getInt("courses.size()");
		System.out.println(couses);
		
		//Print Purchase amount
		int purchaseAmout = js.getInt("dashboard.purchaseAmount");
		System.out.println(purchaseAmout);

		
		//Print Title of the first course
		String firstCourse = js.getString("courses[0].title");
		System.out.println(firstCourse);
		
		//Print All course titles and their respective Prices
		
		for (int i=0; i<couses; i++)
		{
			String courseTitle = js.getString("courses["+i+"].title");
			System.out.println(courseTitle);
			System.out.println(js.get("courses["+i+"].price").toString());
		}
		
		//Print no of copies sold by RPA Course
		
		for (int i=0; i<couses; i++)
		{
			
			String courseTitle = js.getString("courses["+i+"].title");
			if(courseTitle.equalsIgnoreCase("RPA"))
			{
				int rpaCopies = js.getInt("courses["+i+"].copies");
				System.out.println(rpaCopies);
				break;
			}
			
			
		}
		//Verify if Sum of all Course prices matches with Purchase Amount
	}

}

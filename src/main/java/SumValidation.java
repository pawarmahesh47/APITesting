import org.testng.Assert;
import org.testng.annotations.Test;

import Files.payLoad;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses()
	{
		int sum =0;
		JsonPath js = new JsonPath(payLoad.CoursePrice());
		int courses = js.getInt("courses.size()");
		for (int i=0; i<courses; i++)
		{
			int coursePrice = js.getInt("courses["+i+"].price");
			int courseCopies = js.getInt("courses["+i+"].copies");
			
			int amount = coursePrice * courseCopies;
			System.out.println(amount);
			sum = sum +amount;
			System.out.println(sum);
		}
		int purschaseAmout = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purschaseAmout);
	}
}

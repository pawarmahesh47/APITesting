
import static io.restassured.RestAssured.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import Pojo.LoginRequest;
import Pojo.LoginResponce;
import Pojo.Order;
import Pojo.OrderDetails;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

public class EcommerceAPITest {

	public static void main(String[] args) {
		
		//Login to E-Commerce

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		
		LoginRequest loginreq =new LoginRequest();
		loginreq.setUserEmail("nirvi@gmail.com");
		loginreq.setUserPassword("Nirvi@123");
		
		
		RequestSpecification loginReq = given().relaxedHTTPSValidation().log().all().spec(req).body(loginreq);
		
		LoginResponce loginresponce = loginReq.when().post("/api/ecom/auth/login").then().log().all().extract().as(LoginResponce.class);
		
		String token = loginresponce.getToken();
		String userid = loginresponce.getUserId();
		System.out.println(token);
		System.out.println(userid);
		System.out.println(loginresponce.getMessage());		
		
		//Add Product
		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).build();
		
		RequestSpecification addProductReq =given().spec(addProductBaseReq).param("productName", "qwerty").param("productAddedBy", userid)
		.param("productCategory", "fashion").param("productSubCategory", "fashion").param("productPrice", "11500")
		.param("productDescription", "Addias Originals").param("productFor", "women")
		.multiPart("productImage", new File("C:\\Users\\u8gvor\\Postman\\files\\passed.png"));
		
		String addProductResonce = addProductReq.when().post("/api/ecom/product/add-product").then().log().all().extract().asString();
		
		JsonPath path = new JsonPath(addProductResonce);
		String productId =path.get("productId");
		
		//Create Order
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		OrderDetails orderdetails = new OrderDetails();
		orderdetails.setCountry("India");
		orderdetails.setProductOrderedId(productId);
		
		List<OrderDetails> orderList = new ArrayList<OrderDetails>();
		orderList.add(orderdetails);
		
		Order orders = new Order();
		orders.setOrders(orderList);
		
		RequestSpecification createOrderSpec = given().log().all().spec(createOrderBaseReq).body(orders);
		String createOrderResponceString = createOrderSpec.when().post("/api/ecom/order/create-order").then().log().all().extract().asString();
		System.out.println(createOrderResponceString);
		
		//Delete order
		
		
		RequestSpecification deleteOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token).setContentType(ContentType.JSON).build();
		
		RequestSpecification deleteProdReq=given().log().all().spec(deleteOrderBaseReq).pathParam("productId", productId);
		String deleteOrderResponceString =deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response().asString();
		
		JsonPath js1 = new JsonPath(deleteOrderResponceString);
		String deleteResponceMsg = js1.get("message");
		
		Assert.assertEquals("Product Deleted Successfully", deleteResponceMsg);
		

	}

}

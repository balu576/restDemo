package com.employee.testcases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.RestUtils;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC004_Put_Employee_Record extends TestBase {
	RequestSpecification httpRequest;
	Response response;
	String empName=RestUtils.empName();
	String empSalary=RestUtils.empSal();
	String empAge=RestUtils.empAge();
	
	@BeforeClass
	void updateEmployee() throws InterruptedException
	{
		logger.info("*********Started TC003_Post_Employee_Record*********");
		RestAssured.baseURI="http://dummy.restapiexample.com/api/v1";
		httpRequest=RestAssured.given();
		
		//Recreated Data which we can send along with POST request
		JSONObject requestParams=new JSONObject();
		requestParams.put("name", empName);
		requestParams.put("salary", empSalary);
		requestParams.put("age", empAge);
		
		//Add a header stating Request Body is json
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString()); //attach above data to the request

		//PUT Request
		response=httpRequest.request(Method.PUT,"/update/"+empId);
		
		Thread.sleep(3000);
		
	}
	
	@Test
	void checkResponseBody()
	{
		//Capture Response Body to perform Validations
				String responseBody=response.getBody().asString();
				Assert.assertEquals(responseBody.contains(empName), true);
				Assert.assertEquals(responseBody.contains(empSalary), true);
				Assert.assertEquals(responseBody.contains(empAge), true);
	}
	
	@Test
	void checkStatusCode()
	{
		int statusCode=response.getStatusCode();
		System.out.println("StatusCode :" +statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	@Test
	void checkResponseTime()
	{
		logger.info("*****Checking Response Time*******");
		long responseTime=response.getTime();
		logger.info("Response Time==>"+responseTime);
		if(responseTime>2000)
			logger.warn("Response Time is greater than 2000");
		Assert.assertTrue(responseTime<2000);
	}
	
	@Test
	void checkStatusLine()
	{
		logger.info("*****Checking Status Line*******");
		String statusLine=response.getStatusLine();
		logger.info("StatusLine is =>"+statusLine);
		Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");
	}
	
	@Test
	void checkContentType()
	{
		logger.info("*****Checking Content Type*******");
		String contentType=response.header("Content-Type");
		logger.info("ContentType is =>"+contentType);
		Assert.assertEquals(contentType,"text/html; charset=UTF-8");
	}
	
	@Test
	void checkServerType()
	{
		logger.info("*****Checking Server Type*******");
		String serverType=response.header("Server");
		logger.info("ServerType is =>"+serverType);
		Assert.assertEquals(serverType,"nginx/1.14.1");
	}
	
	@Test
	void checkcontentEncoding()
	{
		logger.info("*****Checking Content Encoding*******");
		String contentEncoding=response.header("Content-Encoding");
		logger.info("Content Encoding is =>"+contentEncoding);
		Assert.assertEquals(contentEncoding,"qzip");
	}
	
	@Test
	void checkContentLength()
	{
		logger.info("*****Checking Content Length*******");
		String contentLength=response.header("Content-Length");
		logger.info("Content Length is =>"+contentLength);
		if(Integer.parseInt(contentLength)<100)
			logger.warn("Content Length is less than 100");
		Assert.assertTrue(Integer.parseInt(contentLength)>100);
	}
	
	@Test
	void checkCookies()
	{
		logger.info("*****Checking Cookies*******");
		String cookie=response.getCookie("PHPSESSID");
	}
	
	@AfterClass
	void tearDown()
	{
		logger.info("*****Finished TC003_Post_Employee_Record*******");
	}


}

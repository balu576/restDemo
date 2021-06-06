package com.employeeapi.utilities;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Listeners extends TestListenerAdapter {
	public ExtentHtmlReporter htmlReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	public void onStart(ITestContext testContext)
	{
		//Specify the location 
		htmlReporter=new ExtentHtmlReporter(System.getProperty("user.dir")+"/Reports/myReport.html");
		
		//Title of the Report
		htmlReporter.config().setDocumentTitle("Automation Report");
		
		//Name of the Report
		htmlReporter.config().setReportName("REST API Testing Report");
		
		//Theme
		htmlReporter.config().setTheme(Theme.DARK);
		
		extent=new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Project Name","Employee Database API");
		extent.setSystemInfo("Host Name","localhost");
		extent.setSystemInfo("Environment","QA");
		extent.setSystemInfo("User","Balu");
	}
	
	public void onTestSuccess(ITestResult result)
	{
		//create new entry in the report
		test=extent.createTest(result.getName());
		test.log(Status.PASS,"Test Case PASSED is "+result.getName());
		
	}
	
	public void onTestFailure(ITestResult result)
	{
		//create new entry in the report
		test=extent.createTest(result.getName());
		
		//to add name in extent report
		test.log(Status.FAIL,"Test Case FAILED is "+result.getName());
		
		//to add error/exception in extent report
		test.log(Status.FAIL,"Test Case FAILED is "+result.getThrowable());
		
	}
	
	public void onTestSkipped(ITestResult result)
	{
		//create new entry in the report
		test=extent.createTest(result.getName());
		test.log(Status.SKIP,"Test Case SKIPPED is "+result.getName());
		
	}
	
	public void onFinish(ITestResult result)
	{
		extent.flush();
		
	}
	

}

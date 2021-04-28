package tests;

import org.testng.annotations.Test;
import org.xml.sax.SAXException;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import jdk.internal.org.jline.utils.Log;
import pages.Login;
import pages.Main;
import pages.Registration;
import pages.Restaurant;
import utilites.GetDriver;
import utilites.Utilities;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

public class SanityTest {

	// Global variables
	// Add extent reports
	private ExtentReports extent;
	private ExtentTest myTest;
	private static String reportPaht = System.getProperty("user.dir") + "\\test-output\\reportSanity.html";

	private WebDriver driver;

	// pages
	private Main main;
	private Login login;
	private Restaurant restaurant;

	private static final Logger logger = LogManager.getLogger(SanityTest.class);
	private static String userName;
	private static String password;
	private static String browser;
	private static String baseUrl;
	private static String name;
	private static String restaurantName;

	@BeforeClass
	public void beforeClass() throws ParserConfigurationException, SAXException, IOException {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/log4j.properties");

		extent = new ExtentReports(reportPaht);
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\resources\\extent-config.xml"));

		baseUrl = Utilities.getDataFromXML("info.xml", "website", 0);
		browser = Utilities.getDataFromXML("info.xml", "browser", 0);
		userName = Utilities.getDataFromXML("info.xml", "userName", 0);
		password = Utilities.getDataFromXML("info.xml", "password", 0);
		restaurantName= Utilities.getDataFromXML("info.xml", "restaurantName", 0);
		name= Utilities.getDataFromXML("info.xml", "name", 0);
		

		driver = GetDriver.getDriver(browser, baseUrl);

		main = new Main(driver);
		login = new Login(driver);
		restaurant = new Restaurant(driver);

	}

	@BeforeMethod
	public void beforeMethod(Method method) throws IOException {
		myTest = extent.startTest(method.getName());
		myTest.log(LogStatus.INFO, "Starting test", "Start test");
	}

	/*  Prerequisite: getting into https://www.10bis.com/
	 * 		Given: Client is on the main page 
	 * 		When:Looking for a restaurant in the search field
	 *  	Then: The page of the selected restaurant opens
	 */
	@Test(priority = 1, enabled = true, description = "Going to restaurant page")
	public void GoingToRestaurant()
			throws InterruptedException, IOException, ParserConfigurationException, SAXException {
		
		logger.info("Going to restaurant page ");
		
		main.login();
		
		login.doLoginFacebook(userName, password, name);
		
		Assert.assertTrue(restaurant.SearchRestaurant(restaurantName));
		
		logger.info("Successfully Get into restaurant page");
		
	}
	

	/*  Prerequisite: getting into https://www.10bis.com/
	 * 		Given: Client is on the restaurant page 
	 * 		When:click on adding a dish to the shopping cart
	 *  	Then: The selected dishes were added to the payment stand
	 */
	
	
	@Test(priority = 2, enabled = true, description = "Going to order page")
	public void GoingToOrderPage()
			throws InterruptedException, IOException, ParserConfigurationException, SAXException {
		
		logger.info("Going to order page ");
		
		Assert.assertTrue(restaurant.OrderPage());
		
		logger.info("Successfully Get into checkout page");
		
	}
	

	@AfterMethod
	public void afterMethod(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.FAILURE) {
			myTest.log(LogStatus.FAIL, "Test failed: " + result.getName());
			myTest.log(LogStatus.FAIL, "Test failed reason: " + result.getThrowable());
			myTest.log(LogStatus.FAIL, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));
		} else {
			myTest.log(LogStatus.PASS, result.getName(), "Verify successful ");
			myTest.log(LogStatus.PASS, myTest.addScreenCapture(Utilities.takeScreenShot(driver)));

		}

		myTest.log(LogStatus.INFO, "Finish test", "Finish test ");
		extent.endTest(myTest);

		// return to base URL
		// driver.get(baseUrl);
	}

	@AfterClass
	public void afterClass() {
		extent.flush();
		extent.close();
		driver.quit();

	}

}

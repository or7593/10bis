package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Login extends Base {

	public Login(WebDriver driver) {
		super(driver);
	}

	// start login
	public boolean doLoginFacebook(String userName, String password, String name) throws InterruptedException {

		String baseHandle = driver.getWindowHandle();

		// Click facebook image
		click(By.xpath("//img[@type='facebook']"));

		// switch to Facebook login window

		Thread.sleep(2000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}

		// type user/password
		driver.findElement(By.id("email")).sendKeys(userName);
		Thread.sleep(1000);

		driver.findElement(By.id("pass")).sendKeys(password);
		Thread.sleep(1000);
		click(By.name("login"));

		Thread.sleep(4000);

		driver.switchTo().window(baseHandle);
		Thread.sleep(3000);

		WebElement address = driver.findElement(By.cssSelector("#homePage_SelectAddress"));
		Thread.sleep(1000);
		address.sendKeys("דרך מנחם בגין 74, פתח תקווה, ישראל");
		Thread.sleep(2000);
		address.sendKeys(Keys.ENTER);
		Thread.sleep(1000);

		click(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/section[1]/div/div[1]/div[1]/div/button"));

		String personalMsg = getText(By.cssSelector(".styled__PrimaryText-zzhidz-4.cfoTPh"));

		if (personalMsg.contains(name))
			return true;
		else
			return false;

	}

	// Receive an error message
	public boolean errorMessage() throws InterruptedException {
		click(By.xpath("//button[@data-test='login-submit']"));

		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();

		Thread.sleep(1000);

		String err = driver.findElement(By.xpath("//div[@role='alert']")).getText();

		if (err.equals("שדה חובה"))
			return true;
		else
			return false;
	}

}

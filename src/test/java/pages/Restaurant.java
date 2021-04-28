package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.xml.LaunchSuite.ExistingSuite;

public class Restaurant extends Base {

	public Restaurant(WebDriver driver) {
		super(driver);
	}

	// going to restaurant
	public boolean SearchRestaurant(String restaurantName) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/header/div[2]/div/div[4]/div/div[1]/input"))
				.sendKeys(restaurantName);
		Thread.sleep(2000);

		click(By.xpath("//*[@id=\"searchResults\"]/div/a"));

		if (isExist(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/section/header/div/div[2]/div[1]/h1")))
			return true;
		else
			return false;

	}

	// going to order
	public boolean OrderPage() throws InterruptedException {

		click(By.id("dishName_2250899_0"));
		click(By.xpath("//*[@id=\"modals\"]/div/div/div/div/div/div[4]/div/button"));
		Thread.sleep(1000);

		click(By.id("dishName_2250902_0"));
		Thread.sleep(1000);

		click(By.xpath("//*[@id=\"modals\"]/div/div/div/div/div/div[4]/div/button"));
		Thread.sleep(1000);

		click(By.xpath("//*[@id=\"menu-body-wrapper\"]/div[2]/aside/div/div[2]/div[1]/button"));

		if (isExist(By.xpath("//*[@id=\"root\"]/div[2]/div[1]/div/div[1]/div/h1")))
			return true;
		else
			return false;

	}

	// going to minimum order test
	public boolean mininumOrder(String restaurantName) throws InterruptedException {

		driver.findElement(By.xpath("//*[@id=\"root\"]/div[2]/header/div[2]/div/div[4]/div/div[1]/input"))
				.sendKeys(restaurantName);
		Thread.sleep(2000);

		click(By.xpath("//*[@id=\"searchResults\"]/div/a"));
		Thread.sleep(1000);

		click(By.id("dishName_2250944_0"));
		Thread.sleep(2000);

		click(By.xpath("//*[@id=\"modals\"]/div/div/div/div/div/div[4]/div/button"));
		Thread.sleep(1000);

		click(By.xpath("//*[@id=\"menu-body-wrapper\"]/div[2]/aside/div/div[2]/div[1]/button"));
		Thread.sleep(1000);

		String errorText = driver.findElement(By.xpath("//*[@id=\"modal-content\"]/div[1]")).getText();

		if (errorText.contains("ההזמנה שלך לא עברה את המינימום"))
			return true;
		else
			return false;

	}

}

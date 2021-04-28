package pages;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class Main extends Base {

	public Main(WebDriver driver) {
		super(driver);
	}

	// start registration
	public boolean register() throws InterruptedException {

		click(By.xpath("//button[text()='הרשמה']"));
		if (isExist(By.id("modal-title")))
			return true;
		else
			return false;

	}

	// start login
	public boolean login() throws InterruptedException {

		// Click connection
		click(By.xpath("//button[text()='התחברות']"));
		//String pageText= getText(By.xpath("//*[@id=\"modal-title\"]"));
		
		
		Thread.sleep(3000);


		if (isExist(By.xpath("//*[@id=\"login_tab_controls\"]/div/button")))
			return true;
		else
			return false;

	}

	// Verify job finding field

	public boolean searchCareers() throws InterruptedException {

		// lickC connection

		Thread.sleep(2000);

		click(By.xpath("//*[@id=\"root\"]/div[2]/footer/div/section[1]/div[1]/div/a[5]/div"));

		Thread.sleep(3000);

		String baseHandle = driver.getWindowHandle();

		Thread.sleep(2000);
		Set<String> handels = driver.getWindowHandles();

		for (String h : handels) {
			if (!h.equals(baseHandle))
				driver.switchTo().window(h);
		}

		String url = driver.getCurrentUrl();

		if (url.equals("https://careers.takeaway.com/global/en"))
			return true;
		else
			return false;

	}

}

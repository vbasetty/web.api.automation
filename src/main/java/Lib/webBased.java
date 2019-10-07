package Lib;
import Lib.Base;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;

public class webBased extends Base{

	public static void openBrowser() throws InterruptedException {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver",projectLocation + "\\src\\main\\resources\\ExecutableChrome\\chromedriver.exe");
		driver = new ChromeDriver(options);
		driver.get(getConfig("baseUrl"));
		driver.manage().window().maximize();
		wait(6);
	}

	public static void wait(int seconds){
		try {
			Thread.sleep(seconds*100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void signIn() throws InterruptedException 
	{

		driver.findElement(By.xpath(getConfig("loginButton"))).click();
		driver.findElement(By.xpath(getConfig("userName"))).sendKeys(getConfig("userNameValue"));
		driver.findElement(By.xpath(getConfig("password"))).sendKeys(getConfig("passwordValue"));
		driver.findElement(By.xpath(getConfig("signInButton"))).click();
		wait(6);
	}

	public static void openRandomGameInUserCollections() {

		wait(6);
		//wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(getConfig("userIdButton"))));
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(getConfig("userIdButton"))));
		driver.findElement(By.xpath(getConfig("userIdButton"))).click();
		driver.findElement(By.linkText("Collection")).click();
		WebElement totalCollection = driver.findElement(By.xpath(getConfig("totalCollectionList")));
		List<WebElement> numberOfGames = totalCollection.findElements(By.xpath(getConfig("totalCollectionList")));
		Random r = new Random();
		int randomSelection = r.nextInt(numberOfGames.size());
		System.out.println("Random Generated Number: " + randomSelection);
		if (randomSelection == 0 || randomSelection ==1) {
			randomSelection=2;
		}
		wait(6);
		String Object_id = driver.findElement(By.xpath(getConfig("randomGameToSelect") + "/tr["+ randomSelection +"]/td[1]/div[2]/a")).getAttribute("href");
		System.out.println("Randomly Selected Game Name is :" + driver.findElement(By.xpath(getConfig("randomGameToSelect") + "/tr["+ randomSelection +"]/td[1]/div[2]/a")).getText());
		objectId = getObjectId(Object_id);
		driver.findElement(By.xpath(getConfig("randomGameToSelect")+ "/tr["+ randomSelection +"]/td[1]/div[2]/a")).click();
	}


	public static void languageDependenceValidation(String ExpectedValue) {
		String ActuallanguageDependenceDisplayed = driver.findElement(By.xpath(getConfig("languageDependence"))).getText();
		System.out.println("This is the Actual Language dependency being displayed on UI:"+ ActuallanguageDependenceDisplayed);
		Assert.assertEquals(ActuallanguageDependenceDisplayed,ExpectedValue);
	}


	public static void teardown() {
		driver.close();
		driver.quit();
	}
}


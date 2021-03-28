package Homework5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestExplicitWait {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--suppress-message-center-popups");
        driver = new ChromeDriver(options);
        driver.get("https://www.takeaway.com/bg/");
    }

    @Test
    public void explicitlyWait(){
        driver.findElement(By.cssSelector("article>button")).click();
        WebElement search = driver.findElement(By.id("imysearchstring"));
        search.clear();

        search.sendKeys("Авксентийй Велешки 8");

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("iautoCompleteDropDownContent")));

        WebElement dropdown = driver.findElement(By.cssSelector("div#iautoCompleteDropDownContent a:nth-child(6)"));
        dropdown.click();


        wait.until(ExpectedConditions.titleContains("храна за вкъщи в Център"));

        Assert.assertEquals(driver.getTitle(), "Поръчай храна за вкъщи в Център - Takeaway.com");

        driver.findElement(By.cssSelector("div#irestaurantOPR01001 h2 a")).click();
        Assert.assertEquals(driver.getTitle(), "Target Restaurant|Ресторант Таргет Самоков - Храна за вкъщи | Takeaway.com");


    }

//    @AfterMethod
//    public void close(){driver.quit();}
}

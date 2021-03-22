package Homework3;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class LogInTest {
    WebDriver driver;

    @BeforeClass
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://shop.pragmatic.bg/admin/index.php?route=common/login");
    }

    @Test
    public void logIn(){
        WebElement user = driver.findElement(By.id("input-username"));
        user.sendKeys("admin");

        WebElement pass = driver.findElement(By.id("input-password"));
        pass.sendKeys("parola123!");

        WebElement butLogIn = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
        butLogIn.click();

        WebElement element = driver.findElement(By.id("user-profile"));
        String logIn = element.getAttribute("alt");

        Assert.assertEquals(logIn, "Milen Strahinski", "problem with the log in");

    }

    @AfterMethod
    public void close(){
        driver.quit();
    }

}

package Homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

public class TestActionsCheckbox {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://pragmatic.bg/automation/lecture13/Config.html");
    }

    @Test
    public void checkbox(){
        WebElement airbags = driver.findElement(By.name("airbags"));

        Actions checkBoxes = new Actions(driver);
        checkBoxes.click(airbags).perform();

        assertEquals(airbags.getAttribute("name"), "airbags", "Checkbox Air Bags is not selected");

        WebElement parkingSensor = driver.findElement(By.name("parksensor"));

        checkBoxes.click(parkingSensor).perform();

        assertEquals(parkingSensor.getAttribute("name"), "parksensor", "Checkbox Parking Sensor is not selected");

    }

    @AfterMethod
    public void close(){driver.quit();}

}

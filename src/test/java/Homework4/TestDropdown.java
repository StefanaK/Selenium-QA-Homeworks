package Homework4;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class TestDropdown {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://shop.pragmatic.bg/admin/");
    }


    @Test
    public void Dropdown(){
        WebElement user = driver.findElement(By.id("input-username"));
        user.sendKeys("admin");

        WebElement pass = driver.findElement(By.id("input-password"));
        pass.sendKeys("parola123!");

        WebElement button = driver.findElement(By.xpath("//button[@type=\"submit\"]"));
        button.click();

        WebElement admin = driver.findElement(By.id("user-profile"));
        String logIn = admin.getAttribute("alt");

        Assert.assertEquals(logIn, "Milen Strahinski", "problem with the log in");

        WebElement sales = driver.findElement(By.id("menu-sale"));
        sales.click();

        WebElement orders = driver.findElement(By.linkText("Orders"));
        orders.click();


        WebElement element = driver.findElement(By.id("input-order-status"));

        Select check = new Select(element);

        assertFalse(check.isMultiple());
        assertEquals(check.getOptions().size(), 16, "In the drop-down menu there isn't 16 elements");

        List<String> exp_options = new ArrayList<>(Arrays.asList("", "Missing Orders", "Canceled", "Canceled Reversal",
                "Chargeback", "Complete","Denied", "Expired", "Failed", "Pending", "Processed", "Processing",
                "Refunded", "Reversed", "Shipped", "Voided"));

        List<String> act_options = new ArrayList<>();
        List<WebElement> allOptions = check.getOptions();

        for(WebElement fields: allOptions){
            act_options.add(fields.getText());
        }

        assertEquals(exp_options, act_options, "The lists are not equals");

    }

    @AfterMethod
    public  void close(){driver.quit();}
}

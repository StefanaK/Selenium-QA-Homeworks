package Homework6;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class GuestOrderTests {

    WebDriver driver;

    @BeforeMethod
    public void setupBrowser() {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://shop.pragmatic.bg/index.php?route=product/product&product_id=40");

    }

    @Test
    public void successfulPlacementOfGuestOrder() {
        WebElement addToCartButton = driver.findElement(By.id("button-cart"));
        addToCartButton.click();

        WebElement miniShoppingCart = driver.findElement(By.cssSelector("#cart button.btn-block"));
        miniShoppingCart.click();

        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("ul.pull-right div a:nth-of-type(2)")));

        WebElement miniCartCheckoutLink = driver.findElement(By.cssSelector("ul.pull-right div a:nth-of-type(2)"));
        miniCartCheckoutLink.click();


        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.radio:nth-of-type(2) input")));
        WebElement guestRadioButton = driver.findElement(By.cssSelector("div.radio:nth-of-type(2) input"));
        guestRadioButton.click();

        WebElement continueButton = driver.findElement(By.id("button-account"));
        continueButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-guest")));

        driver.findElement(By.id("input-payment-firstname")).sendKeys("Stefana");
        driver.findElement(By.id("input-payment-lastname")).sendKeys("Kazandzhieva");

        String randomEmail = RandomStringUtils.randomAlphanumeric(7) + "@abv.bg";
        driver.findElement(By.id("input-payment-email")).sendKeys(randomEmail);
        driver.findElement(By.id("input-payment-telephone")).sendKeys("0899003967");
        driver.findElement(By.id("input-payment-address-1")).sendKeys("ul. Avks. Veleshki 8");
        driver.findElement(By.id("input-payment-city")).sendKeys("Samokov");
        driver.findElement(By.id("input-payment-postcode")).sendKeys("2000");

        Select countryDropDown = new Select(driver.findElement(By.id("input-payment-country")));
        countryDropDown.selectByValue("33");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("input-payment-zone"), "Yambol"));

        Select regionDropDown = new Select(driver.findElement(By.id("input-payment-zone")));
        regionDropDown.selectByValue("497");

        // ако адреса за фактуриране е различен от този за доставка трябва да отмаркираме бутона, за да отидем на стъпка 3, а не на стъпка 4 направо!!!

        WebElement checkBoxMyDelivery = driver.findElement(By.name("shipping_address"));
        if(checkBoxMyDelivery.isSelected()){checkBoxMyDelivery.click();}
        driver.findElement(By.id("button-guest")).click();

        //TODO: YOUR HOMEWORK STARTS HERE! BELOW!

        //STEP 3: DELIVERY DETAILS

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-guest-shipping")));

        driver.findElement(By.id("input-shipping-firstname")).sendKeys("Stefana");
        driver.findElement(By.id("input-shipping-lastname")).sendKeys("Kazandzhieva");
        driver.findElement(By.id("input-shipping-address-1")).sendKeys("ul Serdika 12");
        driver.findElement(By.id("input-shipping-city")).sendKeys("Sofia");
        driver.findElement(By.id("input-shipping-postcode")).sendKeys("1000");

        Select deliveryCountryDrpDown = new Select(driver.findElement(By.id("input-shipping-country")));
        deliveryCountryDrpDown.selectByValue("33");

        wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("input-shipping-zone"), "Yambol"));

        Select deliveryZoneDrpDown = new Select(driver.findElement(By.id("input-shipping-zone")));
        deliveryZoneDrpDown.selectByValue("498");

        driver.findElement(By.id("button-guest-shipping")).click();

        // STEP 4: DELIVERY METHOD - тук има само една опция, така че може би да се направи проверка дали радиобутона е маркиран?

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-shipping-method")));

        WebElement radioButtonFlat = driver.findElement(By.xpath("//input[@value=\"flat.flat\"]"));
        if ( !radioButtonFlat.isSelected()){
            radioButtonFlat.click();
        }

        driver.findElement(By.id("button-shipping-method")).click();

        // STEP 5: PAYMENT METHOD - тук както на предната стъпка можем да проверим дали радиобутона е маркиран.
        // Според мен бутона "continue" не трябва да е активен докато не бъде маркиран чекбокса "terms & conditions" и можем да направим следното:

        /*wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-payment-method")));

        WebElement buttonContinue = driver.findElement(By.id("button-payment-method"));
        if(!buttonContinue.isEnabled()){
            driver.findElement(By.name("agree")).click();
        } else {
            fail("Button continue is enable without confirmation of terms and conditions");
        }
        buttonContinue.click();*/

        //Ако трябва да пренебрегнем това, за целите на упражнението правим следното:

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-payment-method")));
        driver.findElement(By.name("agree")).click();
        driver.findElement(By.id("button-payment-method")).click();

        //Има и трети вариант да пропуснем checkbox-a, но ще даде грешка и ще ни върне за същата стъпка, за да маркираме checkbox-a

        // STEP 6: CONFIRM ORDER

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("button-confirm"))).click();
        wait.until(ExpectedConditions.urlContains("success"));

        Assert.assertEquals(driver.getCurrentUrl(), "http://shop.pragmatic.bg/index.php?route=checkout/success", "The order is not finished successfully");

    }


    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}

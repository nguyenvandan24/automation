package org.example.user;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Checkout {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Cấu hình WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");
        sleep(2000);
        driver.findElement(By.className("fa-right-to-bracket")).click();
        driver.findElement(By.id("signIn")).click();
        sleep(3000);

        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys("validEmail@example.com");

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("StrongPass@123");

        WebElement signInButton = driver.findElement(By.className("btn-sign-in"));
        signInButton.click();
        sleep(3000);
    }

    @Test
    public void testCheckoutProcess() {
        driver.findElement(By.cssSelector(".listProductHome .item:nth-child(3) img")).click();
        sleep(2000);

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg"));
        addToCartButton.click();
        sleep(2000);

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        //check total
        WebElement totalPriceElement = driver.findElement(By.id("totalPrice"));
        String totalPriceText = totalPriceElement.getText();
        double expectedTotalPrice = parsePrice(totalPriceText);
        Assert.assertTrue(expectedTotalPrice > 0, "Tổng tiền phải lớn hơn 0 trước khi thanh toán!");

        WebElement checkoutButton = driver.findElement(By.cssSelector(".btn-primary"));
        checkoutButton.click();
        sleep(2000);

        WebElement addressField = driver.findElement(By.id("address"));
        addressField.sendKeys("Khu pho 6, Linh Trung");

        WebElement phoneField = driver.findElement(By.id("phone"));
        phoneField.sendKeys("0123456789");

        WebElement payOnDeliveryCheckbox = driver.findElement(By.id("payOnDelivery"));
        payOnDeliveryCheckbox.click();

        WebElement confirmCheckoutButton = driver.findElement(By.cssSelector("button[type='submit']"));
        confirmCheckoutButton.click();
        sleep(3000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertEquals(currentUrl, "http://localhost:3000/", "Không chuyển hướng về trang chủ!");
    }

    private double parsePrice(String priceText) {
        priceText = priceText.replaceAll("[^\\d.]", "");
        return Double.parseDouble(priceText);
    }


    public void sleep(int time){
        try {
            Thread.sleep(time);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @AfterMethod
    public void clear() {
        if (driver != null) {
            driver.quit();
        }
    }
}

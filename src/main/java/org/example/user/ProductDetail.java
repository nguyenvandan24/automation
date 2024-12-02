package org.example.user;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class ProductDetail {
    WebDriver driver;
    @BeforeMethod
    public void setUp() {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");

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
    @Test(priority = 1)
    public void testQuantityButtons() {
        //click to Product Detail
        driver.findElement(By.cssSelector(".listProductHome .item img")).click();
        sleep(2000);

        // Check button
        WebElement quantityInput = driver.findElement(By.cssSelector(".input-group .form-control"));
        WebElement minusButton = driver.findElement(By.id("button-minus"));
        WebElement plusButton = driver.findElement(By.id("button-plus"));

        // Get current value
        int initialQuantity = Integer.parseInt(quantityInput.getAttribute("value"));

        plusButton.click();
        sleep(1000);
        int increasedQuantity = Integer.parseInt(quantityInput.getAttribute("value"));
        Assert.assertEquals(increasedQuantity, initialQuantity + 1, "Số lượng không tăng đúng!");

        minusButton.click();
        sleep(1000);
        int decreasedQuantity = Integer.parseInt(quantityInput.getAttribute("value"));
        Assert.assertEquals(decreasedQuantity, initialQuantity, "Số lượng không giảm đúng!");

        // check 0
        for (int i = 0; i < 5; i++) {
            minusButton.click();
            sleep(500);
        }
        int nonNegativeQuantity = Integer.parseInt(quantityInput.getAttribute("value"));
        Assert.assertTrue(nonNegativeQuantity >= 0, "Số lượng giảm xuống dưới 0!");
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


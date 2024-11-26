package org.example.user;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Cart {


    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Cấu hình WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");
        sleep(2000);
    }

    @Test(priority = 1)
    public void testAddToCartWithoutLogin() {
        // Locate the product on the homepage
        WebElement product = driver.findElement(By.cssSelector(".listProductHome .item:first-child"));
        WebElement addToCartButton = product.findElement(By.cssSelector("button"));

        // Try to add product to the cart
        addToCartButton.click();
        sleep(1000); // wait for the action to complete

        // Verify if the user is redirected to the login page
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"), "User should be redirected to login page when not logged in.");
    }



    public void sleep(int time){
        try {
            Thread.sleep(time);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    @AfterClass
    public void clear() {
        if (driver != null) {
            driver.quit();
        }
    }
}

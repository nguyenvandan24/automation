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
        WebElement product = driver.findElement(By.cssSelector(".listProductHome .item:first-child"));
        WebElement addToCartButton = product.findElement(By.cssSelector("button"));

        addToCartButton.click();
        sleep(1000);

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/login"), "Người dùng không thể thêm sản phẩm nếu chưa đăng nhập.");
    }

    @Test(priority = 2)
    public void testAddProductToCartAfterLogin() {
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

        WebElement product = driver.findElement(By.cssSelector(".listProductHome .item:first-child"));
        WebElement addToCartButton = product.findElement(By.cssSelector("button"));

        addToCartButton.click();
        sleep(1000);

        String productName = product.findElement(By.id("namePro")).getText();
        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(1000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        boolean isProductInCart = cartTable.getText().contains(productName);
        Assert.assertTrue(isProductInCart, "Sản phẩm thêm vào giỏ hàng không thành công.");
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

package org.example.user;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;

public class Products {
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
    public void testProductDisplay() {
        driver.findElement(By.id("product")).click();
        sleep(2000);

        // Get list products
        List<WebElement> displayedProducts = driver.findElements(By.cssSelector(".listProductHome .item"));
        Assert.assertTrue(displayedProducts.size() > 0, "No products are displayed!");

        for (WebElement product : displayedProducts) {
            WebElement productName = product.findElement(By.tagName("h1"));
            WebElement productPrice = product.findElement(By.className("price"));

            Assert.assertFalse(productName.getText().isEmpty(), "Product name is empty!");
            Assert.assertTrue(productPrice.getText().matches("\\d+.*"), "Invalid price format!");
        }
    }

    @Test(priority = 2)
    public void testPagination() {
        List<WebElement> initialPageProducts = driver.findElements(By.cssSelector(".listProductHome .item"));
        Assert.assertEquals(initialPageProducts.size(), 8, "Incorrect number of products displayed on the first page!");

        // click next
        WebElement nextButton = driver.findElement(By.cssSelector(".ant-pagination-next"));
        Assert.assertTrue(nextButton.isDisplayed(), "Next button is not displayed!");
        nextButton.click();
        sleep(2000);

        // Check products on 2nd page
        List<WebElement> secondPageProducts = driver.findElements(By.cssSelector(".listProductHome .item"));
        Assert.assertEquals(secondPageProducts.size(), 8, "Incorrect number of products displayed on the second page!");
    }


    @Test (priority = 3)
    public void testNavigation() {
        List<WebElement> products = driver.findElements(By.cssSelector(".listProductHome .item img"));
        Assert.assertTrue(products.size() > 0, "Không tìm thấy sản phẩm nào trên trang!");

        // get 1st product and save current URL to compare
        WebElement firstProduct = products.get(0);
        String currentUrl = driver.getCurrentUrl();

        // Click 1st pro
        firstProduct.click();
        sleep(2000);

        // Check URL
        String newUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, newUrl, "URL không thay đổi khi click vào sản phẩm!");
        Assert.assertTrue(newUrl.contains("/product-detail/"), "Không chuyển đến trang chi tiết sản phẩm!");
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

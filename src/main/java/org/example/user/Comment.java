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

import java.util.List;

public class Comment {
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
    public void testCommentWithoutLogin() {
        List<WebElement> products = driver.findElements(By.cssSelector(".listProductHome .item img"));
        Assert.assertTrue(products.size() > 0, "Không tìm thấy sản phẩm nào trên trang!");

        // get 1st product and save current URL to compare
        WebElement firstProduct = products.get(0);

        // Click 1st pro
        firstProduct.click();
        sleep(2000);

        List<WebElement> starRatings = driver.findElements(By.className("fa-star"));
        Assert.assertTrue(starRatings.size() > 0, "Không tìm thấy các ngôi sao để đánh giá!");
        starRatings.get(3).click();
        sleep(1000);

        WebElement commentBox = driver.findElement(By.cssSelector("textarea.form-control"));
        commentBox.click();

        commentBox.sendKeys("Sản phẩm tốt.");

        WebElement commentButton = driver.findElement(By.cssSelector("button.btnCmt"));
        commentButton.click();

        sleep(1000);

        // Check message
        WebElement alert = driver.findElement(By.className("swal2-title"));
        Assert.assertEquals(alert.getText(), "Bạn chỉ được comment khi đã mua sản phẩm");
    }

    @Test(priority = 2)
    public void testCommentWithoutPurchase() {
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

        List<WebElement> products = driver.findElements(By.cssSelector(".listProductHome .item img"));
        Assert.assertTrue(products.size() > 0, "Không tìm thấy sản phẩm nào trên trang!");

        // get 1st product and save current URL to compare
        WebElement firstProduct = products.get(0);

        // Click 1st pro
        firstProduct.click();
        sleep(2000);

        List<WebElement> starRatings = driver.findElements(By.className("fa-star"));
        Assert.assertTrue(starRatings.size() > 0, "Không tìm thấy các ngôi sao để đánh giá!");
        starRatings.get(3).click();
        sleep(1000);

        WebElement commentBox = driver.findElement(By.cssSelector("textarea.form-control"));
        commentBox.click();

        commentBox.sendKeys("Comment khi login nhưng chưa mua");

        WebElement commentButton = driver.findElement(By.cssSelector("button.btnCmt"));
        commentButton.click();

        sleep(1000);

        WebElement alert = driver.findElement(By.className("swal2-title"));
        Assert.assertEquals(alert.getText(), "Bạn chỉ được comment khi đã mua sản phẩm");
    }

    @Test(priority = 3)
    public void testCommentAfterPurchase() {
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

        List<WebElement> products = driver.findElements(By.cssSelector(".listProductHome .item img"));
        Assert.assertTrue(products.size() > 0, "Không tìm thấy sản phẩm nào trên trang!");

        // get 1st product and save current URL to compare
        WebElement firstProduct = products.get(0);

        // Click 1st pro
        firstProduct.click();
        sleep(2000);

        List<WebElement> starRatings = driver.findElements(By.className("fa-star"));
        Assert.assertTrue(starRatings.size() > 0, "Không tìm thấy các ngôi sao để đánh giá!");
        starRatings.get(3).click();
        sleep(1000);

        WebElement commentBox = driver.findElement(By.cssSelector("textarea.form-control"));
        commentBox.click();

        String testComment = "Sản phẩm ok";
        commentBox.sendKeys(testComment);

        WebElement commentButton = driver.findElement(By.cssSelector("button.btnCmt"));
        commentButton.click();

        sleep(1000);

        // Kiểm tra nếu bình luận được hiển thị trong danh sách bình luận
        List<WebElement> comments = driver.findElements(By.id("comment")); // Lấy tất cả các bình luận
        boolean commentFound = false;
        for (WebElement comment : comments) {
            if (comment.getText().contains(testComment)) {
                commentFound = true;
                break;
            }
        }
        Assert.assertTrue(commentFound, "Bình luận không xuất hiện trong danh sách bình luận!");
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

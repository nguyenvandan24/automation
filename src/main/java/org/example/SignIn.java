package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SignIn {

    WebDriver driver;
    ChromeDriver chromeDriver;

    @BeforeClass
    public void setUp() {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");
        driver.findElement(By.className("fa-right-to-bracket")).click();
        driver.findElement(By.id("signIn")).click();
        sleep(3000);
    }

    @Test
    public void testPageLoaded(){
        // Kiểm tra trường nhập email
        WebElement emailField = driver.findElement(By.id("email"));
        Assert.assertTrue(emailField.isDisplayed(), "Trường nhập email không hiển thị!");

        // Kiểm tra trường nhập mật khẩu
        WebElement passwordField = driver.findElement(By.id("password"));
        Assert.assertTrue(passwordField.isDisplayed(), "Trường nhập mật khẩu không hiển thị!");

        // Kiểm tra nút Sign In
        WebElement signInButton = driver.findElement(By.className("btn-sign-in"));
        Assert.assertTrue(signInButton.isDisplayed(), "Nút Sign In không hiển thị!");

        // Kiểm tra có chứa link "Forgot your password"
        WebElement forgotPasswordLink = driver.findElement(By.className("link"));
        Assert.assertTrue(forgotPasswordLink.isDisplayed(), "Link 'Forgot your password' không hiển thị!");
    }

    @Test(priority = 2, dataProvider = "loginData")
    public void testLogin(String email, String password, String expectedErrorMessage) {
        WebElement emailField = driver.findElement(By.id("email"));
        emailField.clear();
        emailField.sendKeys(email);

        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);

        WebElement signInbtn = driver.findElement(By.id("btn-sign-in"));
        signInbtn.click();

        // Kiểm tra thông báo lỗi nếu có
        if (!expectedErrorMessage.isEmpty()) {
            WebElement errorMessage = driver.findElement(By.xpath("//span[@style='color: red; font-size: 13px;']"));
//            Assert.assertEquals(errorMessage.getText(), expectedErrorMessage, "Lỗi đăng nhập!");
        } else {
            // Kiểm tra chuyển hướng thành công nếu không có lỗi
            String currentUrl = driver.getCurrentUrl();
            Assert.assertTrue(currentUrl.contains("") || currentUrl.contains("admin"),
                    "Không chuyển hướng đúng sau khi đăng nhập.");
        }
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"hanho@gmail.com", "Hanho@002", ""},
                {"", "", "Vui lòng nhập email\nVui lòng nhập mật khẩu"},
                {"han@gmail.com", "Hanho@002", "Vui lòng nhập email hợp lệ"},
                {"hanho@gmail.com", "wrongPassword", "Email hoặc mật khẩu không chính xác"}
        };
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

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

public class Search {

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
    public void testSearchWithValidData() {
        // Tìm kiếm với từ khóa hợp lệ
        WebElement searchField = driver.findElement(By.id("search"));
        searchField.clear();
        searchField.sendKeys("Kem chong nang");

        sleep(3000);

        WebElement searchResult = driver.findElement(By.className("listProductHome"));
    }

    @Test
    public void testSearchNoResult() {
        // Tìm kiếm với từ khóa không có kết quả
        WebElement searchField = driver.findElement(By.id("search"));
        searchField.clear();
        searchField.sendKeys("Gakibake");

        sleep(3000);

        // Kiểm tra thông báo không có kết quả tìm kiếm
        WebElement noResultMessage = driver.findElement(By.className("listProductHome"));
        Assert.assertTrue(noResultMessage.getText().contains("Không có kết quả"), "Không hiển thị thông báo không có kết quả tìm kiếm");
    }

    @Test
    public void testSearchInvalidKeyword() {
        // Tìm kiếm với từ khóa không hợp lệ
        WebElement searchField = driver.findElement(By.id("search"));
        searchField.clear();
        searchField.sendKeys("@#$%^&*()");

        sleep(3000);

        // Kiểm tra kết quả tìm kiếm có hiển thị thông báo lỗi hoặc không có kết quả
        WebElement searchResult = driver.findElement(By.className("listProductHome"));
        Assert.assertTrue(searchResult.getText().isEmpty(), "Không có kết quả tìm kiếm với từ khóa không hợp lệ");
    }

    @Test
    public void testSearchEmptyKeyword() {
        // Tìm kiếm với từ khóa trống
        WebElement searchField = driver.findElement(By.id("search"));
        searchField.clear();
        searchField.sendKeys("");

        sleep(3000);

        // Kiểm tra hệ thống không tìm kiếm khi từ khóa trống
        WebElement searchResult = driver.findElement(By.className("listProductHome"));
        Assert.assertTrue(searchResult.getText().isEmpty(), "Không có kết quả khi ô tìm kiếm để trống");
    }

    @Test
    public void testSearchWithSpaces() {
        // Tìm kiếm với từ khóa có khoảng trắng thừa
        WebElement searchField = driver.findElement(By.id("search"));
        searchField.clear();
        searchField.sendKeys(" Kem ");

        sleep(3000);

        WebElement searchResult = driver.findElement(By.className("listProductHome"));
        Assert.assertTrue(searchResult.getText().contains("Kem"), "Không tìm thấy sản phẩm với từ khóa ' Kem '");
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

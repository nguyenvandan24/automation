package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class SignUp {

    WebDriver driver;

    @BeforeMethod
    public void setUp() {
        // Set up WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");
        driver.findElement(By.className("fa-right-to-bracket")).click();
        driver.findElement(By.id("signUp"));
        sleep(1000);
    }

    @Test
    public void testInvalidData() {
        // Fill invalid data
        driver.findElement(By.name("fullName")).sendKeys("Nguyễn Ha Tuan"); // Invalid name
        driver.findElement(By.name("email")).sendKeys("nguyenhatuangmail.com"); // Invalid email
        driver.findElement(By.name("address")).sendKeys("");
        driver.findElement(By.name("phone")).sendKeys("0398"); // Invalid phone
        driver.findElement(By.name("password")).sendKeys("Hatuan002"); // Invalid password

        driver.findElement(By.id("btn-signUp")).click();
        sleep(1000);

        // Verify error messages
        Assert.assertTrue(driver.getPageSource().contains("Full Name must consist only of alphabetic characters"));
        Assert.assertTrue(driver.getPageSource().contains("Invalid email"));
        Assert.assertTrue(driver.getPageSource().contains("Address is required"));
        Assert.assertTrue(driver.getPageSource().contains("The phone number consists of 10 numeric characters"));
        Assert.assertTrue(driver.getPageSource().contains("The password must be 8-16 characters long, including uppercase letters, lowercase letters, numbers, and special characters."));
    }

    @Test
    public void testValidData() {
        // Clear and fill in valid data
        driver.findElement(By.name("fullName")).clear();
        driver.findElement(By.name("email")).clear();
        driver.findElement(By.name("address")).clear();
        driver.findElement(By.name("phone")).clear();
        driver.findElement(By.name("password")).clear();

        driver.findElement(By.name("fullName")).sendKeys("Nguyen Van Dan");
        driver.findElement(By.name("email")).sendKeys("validemail11@example.com");
        driver.findElement(By.name("address")).sendKeys("123 Street, City");
        driver.findElement(By.name("phone")).sendKeys("0123456789");
        driver.findElement(By.name("password")).sendKeys("StrongPass@123");

        // Click Sign Up button
        driver.findElement(By.id("btn-signUp")).click();
        sleep(1000);

        // Verify success message
        WebElement successMessage = driver.findElement(By.id("success-message"));
        Assert.assertEquals(successMessage.getText(), "Dang ky thanh cong");
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

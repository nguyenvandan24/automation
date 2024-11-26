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

public class Category {

    WebDriver driver;

    @BeforeClass
    public void setUp() {
        // Cấu hình WebDriver
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000");

        // Đăng nhập
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
    public void testCategoryTabsExist() {
        //Check title
        WebElement categoryTitle = driver.findElement(By.cssSelector(".sectionProductCate h2"));
        Assert.assertEquals(categoryTitle.getText(), "Danh mục", "Tiêu đề danh mục không đúng!");

        // check display
        List<WebElement> categoryTabs = driver.findElements(By.cssSelector(".ant-tabs-tab"));
        Assert.assertTrue(categoryTabs.size() > 0, "Không có tab danh mục nào được hiển thị!");

        for (WebElement tab : categoryTabs) {
            String tabName = tab.getText();
            Assert.assertFalse(tabName.isEmpty(), "Tên tab danh mục bị trống!");
        }
    }
    @Test(priority = 2)
    public void testCategoryContent() {
        List<WebElement> categoryTabs = driver.findElements(By.cssSelector(".ant-tabs-tab"));

        for (int i = 0; i < categoryTabs.size(); i++) {
            WebElement tab = categoryTabs.get(i);

            tab.click();
            sleep(2000);

            WebElement productList = driver.findElement(By.cssSelector(".listProductHome3"));
            List<WebElement> products = productList.findElements(By.id("category"));

            if (products.size() > 0) {
                for (WebElement product : products) {
                    Assert.assertTrue(
                            product.isDisplayed(),
                            "Sản phẩm không hiển thị đúng!"
                    );

                    WebElement name = product.findElement(By.id("namePro"));
                    WebElement price = product.findElement(By.id("price"));

                    Assert.assertNotNull(
                            name.getText(),
                            "Tên sản phẩm không hợp lệ!"
                    );
                    Assert.assertTrue(
                            price.getText().contains("vnd"),
                            "Giá sản phẩm không đúng định dạng!"
                    );
                }
            } else {
                WebElement message = driver.findElement(By.cssSelector(".listProductHome3 p"));
                Assert.assertEquals(
                        message.getText(),
                        "Sản phẩm sẽ sớm được cập nhật",
                        "Thông báo không đúng khi không có sản phẩm!"
                );
            }
        }
    }

    @Test(priority = 3)
    public void testProductNavigation() {
        //get list
        List<WebElement> categoryTabs = driver.findElements(By.cssSelector(".ant-tabs-tab"));
        Assert.assertTrue(categoryTabs.size() > 0, "Không có tab danh mục nào được hiển thị!");

        // click 1st tab
        categoryTabs.get(0).click();
        sleep(2000);

        // Get 1st Pro
        List<WebElement> productItems = driver.findElements(By.cssSelector(".listProductHome3 .item img"));
        Assert.assertTrue(productItems.size() > 0, "Không có sản phẩm nào được hiển thị trong danh mục!");

        // Click 1st Pro
        WebElement firstProduct = productItems.get(0);
        String currentUrl = driver.getCurrentUrl();
        firstProduct.click();
        sleep(2000);

        //Check URL
        String newUrl = driver.getCurrentUrl();
        Assert.assertNotEquals(currentUrl, newUrl, "URL không thay đổi khi nhấn vào sản phẩm!");
        Assert.assertTrue(newUrl.contains("/product-detail/"), "Không chuyển đến trang chi tiết sản phẩm!");
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

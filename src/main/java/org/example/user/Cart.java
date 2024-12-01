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
    public void testAddToCartFromCategory() {
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

        WebElement categoryTab = driver.findElement(By.cssSelector(".sectionProductCate .listProductCateHome1 .ant-tabs-tab:first-child"));
        categoryTab.click();
        sleep(3000);

        //Get 1st pro
        WebElement categoryProduct = driver.findElement(By.cssSelector(".listProductHome3 .item:first-child"));
        WebElement addToCartButton = categoryProduct.findElement(By.cssSelector("button"));

        addToCartButton.click();
        sleep(1000);

        //Get namePro
        String productName = categoryProduct.findElement(By.id("namePro")).getText();

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        boolean isProductInCart = cartTable.getText().contains(productName);
        Assert.assertTrue(isProductInCart, "Sản phẩm từ danh mục không được thêm vào giỏ hàng.");
    }

    @Test(priority = 3)
    public void testAddToCartFromProductSection() {
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

        WebElement productSection = driver.findElement(By.cssSelector(".sectionProduct"));
        Assert.assertTrue(productSection.isDisplayed(), "Phần sản phẩm không hiển thị.");

        WebElement product = driver.findElement(By.cssSelector(".listProductHome .item:nth-child(2)"));
        WebElement addToCartButton = product.findElement(By.cssSelector("button"));

        addToCartButton.click();
        sleep(3000);

        // Get namePro
        String productName = product.findElement(By.id("namePro")).getText();

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        boolean isProductInCart = cartTable.getText().contains(productName);
        Assert.assertTrue(isProductInCart, "Sản phẩm từ phần sản phẩm không được thêm vào giỏ hàng.");
    }

    @Test(priority = 4)
    public void testAddProductToCartFromProDetail() {
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

        WebElement product = driver.findElement(By.cssSelector(".listProductHome .item:nth-child(3)"));
        WebElement addToCartButton = product.findElement(By.cssSelector("button"));

        addToCartButton.click();
        sleep(1000);

        String productName = product.findElement(By.id("namePro")).getText();
        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        boolean isProductInCart = cartTable.getText().contains(productName);
        Assert.assertTrue(isProductInCart, "Sản phẩm thêm vào giỏ hàng không thành công.");
    }

    @Test(priority = 5)
    public void testAddMultipleQuantitiesToCartFromProductDetail() {
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

        driver.findElement(By.cssSelector(".listProductHome .item:nth-child(5) img")).click();
        sleep(2000);

        WebElement productDetailPage = driver.findElement(By.cssSelector(".product"));
        Assert.assertTrue(productDetailPage.isDisplayed(), "Trang chi tiết sản phẩm không hiển thị.");

        //+1
        WebElement increaseButton = driver.findElement(By.id("button-plus"));
        increaseButton.click();
        sleep(1000);

        //+1
        increaseButton.click();
        sleep(1000);

        //Check quantities
        WebElement quantityInput = driver.findElement(By.cssSelector(".input-group input"));
        String quantityValue = quantityInput.getAttribute("value");
        Assert.assertEquals(quantityValue, "3", "Số lượng sản phẩm không tăng đúng.");

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg"));
        addToCartButton.click();
        sleep(2000);

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        boolean isProductInCart = cartTable.getText().contains("3");
        Assert.assertTrue(isProductInCart, "Sản phẩm với số lượng mong muốn không được thêm vào giỏ hàng.");
    }

    //Cart page
    @Test(priority = 6)
    public void testChangeQuantityInCart() {
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

        driver.findElement(By.cssSelector(".listProductHome .item:nth-child(3) img")).click();
        sleep(2000);

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg"));
        addToCartButton.click();
        sleep(2000);

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        Assert.assertTrue(cartTable.isDisplayed(), "Trang giỏ hàng không hiển thị.");

        WebElement quantitySpan = driver.findElement(By.id("quantity"));
        int initialQuantity = Integer.parseInt(quantitySpan.getText());

        WebElement increaseButton = driver.findElement(By.id("plus"));
        increaseButton.click();
        sleep(1000);

        int increasedQuantity = Integer.parseInt(quantitySpan.getText());
        Assert.assertEquals(increasedQuantity, initialQuantity + 1, "Số lượng sản phẩm không tăng đúng.");

        WebElement decreaseButton = driver.findElement(By.id("minus"));
        decreaseButton.click();
        sleep(1000);

        int decreasedQuantity = Integer.parseInt(quantitySpan.getText());
        Assert.assertEquals(decreasedQuantity, initialQuantity, "Số lượng sản phẩm không giảm đúng.");
    }
    @Test(priority = 7)
    public void testChangeQuantityInCartToZero() {
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

        driver.findElement(By.cssSelector(".listProductHome .item:nth-child(3) img")).click();
        sleep(2000);

        WebElement addToCartButton = driver.findElement(By.cssSelector(".btn.btn-primary.btn-lg"));
        addToCartButton.click();
        sleep(2000);

        driver.findElement(By.className("fa-cart-shopping")).click();
        sleep(3000);

        WebElement cartTable = driver.findElement(By.cssSelector(".cart-list table tbody"));
        Assert.assertTrue(cartTable.isDisplayed(), "Trang giỏ hàng không hiển thị.");

        WebElement quantitySpan = driver.findElement(By.id("quantity"));
        int initialQuantity = Integer.parseInt(quantitySpan.getText());

        WebElement increaseButton = driver.findElement(By.id("plus"));
        increaseButton.click();
        sleep(1000);

        int increasedQuantity = Integer.parseInt(quantitySpan.getText());
        Assert.assertEquals(increasedQuantity, initialQuantity + 1, "Số lượng sản phẩm không tăng đúng.");

        WebElement decreaseButton = driver.findElement(By.id("minus"));
        decreaseButton.click();
        sleep(1000);

        int decreasedQuantity = Integer.parseInt(quantitySpan.getText());
        Assert.assertEquals(decreasedQuantity, initialQuantity, "Số lượng sản phẩm không giảm đúng.");

        //Check 0
        int minimumQuantity = 1;
        for (int i = 0; i < 3; i++) {
            decreaseButton.click();
            sleep(1000);

            int currentQuantity = Integer.parseInt(quantitySpan.getText());
            Assert.assertTrue(currentQuantity >= minimumQuantity, "Số lượng sản phẩm giảm dưới mức tối thiểu.");
        }
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

package tests;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import org.testng.Assert;
import org.testng.ITestResult;

import com.aventstack.extentreports.*;
import pages.*;
import utils.ExcelUtils;
import utils.ExtentManager;

public class Test001_SauceDemo {
    WebDriver driver;
    ExtentReports extent;
    ExtentTest test;
    String excelPath = "testdata/credentials.xlsx";
    public static void deleteFolder(Path path) throws IOException {
        if (Files.exists(path)) {
            Files.walk(path)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }
    @BeforeSuite
    public void setUp() throws IOException {
    	ChromeOptions options = new ChromeOptions();
   
    	Map<String, Object> chromePrefs = new HashMap<>();
    	//Disable the credentials enable service (prevents saving passwords)
        //chromePrefs.put("credentials_enable_service", false);
    	//Disable the password manager (prevents prompting to save/change passwords)
        //chromePrefs.put("profile.password_manager_enabled", false);
    	
    	// Disable password leak detection (prevents "Change your password" warnings)
        chromePrefs.put("profile.password_manager_leak_detection", false);
   
        options.setExperimentalOption("prefs", chromePrefs);
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        extent = ExtentManager.getInstance();
        driver.get("https://www.saucedemo.com/");
    }

    @Test(priority = 1)
    public void validateTitleAndURL() {
        test = extent.createTest("Validate Title and URL");
        Assert.assertEquals(driver.getTitle(), "Swag Labs", "Title mismatch");
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo"), "URL mismatch");
        test.pass("Title and URL validated successfully.");
    }

    @Test(priority = 2)
    public void loginAndCheckout() {
        test = extent.createTest("Login and Checkout");

        String username = ExcelUtils.getCellData(excelPath, "Sheet1", 1, 0);
        String password = ExcelUtils.getCellData(excelPath, "Sheet1", 1, 1);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);
        test.info("Logged in as " + username);
      
        ProductPage productPage = new ProductPage(driver);
        productPage.filterByNameDesc();
        Assert.assertTrue(productPage.isSortedDescending(), "Products not sorted correctly");
        test.pass("Products sorted Z-A successfully");

        productPage.addFirstProductToCart();
        test.pass("Product added to cart successfully");
        productPage.goToCart();
       
        CartPage cart = new CartPage(driver);
        Assert.assertTrue(cart.verifyCartItemPresent(), "Cart is empty");
        cart.checkout();
        test.pass("Product added and moved to checkout page");

        CheckoutPage checkout = new CheckoutPage(driver);
        checkout.enterDetails("John", "Doe", "600001");
        checkout.continueCheckout();

        ConfirmationPage confirm = new ConfirmationPage(driver);
        confirm.finishCheckout();
        Assert.assertTrue(confirm.getThankYouMsg().contains("Thank you for your order!"), "Order message mismatch");
        test.pass("Order completed successfully!");
    }

    @AfterMethod
    public void captureFailure(org.testng.ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            String path = ExtentManager.captureScreenshot(driver, result.getName());
            test.fail("Test failed: " + result.getThrowable());
            test.addScreenCaptureFromPath(path);
        }
    }

    @AfterSuite
    public void tearDown() {
       // driver.quit();
        extent.flush();
    }
}

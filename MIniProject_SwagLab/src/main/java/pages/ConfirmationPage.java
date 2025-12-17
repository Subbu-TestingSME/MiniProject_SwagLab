package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class ConfirmationPage {
    WebDriver driver;

    @FindBy(id = "finish")
    WebElement finishButton;

    @FindBy(className = "complete-header")
    WebElement thankYouMsg;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void finishCheckout() {
        finishButton.click();
    }

    public String getThankYouMsg() {
        return thankYouMsg.getText();
    }
}

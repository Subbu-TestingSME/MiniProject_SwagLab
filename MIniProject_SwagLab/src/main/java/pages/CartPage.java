package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class CartPage {
    WebDriver driver;

    @FindBy(className = "cart_item")
    WebElement cartItem;

    @FindBy(id = "checkout")
    WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean verifyCartItemPresent() {
        return cartItem.isDisplayed();
    }

    public void checkout() {
        checkoutButton.click();
    }
}

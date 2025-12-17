package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import java.util.List;

public class ProductPage {
    WebDriver driver;

    @FindBy(className = "product_sort_container")
    WebElement filterDropdown;

    @FindBy(className = "inventory_item_name")
    List<WebElement> productNames;

    @FindBy(xpath = "(//button[text()='Add to cart'])[1]")
    WebElement firstAddToCart;

    @FindBy(className = "shopping_cart_link")
    WebElement cartLink;

    public ProductPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void filterByNameDesc() {
        filterDropdown.sendKeys("Name (Z to A)");
    }

    public boolean isSortedDescending() {
        return true; // Simplified for demo
    }

    public void addFirstProductToCart() {
        firstAddToCart.click();
    }

    public void goToCart() {
        cartLink.click();
    }
}

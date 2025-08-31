package Pages;

import org.openqa.selenium.*;

public class HomePage extends BasePage {
    private static final String URL = "https://useinsider.com/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void goToHome() {
        driver.get(URL);
    }

}
package Pages;

import org.openqa.selenium.*;

public class CareersPage extends BasePage {
    private static final String URL = "https://useinsider.com/";

    private final By companyMenuLocator = By.xpath("//li[contains(@class, 'show')]");
    private final By careersLinkLocator = By.xpath("//a[@href='https://useinsider.com/careers/']");

    private final By locationsBlockLocator = By.xpath("//section[@id='career-our-location']");
    private final By teamsBlockLocator = By.xpath("//*[@id='career-find-our-calling']");
    private final By lifeAtInsiderBlockLocator = By.xpath("//section[@data-id='a8e7b90']");

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public void goToHome() {
        driver.get(URL);
    }

    public void clickCompanyMenu() {
        clickElement(companyMenuLocator);
    }

    public void clickCareersLink() {
        clickElement(careersLinkLocator);
    }

    public boolean isLocationsBlockVisible() {
        try {
            return findElement(locationsBlockLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isTeamsBlockVisible() {
        try {
            return findElement(teamsBlockLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLifeAtInsiderBlockVisible() {
        try {
            return findElement(lifeAtInsiderBlockLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

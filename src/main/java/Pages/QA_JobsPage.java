package Pages;

import java.util.List;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class QA_JobsPage extends BasePage {
    private static final String URL = "https://useinsider.com/careers/quality-assurance/";

    private final By acceptCookieButton = By.xpath("//*[@id=\"wt-cli-accept-all-btn\"]");
    private final By seeAllQaJobsButton = By.className("btn-outline-secondary");

    private final By locationSelectionDropdownMenuLocator = By.xpath("//*[@id=\"top-filter-form\"]/div[1]/span/span[1]/span/span[2]");
    private final By departmentSelectionDropdownMenuLocator = By.xpath("//*[@id=\"top-filter-form\"]/div[2]/span/span[1]/span/span[2]");

    private final By jobsListLocator = By.xpath("//*[@id=\"career-position-list\"]");

    private final By viewRoleButton = By.xpath("//a[text()='View Role']");

    public QA_JobsPage(WebDriver driver) {
        super(driver);
    }

    public void goToHome() {
        driver.get(URL);
    }

    public boolean isJobListVisible() {
        try {
            return findElement(jobsListLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void acceptCookieButton() {
        clickElement(acceptCookieButton);

    }

    public void clickSeeAllQaJobs() {
        clickElement(seeAllQaJobsButton);
    }

    public void clickLocationSelectionDropdownMenu() {
        clickElement(locationSelectionDropdownMenuLocator);

    }

    public void clickDepartmentSelectionDropdownMenu() {
        clickElement(departmentSelectionDropdownMenuLocator);
    }

    public void scroll() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0, 250);");
    }

    public void filterByLocation() {
        By targetLocationLocator = By.xpath("//ul[@id='select2-filter-by-location-results']/li[contains(text(),'Istanbul, Turkiye')]");

        wait.until(ExpectedConditions.visibilityOfElementLocated(targetLocationLocator));
        wait.until(ExpectedConditions.elementToBeClickable(targetLocationLocator));

        clickElement(targetLocationLocator);
    }

    public void filterByDepartment() {
        By selectOptionsContainer = By.className("select2-results__option");
        wait.until(ExpectedConditions.visibilityOfElementLocated(selectOptionsContainer));
        By targetDepartmentLocator = By.xpath("//li[text()='Quality Assurance']");

        clickElement(targetDepartmentLocator);
    }

    public List<WebElement> getJobsList() {
        return driver.findElements(By.className("position-list-item"));
    }

    public WebElement getViewRoleButton() {
        return findElement(viewRoleButton);
    }
}
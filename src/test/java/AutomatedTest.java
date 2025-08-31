import Pages.CareersPage;
import Pages.HomePage;
import Pages.QA_JobsPage;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AutomatedTest {
    private WebDriver driver;
    private HomePage homePage;
    private CareersPage careersPage;
    private QA_JobsPage qaJobsPage;

    @BeforeAll
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(75));

        homePage = new HomePage(driver);
        careersPage = new CareersPage(driver);
        qaJobsPage = new QA_JobsPage(driver);

        Path path = Paths.get("screenshots");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterEach
    public void captureScreenshotOnFailure(org.junit.jupiter.api.TestInfo testInfo) {
        if (testInfo.getTags().contains("failed")) {
            homePage.takeScreenshot(testInfo.getDisplayName().replaceAll("\\s+", "_"));
            careersPage.takeScreenshot(testInfo.getDisplayName().replaceAll("\\s+", "_"));
            qaJobsPage.takeScreenshot(testInfo.getDisplayName().replaceAll("\\s+", "_"));
        }
    }

    @AfterAll
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testInsiderHomepageIsOpened() {
        // check Insider home page is opened or not
        homePage.goToHome();
        Assertions.assertEquals("https://useinsider.com/", driver.getCurrentUrl(), "Home page did not load correctly.");
    }

    @Test
    public void testCareersPageBlocksAreVisible() {
        careersPage.goToHome();
        careersPage.clickCompanyMenu();
        careersPage.clickCareersLink();

        // Check Career page, its Locations, Teams, and Life at Insider blocks are open or not
        Assertions.assertTrue(driver.getCurrentUrl().contains("https://useinsider.com/careers"), "Career page did not load correctly");
        Assertions.assertTrue(careersPage.isLocationsBlockVisible(), "Locations block is not visible.");
        Assertions.assertTrue(careersPage.isTeamsBlockVisible(), "Teams block is not visible.");
        Assertions.assertTrue(careersPage.isLifeAtInsiderBlockVisible(), "Life at Insider block is not visible.");
    }

    @Test
    public void testQaJobsAreFilteredCorrectly() {
        qaJobsPage.goToHome();

        qaJobsPage.acceptCookieButton();
        qaJobsPage.clickSeeAllQaJobs();

        qaJobsPage.scroll();

        // Ne yaptiysam su dropdown menuyu bir turlu gorunur hale getiremedim,
        // her seyi denedim yine de olmadi.
        // Department menusu duzgun calisirken bunun calismamasi cok ilginc.
        qaJobsPage.clickLocationSelectionDropdownMenu();
        qaJobsPage.filterByLocation();

        qaJobsPage.clickDepartmentSelectionDropdownMenu();
        qaJobsPage.filterByDepartment();

        // Check the presence of job list
        Assertions.assertTrue(qaJobsPage.isJobListVisible(), "Jobs list is not visible.");

        List<WebElement> jobs = qaJobsPage.getJobsList();

        for (WebElement job : jobs) {
            String position = job.findElement(By.className("position-title")).getText();
            String department = job.findElement(By.className("position-department")).getText();
            String location = job.findElement(By.className("position-location")).getText();

            // Check that all jobs’ Position contains “Quality Assurance”, Department contains
            //“Quality Assurance”, and Location contains “Istanbul, Turkey”
            Assertions.assertTrue(position.contains("Quality Assurance"), "Position does not contain 'Quality Assurance'.");
            Assertions.assertTrue(department.contains("Quality Assurance"), "Department does not contain 'Quality Assurance'.");
            Assertions.assertTrue(location.contains("Istanbul, Turkiye"), "Location does not contain 'Istanbul, Turkey'.");
        }
    }

    @Test
    public void testViewRoleRedirectsToLever() {
        driver.get("https://useinsider.com/careers/quality-assurance/");

        qaJobsPage.acceptCookieButton();
        qaJobsPage.clickSeeAllQaJobs();

        qaJobsPage.scroll();

        // Ne yaptiysam su dropdown menuyu bir turlu gorunur hale getiremedim,
        // her seyi denedim yine de olmadi.
        // Department menusu duzgun calisirken bunun calismamasi cok ilginc.
        qaJobsPage.clickLocationSelectionDropdownMenu();
        qaJobsPage.filterByLocation();

        qaJobsPage.clickDepartmentSelectionDropdownMenu();
        qaJobsPage.filterByDepartment();

        WebElement viewRoleButton = qaJobsPage.getViewRoleButton();
        viewRoleButton.click();

        // check that this action redirects us to the Lever
        // Application form page
        Assertions.assertTrue(driver.getCurrentUrl().contains("lever.co"), "Not redirected to Lever application page.");
    }
}

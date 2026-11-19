import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BaseTest {
    protected WebDriver driver;
    protected String baseUrl;

    @BeforeEach
    public void setUp(){
        baseUrl = System.getProperty(
                "baseUrl",
                "http://localhost:8080"
        );

        ChromeOptions options = new ChromeOptions();
        driver = new ChromeDriver(options);
        driver.manage().timeouts()
                .pageLoadTimeout(Duration.ofSeconds(30));

        driver.manage().window().maximize();

        driver.get(baseUrl);
    }

    @RegisterExtension
    TestExecutionExceptionHandler screenshotOnFailure = (context, exception) -> {
        saveScreenshot(context);
        throw  exception;
    };

    @AfterEach
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
    }

    private void saveScreenshot(ExtensionContext context){
        if (driver == null){
            return;
        }
        try {
            Path folder = Path.of("screenshots");
            Files.createDirectories(folder);

            String testName = context.getRequiredTestMethod().getName();

            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));

            File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

            Path destination = folder.resolve(testName + "_" + timestamp + ".png");

            Files.copy(screenshot.toPath(), destination);
        } catch (IOException e){
            System.out.println("Не удалось сделать скрин");
            e.printStackTrace();
        }
    }
}

import org.example.pages.IssuesPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Path;

public class AuthenticatedTest extends  BaseTest{
    protected IssuesPage issuesPage;

    @BeforeEach
    public void loginBeforeTest() throws Exception {
        String[] credentials = Files.readString(
                Path.of("src/test/resources/valid-credentials.csv")
        ).trim().split(",");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(credentials[0], credentials[1]);

        issuesPage = new IssuesPage(driver);
        issuesPage.openIssuesTab();
    }
}

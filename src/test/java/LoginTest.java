import org.example.pages.IssuesPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfullLogin() throws IOException {

        String[] credentials = Files.readString(
                Path.of("src/test/resources/valid-credentials.csv")
        ).trim().split(",");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(credentials[0], credentials[1]);

        IssuesPage issuesPage = new IssuesPage(driver);
        issuesPage.openIssuesTab();
        assertTrue(
                issuesPage.isIssuesPageOpened(),
                "После авторизации не открылась страница с задачами"
        );
        System.out.println("Тест с правильной авторизацией");
    }

    @ParameterizedTest
    @CsvFileSource(
            resources = "/invalid-credentials.csv",
            numLinesToSkip = 0
    )

    public void testLoginWithWrongCredentials(String login, String password){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(login, password);

        assertTrue(
                loginPage.isLoginErrorDisplayed(),
                "При неправильных данных авторизации нет сообщения об ошибке"
        );
        System.out.println("Параметизированный тест");
    }
}

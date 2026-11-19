import org.example.pages.IssuesPage;
import org.example.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.assertTrue;

@Execution(ExecutionMode.CONCURRENT)
public class LoginTest extends BaseTest {

//    @Test
//    public void testScreenshot(){
//        assertTrue(false, "Проверка создания скриншота");
//    }

    @Test
    public void testSuccessfullLogin(){

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("admin","password");

        IssuesPage issuesPage = new IssuesPage(driver);
        issuesPage.openIssuesTab();

        assertTrue(
                issuesPage.isIssuesPageOpened(),
                "После авторизации не открылась страница с задачами"
        );
        System.out.println("Тест с правильной авторизацией");
    }

    @ParameterizedTest
    @CsvSource({
        "admin, 'wrong password'",
        "'wrong login', password"
    })

    public void  testLoginWithWrongCredentials(String login, String password){
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(login, password);

        assertTrue(
                loginPage.isLoginErrorDisplayed(),
                "При неправильных данных авторизации нет сообщения об ошибке"
        );
        System.out.println("Параметизированный тест");
    }
}

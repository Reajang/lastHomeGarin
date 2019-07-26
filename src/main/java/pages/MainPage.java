package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.DriverManager;

import java.util.List;

public class MainPage extends BasePage {
    @FindBy(xpath = "//span[@data-test-id='header-my-ozon-title']")
    private List<WebElement> mainBar;
    @FindBy(xpath = "//input[@placeholder]")
    private WebElement searchLineInput;
    @FindBy(xpath = "//button[@data-test-id='header-search-go']")
    private WebElement searchButton;

    //Autoriz
    @FindBy(xpath = "//div[@class='modal-content']")
    private WebElement logInForm;
    @FindBy(xpath = "//a[@data-test-id='goToEmailLink']")
    private WebElement goToEmailLink;
    @FindBy(xpath = "//input[@data-test-id='emailField']")
    private WebElement emailInput;
    @FindBy(xpath = "//input[@data-test-id='passwordField']")
    private WebElement passwordInput;
    @FindBy(xpath = "//button[@data-test-id='loginFormSubmitButton']")
    private WebElement inputButton;

    public void logIn(String login, String password){
        //clickElem(findElemByName(mainBar, "Войти"));
        clickElem(driver.findElement(By.xpath("//span[@data-test-id='header-my-ozon-title']")));
        clickElem(goToEmailLink);
        fillField(emailInput, login);
        fillField(passwordInput, password);
        clickElem(inputButton);
        //div[@class='_327ec']
    }
    public void logOut(){
        WebElement quit = findElemByName(mainBar, "Кабинет");
        clickElem(quit);
        clickElem(quit.findElement(By.xpath("//button[@class='eda59']")));
    }

    public WebElement getSearchLineInput() {
        return searchLineInput;
    }

    public WebElement getSearchButton() {
        return searchButton;
    }

    public List<WebElement> getMainBar() {
        return mainBar;
    }
}

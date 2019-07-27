package pages;

import cucumber.api.java.After;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DriverManager;

import java.util.List;

public class BasePage {
    protected WebDriver driver = DriverManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, 5,100);

    public BasePage() {
        PageFactory.initElements(driver, this);
    }

    //Не работает для MainPage
    public WebElement findElemByName(List<WebElement> elements, String name) {
        for (WebElement x : elements) {
            if (x.getText().equalsIgnoreCase(name)) {
                clickElem(x);
                return x;
            }
        }
        Assert.fail("Не найден элемент " + name);
        return null;
    }

    public void clickElem(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }
    public void fillField(WebElement element, String text){
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(text);
    }
    public void fillField(WebElement element, int number){
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(String.valueOf(number));
    }
    public WebDriverWait getWaiter(){
        return wait;
    }
    /*@After
    public void quit(){
        DriverManager.closeDriver();
    }*/
    public byte[] takeScreenshot(){
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
    public Integer getProductPriceFromString(String price){
        StringBuilder builder = new StringBuilder();
        for(char x : price.toCharArray()){
            if(Character.isDigit(x)) builder.append(x);
        }
        return Integer.parseInt(builder.toString());
    }
}

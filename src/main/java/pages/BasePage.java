package pages;

import cucumber.api.java.After;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.json.JsonOutput;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.DriverManager;

import java.util.List;

public class BasePage {
    protected WebDriver driver = DriverManager.getDriver();
    protected WebDriverWait wait = new WebDriverWait(driver, 5, 100);
    protected JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

    public BasePage() {
        PageFactory.initElements(driver, this);
    }

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

    public void fillField(WebElement element, String text) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).clear();
        element.sendKeys(text);
    }

    public WebDriverWait getWaiter() {
        return wait;
    }

    public byte[] takeScreenshot() {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    public Integer getProductPriceFromString(String price) {
        StringBuilder builder = new StringBuilder();
        for (char x : price.toCharArray()) {
            if (Character.isDigit(x)) builder.append(x);
        }
        return Integer.parseInt(builder.toString());
    }
}

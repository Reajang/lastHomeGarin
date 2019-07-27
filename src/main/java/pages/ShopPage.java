package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.DriverManager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopPage extends BasePage {

    public Map<String, String> buysMap = new LinkedHashMap<>();

    @FindBy(xpath = "//div[@class='tile m-default m-border']")
    private List<WebElement> allGoods;

    @FindBy(xpath = "//aside[@class='aside']//descendant::*")
    private List<WebElement> configBar;

    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    private WebElement maxPrice;

    public WebElement getMaxPrice() {
        return maxPrice;
    }

    public List<WebElement> getAllGoods() {
        return allGoods;
    }


    /*public void addProductToBasket(WebElement product){
        WebElement buttonAdd = product.findElement(By.xpath("/descendant::button[@class='button blue enlarged buy-text-button']"));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", buttonAdd);
        clickElem(buttonAdd);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.getWaiter().until(ExpectedConditions.invisibilityOf(buttonAdd.findElement(By.xpath("//child::span"))));
    }*/
    public WebElement getElementFromGoodsList(int num){
        return DriverManager.getDriver().findElement(By.xpath(String.format("//div[@class='tile m-default m-border'][%d]", num)));
        //return allGoods.get(num);
    }
    /*public String getProductPrice(WebElement product){
        WebElement price = product.findElement(By.xpath("/descendant::span[@data-test-id='tile-price']"));
        return price.getText();
    }
    public String getProductName(WebElement product){
        WebElement name = product.findElement(By.xpath("/descendant::a[@data-test-id='tile-name']"));
        return name.getText();
    }*/
    public String getProductPrice(int num){
        WebElement price = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::span[@data-test-id='tile-price'])[%d]", num)));
        return price.getText();
    }
    public String getProductName(int num){
        WebElement name = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::a[@data-test-id='tile-name'])[%d]", num)));
        return name.getText();
    }
    public void addProductToBasket( int num){
        WebElement buttonAdd = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::button[@class='button blue enlarged buy-text-button'])[%d]", num)));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", buttonAdd);
        clickElem(buttonAdd);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.getWaiter().until(ExpectedConditions.invisibilityOf(buttonAdd.findElement(By.xpath("//child::span"))));
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.DriverManager;

import java.util.*;
import java.util.stream.Collectors;

public class ShopPage extends BasePage {

    public Map<String, Integer> buysMap = new HashMap<>();

    @FindBy(xpath = "//div[@class='tile m-default m-border']")
    private List<WebElement> allGoods;
    @FindBy(xpath = "/aside[@class='aside']/descendant::div")
    private List<WebElement> configBar;
    @FindBy(xpath = "//input[@data-test-id='range-filter-to-input']")
    private WebElement maxPrice;
    @FindBy(xpath = "//input[@data-test-id='range-filter-from-input']")
    private WebElement minPrice;

    public WebElement getMaxPrice() {
        return maxPrice;
    }

    public List<WebElement> getAllGoods() {
        return allGoods;
    }

    public WebElement getMinPrice() {
        return minPrice;
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
    public WebElement getElementFromGoodsList(int num) {
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
    public String getProductPrice(int num) {
        WebElement price = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::span[@data-test-id='tile-price'])[%d]", num)));
        return price.getText();
    }

    public String getProductName(int num) {
        WebElement name = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::a[@data-test-id='tile-name'])[%d]", num)));
        return name.getText();
    }

    public void addProductToBasket(int num) {
        WebElement buttonAdd = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='tile m-default m-border']/descendant::button[@class='button blue enlarged buy-text-button'])[%d]", num)));
        js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", buttonAdd);
        clickElem(buttonAdd);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //this.getWaiter().until(ExpectedConditions.invisibilityOf(buttonAdd.findElement(By.xpath("//child::span"))));
    }

    public void selectBrand(String... names) {
        selectBrand(Arrays.asList(names));
    }

    public void selectBrand(List<String> names) {
        WebElement qwe = DriverManager.getDriver().findElement(By.xpath("//span[@class='show'][1]"));
        js.executeScript("return arguments[0].scrollIntoViewIfNeeded(true);", qwe);
        clickElem(qwe);
        WebElement inputBrand = DriverManager.getDriver().findElement(By.xpath("//div[@class='input-wrap search-input m-low-height']/input"));

        for (String name : names) {
            clickElem(inputBrand);
            fillField(inputBrand, name + "\n");

        }
    }
}

package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import util.DriverManager;

import java.util.*;

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
    @FindBy(xpath = "//div[@class='active-filters']/descendant::span[contains(text(), 'Цена')]")
    private WebElement filterPrice;
    @FindBy(xpath = "//span[@class='show'][1]")
    private WebElement brandsBar;
    @FindBy(xpath = "//div[@class='input-wrap search-input m-low-height']/input")
    private WebElement inputBrand;

    public WebElement getMaxPrice() {
        return maxPrice;
    }

    public List<WebElement> getAllGoods() {
        return allGoods;
    }

    public WebElement getMinPrice() {
        return minPrice;
    }

    public void addProductToBasket(WebElement product) {
        WebElement buttonAdd = product.findElement(By.xpath("./descendant::button[@class='button blue enlarged buy-text-button']"));
        js.executeScript("arguments[0].scrollIntoViewIfNeeded(true);", buttonAdd);
        clickElem(buttonAdd);
        this.getWaiter().until(ExpectedConditions.textToBePresentInElement(product.findElement(By.xpath("./descendant::span[@class='count']")), "1 шт"));
    }

    public WebElement getElementFromGoodsList(int num) {
        return allGoods.get(num);
    }

    public String getProductPrice(WebElement product) {
        WebElement price = product.findElement(By.xpath("./descendant::span[@data-test-id='tile-price']"));
        return price.getText();
    }

    public String getProductName(WebElement product) {
        WebElement name = product.findElement(By.xpath("./descendant::a[@data-test-id='tile-name']"));
        return name.getText();
    }

    public void selectBrand(String... names) {
        selectBrand(Arrays.asList(names));
    }

    public void selectBrand(List<String> names) {
        js.executeScript("return arguments[0].scrollIntoViewIfNeeded(true);", brandsBar);
        clickElem(brandsBar);
        String filterShow = "//div[@class='active-filters']/descendant::span[contains(text(), '%s')]";
        for (String name : names) {
            clickElem(inputBrand);
            fillField(inputBrand, name + "\n");
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(String.format(filterShow, name))));
        }
    }

    public WebElement getFilterPrice() {
        return filterPrice;
    }
}

package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ShopPage extends BasePage {

    public Map<String, String> buysMap = new HashMap<>();

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

    /*public Integer getProductPrice(WebElement element){
        String price = element.findElement(By.xpath("//span[@class='total-price']")).getText();
        StringBuilder builder = new StringBuilder();
        for(char x : price.toCharArray()){
            if(Character.isDigit(x)) builder.append(x);
        }
        return Integer.parseInt(builder.toString());
    }*/
    public void addProductToBasket(WebElement element){
        clickElem(element.findElement(By.xpath("//descendant::button//descendant::span")));
    }
    public List<String> getProductPrice(WebElement element){
        return element.findElements(By.xpath("//span[@data-test-id='tile-price']")).stream().map(WebElement::getText).collect(Collectors.toList());
    }
    public List<String> getProductName(WebElement element){
        return element.findElements(By.xpath("//a[@data-test-id='tile-name']")).stream().map(WebElement::getText).collect(Collectors.toList());
    }
}

package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//div[@class='bottom-inner-wrap']")
    private List<WebElement> listOfBuys;


    public List<WebElement> getListOfBuys() {
        return listOfBuys;
    }

    public Map<String, String> baskerContains(){
        if (listOfBuys.isEmpty()) return null;
        Map<String, String> res = new HashMap<>();
        for(WebElement element : listOfBuys){
            String name = element.findElement(By.xpath("//descendant::a[@class='title']")).getText();
            String price = element.findElement(By.xpath("//descendant::span[@class='price-number']")).getText();
            res.put(name, price);
        }
        return res;
    }
}

package pages;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import util.DriverManager;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BasketPage extends BasePage {

    @FindBy(xpath = "//div[@class='bottom-inner-wrap']")
    private List<WebElement> listOfBuys;
    @FindBy(xpath = "//button[contains(text(),'Удалить выбранные')]")
    private WebElement delAllSelected;
    @FindBy(xpath = "//button[contains(text(),'Выбрать всё')]")
    private WebElement selectAllProducts;
    @FindBy(xpath = "//button[@data-test-id='checkcart-confirm-modal-confirm-button']")
    private WebElement confirmDelete;
    @FindBy(xpath = "//h1")
    private WebElement titleOfEmptyBasket;

    public WebElement getTitleOfEmptyBasket() {
        return titleOfEmptyBasket;
    }

    public List<WebElement> getListOfBuys() {
        return listOfBuys;
    }

    public Map<String, String> baskerContains(){
        //if (listOfBuys.isEmpty()) return null;
        Map<String, String> res = new HashMap<>();

        for(int i = 1; i<=listOfBuys.size(); i++){
            String name = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='bottom-inner-wrap']//descendant::a[@class='title'])[%d]", i))).getText();
            String price = DriverManager.getDriver().findElement(By.xpath(String.format("(//div[@class='bottom-inner-wrap']//descendant::span[@class='price-number'])[%d]", i))).getText();
            res.put(name, price);
        }
        /*for(WebElement element : listOfBuys){
            String name = element.findElement(By.xpath("//descendant::a[@class='title']")).getText();
            String price = element.findElement(By.xpath("//descendant::span[@class='price-number']")).getText();
            res.put(name, price);
        }*/
        return res;
    }
    public void cleanBasket(){
        /*String checkSelectingAll = selectAllProducts.findElement(By.xpath("//div")).getAttribute("class");
        if(!checkSelectingAll.equals("checkbox mActive"))
            clickElem(selectAllProducts);*/
        clickElem(delAllSelected);
        clickElem(confirmDelete);
    }

}

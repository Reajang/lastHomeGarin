package steps;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasePage;
import pages.BasketPage;
import pages.MainPage;
import pages.ShopPage;
import util.DriverManager;

import java.util.Map;


public class CucumberSteps {
    private MainPage mainPage = new MainPage();
    private ShopPage shopPage = new ShopPage();
    private BasketPage basketPage = new BasketPage();

    @Допустим("Перейти на сайт {string}")
    public void goToUrl(String url){
        DriverManager.getDriver(url);
    }
    @Тогда("Выполнить авторизацию {string} {string}")
    public void logIn(String login, String password){
        //Map<String, String> personalData = dataTable.asMap(String.class, String.class);
        mainPage.logIn(login, password);
        mainPage.getWaiter().until(ExpectedConditions.elementToBeClickable(mainPage.getSearchLineInput()));
    }
    @Когда("Выполнить поиск по {string}")
    public void search(String seachingItem){
        while (!seachingItem.equals(mainPage.getSearchLineInput().getAttribute("value")))
        mainPage.fillField(mainPage.getSearchLineInput(), seachingItem);
        mainPage.clickElem(mainPage.getSearchButton());
    }
    @Тогда("Ограничить цену сверху до {int}")
    public void setMaxPrice(int price){
        if(price < 13 || price > 121048) Assert.fail(String.format("Цена %d не в диапазоне", price));//Исправить для возможных изменений диапазона
//ИСПРАВИТЬ
        shopPage.fillField(shopPage.getMaxPrice(), "\b\b"+price+"\n"); //Временный вариант
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Допустим("Добавить {int} товаров в корзину. Условие - {string}")
    public void addTobasket(int count, String rule){
        int i, step;
        if(rule.equals("нечетные")){
            i = 1;
            step = 2;
            count*=2;
        }
        else if (rule.equals("четные")){
            i = 2;
            step = 2;
            count*=2;
            count++;
        }
        else {
            i = 1;
            step = 1;
        }
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        String xpathElement = "(//div[@class='tile m-default m-border']//descendant::button)[%d]";
        String xpathName = "(//div[@class='tile m-default m-border']//a[@data-test-id='tile-name'])[%d]";
        String xpathPrice = "(//div[@class='tile m-default m-border']//span[@data-test-id='tile-price'])[%d]";
        for(; i<count; i+=step){
            String nameElement = DriverManager.getDriver().findElement(By.xpath(String.format(xpathName, i))).getText();
            String priceElement = DriverManager.getDriver().findElement(By.xpath(String.format(xpathPrice, i))).getText();
            //System.out.println(nameElement + "  "  + priceElement);
            shopPage.buysMap.put(nameElement, priceElement);
            WebElement elemClick = DriverManager.getDriver().findElement(By.xpath(String.format(xpathElement, i)));
            js.executeScript("arguments[0].scrollIntoView();", elemClick);
            elemClick.click();
        }
    }
    @Допустим("Проверить что все товары добавлены в корзину")
    public void checkProductsInBasket(){
        mainPage.findElemByName(mainPage.getMainBar(), "Корзина").click();
        if(shopPage.buysMap.size()!=basketPage.baskerContains().size()) Assert.fail("Ошибка с добавлением в корзину");
        for(Map.Entry<String, String> pair : shopPage.buysMap.entrySet()){
            if(!basketPage.baskerContains().containsKey(pair.getKey()))
                Assert.fail("Ошибка с добавлением в корзину");
        }
    }

    @Тогда("Разлогиниться")
    public void logIn(){
        mainPage.logOut();
    }


    /*@After
    public void close(){
        DriverManager.closeDriver();
    }*/

}

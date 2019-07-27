package steps;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasketPage;
import pages.MainPage;
import pages.ShopPage;
import util.DriverManager;

import java.util.List;
import java.util.Map;


public class CucumberSteps {

         MainPage mainPage = new MainPage();
         ShopPage shopPage = new ShopPage();
         BasketPage basketPage = new BasketPage();


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
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Допустим("Добавить {int} товаров в корзину. Условие - {string}")
    public void addTobasket(int count, String rule){
        int i, step, stepForAdd;
        if(rule.equals("нечетные")){
            i = 1;
            step = 2;
            count*=2;
            stepForAdd = 1;
        }
        else if (rule.equals("четные")){
            i = 2;
            step = 2;
            count*=2;
            count++;
            stepForAdd = 2;
        }
        else {
            i = 1;
            step = 1;
            stepForAdd = i;
        }
        for(; i<count; i+=step){
            //WebElement product = shopPage.getElementFromGoodsList(i);
            String nameElement = shopPage.getProductName(i);
            String priceElement = shopPage.getProductPrice(i);
            shopPage.buysMap.put(nameElement, priceElement);
            shopPage.addProductToBasket(stepForAdd++);
        }
    }
    @Допустим("Проверить что все товары добавлены в корзину")
    public void checkProductsInBasket(){
        mainPage.clickElem(mainPage.getGoToBasket());
        /*for(Map.Entry<String, String> pair : shopPage.buysMap.entrySet()){
            System.out.println(pair.getKey() + "  " + pair.getValue());
        }
        System.out.println("В корзине");
        for(Map.Entry<String, String> pair : basketPage.baskerContains().entrySet()){
            System.out.println(pair.getKey() + "  " + pair.getValue());
        }*/
        if(shopPage.buysMap.size()!=basketPage.baskerContains().size()) Assert.fail("Ошибка с добавлением в корзину");
        for(Map.Entry<String, String> pair : shopPage.buysMap.entrySet()){
            if(!basketPage.baskerContains().containsKey(pair.getKey()))
                Assert.fail("Ошибка с добавлением в корзину. Нет" + pair.getKey());
        }
    }
    @Допустим("Проверить, что итоговая цена равна сумме цен добавленных товаров")
    public void checkProductsPriceInBasket(){

    }
    @Допустим("Удалить все товары из корзины")
    public void delAllProductsFromBasker(){
        basketPage.cleanBasket();
    }
    @Тогда("Разлогиниться")
    public void logIn(){
        mainPage.logOut();
    }
    @Допустим("Проверить что корзина пуста")
    public void checkBasketClear(){
        //mainPage.clickElem(mainPage.getGoToBasket());
        String emptyBasketTitle = basketPage.getTitleOfEmptyBasket().getText();
        Assert.assertEquals("Корзина не пуста", "Корзина пуста", emptyBasketTitle);
    }


    @After
    public void close(Scenario scenario){
        if(scenario.isFailed()){
            mainPage.takeScreenshot();
        }
        DriverManager.closeDriver();
    }
}

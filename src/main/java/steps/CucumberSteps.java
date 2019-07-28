package steps;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.ru.Допустим;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pages.BasketPage;
import pages.MainPage;
import pages.ShopPage;
import util.DriverManager;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;


public class CucumberSteps {

    private MainPage mainPage = new MainPage();
    private ShopPage shopPage = new ShopPage();
    private BasketPage basketPage = new BasketPage();


    @Допустим("Перейти на сайт {string}")
    public void goToUrl(String url) {
        DriverManager.getDriver(url);
    }

    @Тогда("Выполнить авторизацию {string} {string}")
    public void logIn(String login, String password) {
        mainPage.logIn(login, password);
        //mainPage.getWaiter().until(ExpectedConditions.invisibilityOf(mainPage.getLogInForm()));
    }

   /* @Тогда("Выполнить авторизацию")
    public void logIn(Map<String, String> userData) {
        //Map<String, String> userData = dataTable.asMap(String.class, String.class);
        mainPage.logIn(userData.get("login"), userData.get("password"));
        //mainPage.getWaiter().until(ExpectedConditions.invisibilityOf(mainPage.getLogInForm()));
    }*/

    @Когда("Выполнить поиск по {string}")
    public void search(String seachingItem) {
        while (!seachingItem.equals(mainPage.getSearchLineInput().getAttribute("value")))
            mainPage.fillField(mainPage.getSearchLineInput(), seachingItem);
        mainPage.clickElem(mainPage.getSearchButton());
    }

    @Тогда("Ограничить цену сверху до {int}")
    public void setMaxPrice(int price) {
        WebElement autofill = shopPage.getMaxPrice();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < autofill.getAttribute("value").length(); i++) {
            builder.append("\b");
        }
        //Так работает стабильнее, чем при использовании clear()
        shopPage.getMaxPrice().sendKeys(builder.toString() + price + "\n");
        shopPage.getWaiter().until(ExpectedConditions.elementToBeClickable(shopPage.getFilterPrice()));

    }

    @Допустим("Выбрать бренды")
    public void selectBrands(List<String> brandList) {
        shopPage.selectBrand(brandList);
    }

    @Допустим("Добавить {int} товаров в корзину. Условие - {string}")
    public void addTobasket(int count, String rule) {
        int i, step;
        if (rule.equals("нечетные")) {
            i = 0;
            step = 2;
            count *= 2;
        } else if (rule.equals("четные")) {
            i = 1;
            step = 2;
            count *= 2;
            count++;
        } else {
            i = 1;
            step = 1;
        }
        for (; i < count; i += step) {
            WebElement product = shopPage.getElementFromGoodsList(i);
            String nameElement = shopPage.getProductName(product);
            String priceElement = shopPage.getProductPrice(product);
            shopPage.buysMap.put(nameElement, shopPage.getProductPriceFromString(priceElement));
            try {
                shopPage.addProductToBasket(product);
            } catch (NoSuchElementException e) {
                Assert.fail("Нет в наличии " + nameElement + "\nНельзя добавить в корзину");
            }
        }
    }

    @Допустим("Проверить что все товары добавлены в корзину")
    public void checkProductsInBasket() {
        mainPage.goToBasket();
        if (shopPage.buysMap.size() != basketPage.baskerContains().size())
            Assert.fail("Ошибка с добавлением в корзину");
        for (Map.Entry<String, Integer> pair : shopPage.buysMap.entrySet()) {
            if (!basketPage.baskerContains().containsKey(pair.getKey()))
                Assert.fail("Ошибка с добавлением в корзину. Нет " + pair.getKey());
        }
    }

    @Допустим("Проверить, что итоговая цена равна сумме цен добавленных товаров")
    public void checkProductsPriceInBasket() {
        int sumInBasket = basketPage.getSumma();
        int sumOfAddedProducts = 0;
        for (Map.Entry<String, Integer> pair : shopPage.buysMap.entrySet()) {
            sumOfAddedProducts += pair.getValue();
        }
        Assert.assertEquals("Несоответствие суммарной стоимости товаров сумме, указанной в корзине", sumOfAddedProducts, sumInBasket);
    }

    @Допустим("Удалить все товары из корзины")
    public void delAllProductsFromBasker() {
        basketPage.cleanBasket();
    }

    @Тогда("Разлогиниться")
    public void logIn() {
        mainPage.logOut();
    }

    @Допустим("Проверить что корзина пуста")
    public void checkBasketClear() {
        //mainPage.clickElem(mainPage.getGoToBasket());
        String emptyBasketTitle = basketPage.getTitleOfBasket().getText();
        Assert.assertEquals("Корзина не пуста", "Корзина пуста", emptyBasketTitle);
    }


    @After
    public void close(Scenario scenario) {
        if (scenario.isFailed()) {
            mainPage.takeScreenshot();
            mainPage.goToBasket();
            String basketTitle = basketPage.getTitleOfBasket().getText();
            if (!basketTitle.equals("Корзина пуста")) {
                delAllProductsFromBasker();
            }
        }
        DriverManager.closeDriver();
    }
}

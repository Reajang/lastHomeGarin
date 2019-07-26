package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverManager {
    private static WebDriver driver;
    private static final Properties PROPERTIES = new Properties();

    public static void initDriver() {
        try {
            PROPERTIES.load(new FileInputStream(new File("src/main/resources/driver.PROPERTIES")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (PROPERTIES.getProperty("browser")) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", PROPERTIES.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", PROPERTIES.getProperty("webdriver.edge.driver"));
                driver = new EdgeDriver();
                break;
            default:
                System.setProperty("webdriver.chrome.driver", PROPERTIES.getProperty("webdriver.chrome.driver"));
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public static void closeDriver() {
        /*for (String windowHandler : driver.getWindowHandles())
            driver.switchTo().window(windowHandler).close();*/
        driver.quit();
    }

    public static WebDriver getDriver(String url) {
        if(driver == null) initDriver();
        driver.get(url);
        return driver;
    }
    public static WebDriver getDriver(){
        if(driver == null) {
            initDriver();
            driver.get(PROPERTIES.getProperty("url"));
        }
        return driver;
    }

}

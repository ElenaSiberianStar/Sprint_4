package tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pageobject.MainPage;
import pageobject.OrderPage;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertTrue;


@RunWith(Parameterized.class)
public class OrderTest {

    private WebDriver driver;

    private final String browser;
    private final String entryPoint; // "header" или "footer"
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final String date;
    private final String period;
    private final String color;
    private final String comment;

    public OrderTest(String browser, String entryPoint, String firstName, String lastName, String address,
                     String metroStation, String phone, String date, String period,
                     String color, String comment) {
        this.browser = browser;
        this.entryPoint = entryPoint;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.date = date;
        this.period = period;
        this.color = color;
        this.comment = comment;
    }
    @Parameterized.Parameters
    public static Collection<Object[]> getOrderData() {
        return Arrays.asList(new Object[][]{
                // Chrome: Набор 1 (Верхняя кнопка)
                {"chrome", "header", "Елена", "Кокошина", "Москва, ул. Ленина 10", "Черкизовская", "89131113131", "18.10.2026", "сутки", "black", "Помахать рукой"},
                // Chrome: Набор 2 (Нижняя кнопка)
                {"chrome", "footer", "Семен", "Трололо", "Москва, ул. Тверская 10", "Сокольники", "89232322323", "20.10.2026", "двое суток", "grey", "Привезти пончики"},

                // Firefox: Набор 1 (Верхняя кнопка)
                {"firefox", "header", "Елена", "Кокошина", "Москва, ул. Ленина 10", "Черкизовская", "89131113131", "18.10.2026", "сутки", "black", "Одеться в костюм Чебурашки"},
                // Firefox: Набор 2 (Нижняя кнопка)
                {"firefox", "footer", "Семен", "Трололо", "Москва, ул. Тверская 10", "Сокольники", "89232322323", "20.10.2026", "двое суток", "grey", "Поцеловать при встрече"}
        });
    }
    @Before
    public void setUp() {
        if ("firefox".equalsIgnoreCase(browser)) {
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
        } else {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
    }

    @Test
    public void testScooterOrderFlow() {
        MainPage mainPage = new MainPage(driver);
        mainPage.open();
        mainPage.acceptCookies();

        if ("header".equalsIgnoreCase(entryPoint)) {
            mainPage.clickHeaderOrderButton();
        } else {
            mainPage.clickFooterOrderButton();
        }

        OrderPage orderPage = new OrderPage(driver);
        orderPage.fillFirstForm(firstName, lastName, address, metroStation, phone);
        orderPage.fillSecondForm(date, period, color, comment);
        orderPage.orderNextButton();
        orderPage.orderYesButton();

        assertTrue("Окно об успешном создании заказа не появилось!", orderPage.isOrderMessage());
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

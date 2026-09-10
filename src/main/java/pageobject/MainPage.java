package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class MainPage {
    private WebDriver driver;
    public static final String PAGE_URL = "https://qa-scooter.praktikum-services.ru/";
    // локаторы элементов
    // Заголовок страницы
    private final By LogoYandex = By.xpath("//img[@alt='Yandex']");
    // Заголовок страницы самокат
    private final By LogoScooter = By.xpath("//img[@alt='Scooter']");
    // Кнопка "Заказать" в шапке страницы
    private final By HeaderOrderButton = By.className("Button_Button__ra12g");
    // Кнопка "Статус заказа" в шапке страницы
    private final By orderStatusButton = By.className("Header_Link__1TAG7");
    // Кнопка "Заказать" в середине страницы
    private final By footerOrderButton = By.xpath(".//button[contains(@class, 'Button_UltraBig') and text()='Заказать']");
    // Заголовок раздела "Вопросы о важном"
    private final By question = By.className("Home_SubHeader__zwi_E");
    // Константы для вопросов о важном УБРАТЬ ИХ В ТЕСТ
    public static final String EXPECTED_QUESTION_1 = "Сколько это стоит? И как оплатить?";
    public static final String EXPECTED_ANSWER_1 = "Сутки — 400 рублей. Оплата курьеру — наличными или картой.";

    public static final String EXPECTED_QUESTION_2 = "Хочу сразу несколько самокатов! Так можно?";
    public static final String EXPECTED_ANSWER_2 = "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим.";

    public static final String EXPECTED_QUESTION_3 = "Как рассчитывается время аренды?";
    public static final String EXPECTED_ANSWER_3 = "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30.";

    public static final String EXPECTED_QUESTION_4 = "Можно ли заказать самокат прямо на сегодня?";
    public static final String EXPECTED_ANSWER_4 = "Только начиная с завтрашнего дня. Но скоро станем расторопнее.";

    public static final String EXPECTED_QUESTION_5 = "Можно ли продлить заказ или вернуть самокат раньше?";
    public static final String EXPECTED_ANSWER_5 = "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010.";

    public static final String EXPECTED_QUESTION_6 = "Вы привозите зарядку вместе с самокатом?";
    public static final String EXPECTED_ANSWER_6 = "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится.";

    public static final String EXPECTED_QUESTION_7 = "Можно ли отменить заказ?";
    public static final String EXPECTED_ANSWER_7 = "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои.";

    public static final String EXPECTED_QUESTION_8 = "Я жизу за МКАДом, привезёте?";
    public static final String EXPECTED_ANSWER_8 = "Да, обязательно. Всем самокатов! И Москве, и Московской области.";
    // Поиск кнопки вопроса по тексту
    private static final String BUTTON_BY_TEXT_XPATH =
        "//div[contains(@class, 'accordion__button') and contains(text(), '%s')]";
    // Поиск ответа, привязанного к вопросу с этим текстом
    private static final String ANSWER_PANEL_XPATH =
            "//div[contains(@class, 'accordion__button') and contains(text(), '%s')]/parent::div/following-sibling::div[contains(@class, 'accordion__panel')]";
    // Кнопка куки
    private final By cookieAcceptButton = By.id("rcc-confirm-button");

    // Методы для получения By-локаторов
    public By getButtonByText(String questionText) {
        return By.xpath(String.format(BUTTON_BY_TEXT_XPATH, questionText));
    }
    public  By getAnswerByQuestionText(String questionText) {
        return By.xpath(String.format(ANSWER_PANEL_XPATH, questionText));
    }
    // Конструктор

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }
    // Методы
    // Открыть главную страницу
    public void open() {
        driver.get(PAGE_URL);
    }
        // Кликнуть по верхней кнопке "Заказать"
    public void clickHeaderOrderButton() {
        driver.findElement(HeaderOrderButton).click();
    }
    // Проскроллить до нижней кнопки "Заказать" и кликнуть по ней
    public void clickFooterOrderButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement faqHeader = driver.findElement(footerOrderButton);
        // Скроллим к кнопке
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", faqHeader);
        // Ожидание кнопки
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(footerOrderButton));
        button.click();
    }
    // Проскроллить к разделу "Вопросы о важном"
    public void scrollToFaq() {
        WebElement element = driver.findElement(question);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    // Нажать на вопрос по его тексту
    public void clickQuestion(String questionText) {
        By questionLocator = By.xpath(String.format(BUTTON_BY_TEXT_XPATH, questionText));
         driver.findElement(questionLocator).click();
    }

    // Получить текст ответа для конкретного вопроса
    public String getAnswerText(String questionText) {
        By answerLocator = By.xpath(String.format(ANSWER_PANEL_XPATH, questionText));
        // Ожидание 5 секунд
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(answerLocator));

        return driver.findElement(answerLocator).getText();
    }
    // Принять куки, если всплывашка присутствует. Тут помог ИИ
    public void acceptCookies() {
        if (!driver.findElements(cookieAcceptButton).isEmpty()) {
            driver.findElement(cookieAcceptButton).click();
        }
    }
}



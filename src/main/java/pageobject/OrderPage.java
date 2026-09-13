package pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class OrderPage {

    private WebDriver driver;

    // Локаторы: 1 формы заказа «Для кого самокат»
    // Поле «Имя»
    private final By firstNameInput = By.xpath(".//input[@placeholder='* Имя']");
    // Поле «Фамилия»
    private final By lastNameInput = By.xpath(".//input[@placeholder='* Фамилия']");
    // Поле «Адрес: куда привезти заказ»
    private final By addressInput = By.xpath(".//input[@placeholder='* Адрес: куда привезти заказ']");
    // Поле «Станция метро»
    private final By metroInput = By.xpath(".//input[@placeholder='* Станция метро']");
    // Выбор станции Метро
    private final String metroOptionXpath = ".//div[contains(@class, 'select-search')]//button[.//div[text()='%s']]";
    // Поле «Телефон: на него позвонит курьер»
    private final By phoneInput = By.xpath(".//input[@placeholder='* Телефон: на него позвонит курьер']");
    // Кнопка «Далее»
    private final By nextButton = By.xpath(".//button[text()='Далее']");

    // Локаторы: 2 формы заказа «Про аренду»
    // Поле «Когда привезти самокат»
    private final By dateInput = By.xpath(".//input[@placeholder='* Когда привезти самокат']");
    // Поле «Срок аренды»
    private final By timeInput = By.xpath("//div[@class='Dropdown-placeholder' and text()='* Срок аренды']");
    // Выпадпющий список «Срок аренды»
    private final String rentalPeriodOptionXpath = "//div[@class='Dropdown-menu']//div[text()='%s']";
    // Поле «Цвет самоката»
    private final By colourInput = By.className("Order_Title__3EKne");
    // Чекбокс «чёрный жемчуг»
    private final By blackCheckbox = By.cssSelector("#black[type='checkbox']");
    // Чекбокс «Cерая безысходность»
    private final By greyCheckbox = By.cssSelector("#grey[type='checkbox']");
    // Поле "Комментарий для курьера"
    private final By commentInput = By.xpath(".//input[@placeholder='Комментарий для курьера']");
    // Кнопка «Заказать»
    public final By orderNextButton = By.xpath(".//div[contains(@class, 'Order_Buttons')]//button[text()='Заказать']");

    // Локаторы: 3 формы заказа «Заказ оформлен»
    // Кнопка «Да»
    private final By orderYesButton = By.xpath("//button[text()='Да']");
    // Текст "Заказ оформлен"
    private final By orderMessage = By.xpath("//div[@class='Order_ModalHeader__3FDaJ' and contains(text(), 'Заказ оформлен')]");


    // Методы для 1 формы
    // Заполнение имени, фамилии, адреса
    public void fillFirstForm(String firstName, String lastName, String address, String metroStation, String phone) {
        driver.findElement(firstNameInput).sendKeys(firstName);
        driver.findElement(lastNameInput).sendKeys(lastName);
        driver.findElement(addressInput).sendKeys(address);

     // Выбор метро  / Тут мне помог ИИ чтобы понять как это выполнить
        driver.findElement(metroInput).click();
        By metroOption = By.xpath(String.format(metroOptionXpath, metroStation));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView();", driver.findElement(metroOption));
        driver.findElement(metroOption).click();
    // Заполнение телефона
        driver.findElement(phoneInput).sendKeys(phone);
    // Нажать "Далее"
        driver.findElement(nextButton).click();
    }
public OrderPage(WebDriver driver) {
        this.driver = driver;
    }
    // Методы для 2 формы
    // Заполнение имени, фамилии, адреса
    public void fillSecondForm(String date, String period, String color, String comment) {
        // Заполнение даты
        driver.findElement(dateInput).sendKeys(date, Keys.ENTER);
        // Заполнение срока оренды
        driver.findElement(timeInput).click();
        By periodOption = By.xpath(String.format(rentalPeriodOptionXpath, period));
        driver.findElement(periodOption).click();
        // Выбор цвета
        if ("black".equalsIgnoreCase(color)) {
            driver.findElement(blackCheckbox).click();
        } else if ("grey".equalsIgnoreCase(color)) {
            driver.findElement(greyCheckbox).click();
        }
        // Не заполняем комментарий
        if (comment != null && !comment.isEmpty()) {
            driver.findElement(commentInput).sendKeys(comment);
        }

    }
        public boolean isOrderMessage() {
            // Проверяем, что список найденных элементов с таким локатором не пуст
            return !driver.findElements(orderMessage).isEmpty();
        }
        // Нажать "Заказать"
        public void orderNextButton() {
            driver.findElement(orderNextButton).click();
        }
        // Нажать "Да"
        public void orderYesButton() {
            driver.findElement(orderYesButton).click();
        }
}
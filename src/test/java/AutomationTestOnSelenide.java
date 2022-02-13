import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.WebDriverRunner.url;

public class AutomationTestOnSelenide {

    private final int EXPECTED_PRODUCT_IN_PROMOTIONAL_SECTION = 6;

    @BeforeMethod
    public void before() {
        Configuration.startMaximized = true;
        open("https://rozetka.com.ua/");
    }

//    1.Открыть https://rozetka.com.ua/
//    2.Проверить, что в блоке Акционные предложения отображается 6 товаров
//    3.Сохранить цену товара
//    4.Перейти на продуктовую каждого товара и проверить, что цена совпадает с сохраненной на главной странице!
//    5.Вернуться на главную страницу (проверка, что открылась главная страница)
//    6.Проверить, что в блоке Акционные предложения отображается 6 товаров

    @Test
    public void selenideTest() {

        ElementsCollection productInPromotionalSection = $$(By.xpath("//h2[contains(text(), 'Акционные предложения')]/../ul/li"));
        productInPromotionalSection.shouldHaveSize(EXPECTED_PRODUCT_IN_PROMOTIONAL_SECTION);

        ElementsCollection priceOfProductInPromotionalSection = $$(By.xpath("//h2[contains(text(), 'Акционные предложения')]/..//span[@class='tile__price-value']"));

        for (int i = 0; i < priceOfProductInPromotionalSection.size(); i++) {

            productInPromotionalSection.shouldHaveSize(EXPECTED_PRODUCT_IN_PROMOTIONAL_SECTION);

            String priceFromPromotionSection = priceOfProductInPromotionalSection.get(i).innerText().trim();
            productInPromotionalSection.get(i).click();

            SelenideElement priceOfProductFromProductPage = $(By.xpath("//div[@class='product-prices__inner ng-star-inserted']/p[1]"));
            String priceFromProductPage = priceOfProductFromProductPage.innerText().trim();
            priceFromProductPage = priceFromProductPage.substring(0, priceFromProductPage.length() - 1);

            Assert.assertEquals(priceFromPromotionSection, priceFromProductPage);

            back();
            Assert.assertEquals(url(), "https://rozetka.com.ua/");
            refresh();

            SelenideElement PromotionalSection = $(By.xpath("//h2[contains(text(), 'Акционные предложения')]"));
            PromotionalSection.should(Condition.visible);
        }
    }
}

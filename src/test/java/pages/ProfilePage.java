package pages;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static utils.TestData.PROFILE_URL;

public class ProfilePage {

    private final SelenideElement deleteButton = $("#delete-record-undefined");
    private final SelenideElement okButton = $("#closeSmallModal-ok");

    @Step("Открываем страницу профиля")
    public ProfilePage openPage() {
        open(PROFILE_URL);
        return this;
    }

    @Step("Проверяем наличие книги '{title}' в списке")
    public ProfilePage verifyBookIsInList(String title) {
        String xpath = String.format("//*[text()='%s']", title);
        $x(xpath).shouldBe(visible);
        return this;
    }

    @Step("Удаляем книгу")
    public ProfilePage deleteBook() {
        deleteButton.click();
        return this;
    }

    @Step("Проверяем, что книга удалена")
    public ProfilePage verifyDeleteBook() {
        okButton.click();
        Selenide.switchTo().alert().accept();
        Selenide.switchTo().parentFrame();
        return this;
    }

    @Step("Проверяем, что книга '{title}' не в списке")
    public ProfilePage verifyBookIsNotInList(String title) {
        String xpath = String.format("//*[text()='%s']", title);
        $x(xpath).shouldNot(visible);
        return this;
    }
}

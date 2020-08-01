package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthPage {

    private String authUrl = "http://127.0.0.1:9999";
    private SelenideElement login = $("[data-test-id=\"login\"] input");
    private SelenideElement password = $("[data-test-id=\"password\"] input");
    private SelenideElement buttonLogin = $("[data-test-id=\"action-login\"]");
    private SelenideElement errorTransfer = $("[data-test-id='error-notification']");
    private SelenideElement errorBlocked = $("[data-test-id='error-notification' div.notification__content]");

    public AuthPage() {
        open(authUrl);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
        return new VerificationPage();
    }

    public void invalidLogin(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
        errorTransfer.shouldBe(visible);
    }

    public void moreInvalidPassword(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
        buttonLogin.click();
        buttonLogin.click();
        buttonLogin.click();
        buttonLogin.click();
    }

    public void validLoginWithBlockedStatus(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        buttonLogin.click();
    }

    public SelenideElement getErrorBlocked() {
        return errorBlocked;
    }
}

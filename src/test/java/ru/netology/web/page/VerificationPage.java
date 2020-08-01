package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VerificationPage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");
    private SelenideElement errorTransfer = $("[data-test-id='error-notification']");
    private SelenideElement errorBlocked = $("[data-test-id='error-notification'] div.notification__content");

    public VerificationPage() {
        codeField.shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        return new DashboardPage();
    }

    public void invalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        errorTransfer.shouldBe(visible);
    }

    public void moreInvalidVerify(String verificationCode) {
        codeField.setValue(verificationCode);
        verifyButton.click();
        verifyButton.click();
        verifyButton.click();
        verifyButton.click();
        verifyButton.click();
    }

}

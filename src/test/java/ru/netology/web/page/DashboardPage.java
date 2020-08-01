package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Condition.*;

public class DashboardPage {
    private SelenideElement head = $("[data-test-id='dashboard']");

    public void isDashboardPage () {
        head.shouldBe(visible);
    }
}

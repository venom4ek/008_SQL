package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.AuthPage;
import ru.netology.web.page.DashboardPage;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthServiceTest {
    DataHelper dataHelper = new DataHelper();

    @AfterAll
    public static void cleanTable() throws SQLException {
        DataHelper dataHelper = new DataHelper();
        dataHelper.cleanAllTable();
    }

    @Test
    void shouldDashboardPage() {
        val authPage = new AuthPage();
        val authInfo = dataHelper.getAuthInfo();
        val verificationPage = authPage.validLogin(authInfo);
        val codeVerify = dataHelper.getVerificationCode();
        verificationPage.validVerify(codeVerify);
        val dashboardPage = new DashboardPage();
        dashboardPage.isDashboardPage();
    }

    @Test
    void shouldErrorWithInvalidLogin() {
        val authPage = new AuthPage();
        val authInfo = dataHelper.getWrongAuthInfo();
        authPage.invalidLogin(authInfo);
    }

    @Test
    void shouldErrorWithInvalidCode() {
        val authPage = new AuthPage();
        val authInfo = dataHelper.getAuthInfo();
        val verificationCode = authPage.validLogin(authInfo);
        val codeVerify = dataHelper.getInvalidVerificationCode();
        verificationCode.invalidVerify(codeVerify);
    }

    @Test
    void shouldBlockedWithMoreInvalidPassword() {
        val authPage = new AuthPage();
        val authInfo = dataHelper.getLoginWithBadPassword();
        authPage.moreInvalidPassword(authInfo);
        assertEquals("Превышено количество попыток входа! Попробуйте позже!", authPage.getErrorBlocked().getText());
    }

    @Test
    void shouldBlockedWithMoreErrorCode() {
        val authPage = new AuthPage();
        val authInfo = dataHelper.getAuthInfo();
        val verificationCode = authPage.validLogin(authInfo);
        val codeVerify = dataHelper.getInvalidVerificationCode();
        verificationCode.moreInvalidVerify(codeVerify);
        assertEquals("Ошибка! Превышено количество попыток ввода кода!", authPage.getErrorBlocked().getText());
    }

    @Test
    void shouldErrorWithBlockedAccount() throws SQLException {
        dataHelper.setStatusUserVasya();
        val authPage = new AuthPage();
        val authInfo = dataHelper.getAuthInfo();
        authPage.validLoginWithBlockedStatus(authInfo);
        assertEquals("Ваш аккаунт заблокирован!", authPage.getErrorBlocked().getText());
    }


    @Nested
    public class moreLogin {

        @Test
        void shouldMoreLoginPage () throws SQLException {
            val authPage = new AuthPage();
            val authInfo = dataHelper.getAuthInfo();
            val verificationPage = authPage.validLogin(authInfo);
            val codeVerify = dataHelper.getVerificationCode();
            verificationPage.validVerify(codeVerify);

            val authPage1 = new AuthPage();
            val authInfo1 = dataHelper.getAuthInfo();
            val verificationPage1 = authPage1.validLogin(authInfo1);
            val codeVerify1 = dataHelper.getVerificationCode();
            verificationPage1.validVerify(codeVerify1);

            val authPage2 = new AuthPage();
            val authInfo2 = dataHelper.getAuthInfo();
            val verificationPage2 = authPage2.validLogin(authInfo2);
            val codeVerify2 = dataHelper.getVerificationCode();
            verificationPage2.validVerify(codeVerify2);

            val authPage3 = new AuthPage();
            val authInfo3 = dataHelper.getAuthInfo();
            val verificationPage3 = authPage3.validLogin(authInfo3);
            val codeVerify3 = dataHelper.getVerificationCode();
            verificationPage3.validVerify(codeVerify3);

            val dashboardPage = new DashboardPage();
            dashboardPage.isDashboardPage();
        }
    }
}

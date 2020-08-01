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

    @AfterAll
    public static void cleanTable() throws SQLException {
        DataHelper.cleanAllTable();
    }

    @Test
    void shouldDashboardPage() throws SQLException {
        val authPage = new AuthPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = authPage.validLogin(authInfo);
        val codeVerify = DataHelper.getVerificationCode();
        verificationPage.validVerify(codeVerify);
        val dashboardPage = new DashboardPage();
        dashboardPage.isDashboardPage();
    }

    @Test
    void shouldErrorWithInvalidLogin() {
        val authPage = new AuthPage();
        val authInfo = DataHelper.getWrongAuthInfo();
        authPage.invalidLogin(authInfo);
    }

    @Test
    void shouldErrorWithInvalidCode() {
        val authPage = new AuthPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationCode = authPage.validLogin(authInfo);
        val codeVerify = DataHelper.getInvalidVerificationCode();
        verificationCode.invalidVerify(codeVerify);
    }

    @Test
    void shouldBlockedWithMoreInvalidPassword() {
        val authPage = new AuthPage();
        val authInfo = DataHelper.getLoginWithBadPassword();
        authPage.moreInvalidPassword(authInfo);
        assertEquals("Превышено колиество попыток входа! Попробуйте позже!", authPage.getErrorBlocked().getText());
    }

    @Test
    void shouldBlockedWithMoreErrorCode() {
        val authPage = new AuthPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationCode = authPage.validLogin(authInfo);
        val codeVerify = DataHelper.getInvalidVerificationCode();
        verificationCode.moreInvalidVerify(codeVerify);
        assertEquals("Ошибка! Превышено количество попыток ввода кода!", authPage.getErrorBlocked().getText());
    }

    @Test
    void shouldErrorWithBlockedAccount() throws SQLException {
        DataHelper.setStatusUserVasya();
        val authPage = new AuthPage();
        val authInfo = DataHelper.getAuthInfo();
        authPage.validLoginWithBlockedStatus(authInfo);
        assertEquals("Ваш аккаунт заблокирован!", authPage.getErrorBlocked().getText());
    }


    @Nested
    public class moreLogin {

        @Test
        void shouldMoreLoginPage () throws SQLException {
            val authPage = new AuthPage();
            val authInfo = DataHelper.getAuthInfo();
            val verificationPage = authPage.validLogin(authInfo);
            val codeVerify = DataHelper.getVerificationCode();
            verificationPage.validVerify(codeVerify);

            val authPage1 = new AuthPage();
            val authInfo1 = DataHelper.getAuthInfo();
            val verificationPage1 = authPage1.validLogin(authInfo1);
            val codeVerify1 = DataHelper.getVerificationCode();
            verificationPage1.validVerify(codeVerify1);

            val authPage2 = new AuthPage();
            val authInfo2 = DataHelper.getAuthInfo();
            val verificationPage2 = authPage2.validLogin(authInfo2);
            val codeVerify2 = DataHelper.getVerificationCode();
            verificationPage2.validVerify(codeVerify2);

            val authPage3 = new AuthPage();
            val authInfo3 = DataHelper.getAuthInfo();
            val verificationPage3 = authPage3.validLogin(authInfo3);
            val codeVerify3 = DataHelper.getVerificationCode();
            verificationPage3.validVerify(codeVerify3);

            val dashboardPage = new DashboardPage();
            dashboardPage.isDashboardPage();
        }
    }
}

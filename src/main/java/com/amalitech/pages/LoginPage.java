package com.amalitech.pages;

import com.amalitech.utils.WaitUtil;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selenide.$;

public class LoginPage {

    // ── Locators ──────────────────────────────────────────────────
    private final SelenideElement usernameInput = $("#user-name");
    private final SelenideElement passwordInput = $("#password");
    private final SelenideElement loginButton   = $("#login-button");
    private final SelenideElement errorMessage  = $("[data-test='error']");

    // Custom timeout for login page elements — longer than default
    // to account for slow CI or Docker network conditions
    private static final long LOGIN_TIMEOUT_MS = 15_000;

    // ── Actions ───────────────────────────────────────────────────

    @Step("Enter username: {username}")
    public LoginPage enterUsername(String username) {
        WaitUtil.waitForVisible(usernameInput, LOGIN_TIMEOUT_MS);
        usernameInput.setValue(username);
        return this;
    }

    @Step("Enter password")
    public LoginPage enterPassword(String password) {
        WaitUtil.waitForVisible(passwordInput, LOGIN_TIMEOUT_MS);
        passwordInput.setValue(password);
        return this;
    }

    @Step("Click login button")
    public ProductsPage clickLogin() {
        WaitUtil.waitForEnabled(loginButton, LOGIN_TIMEOUT_MS);
        loginButton.click();
        return new ProductsPage();
    }

    @Step("Login with username: {username}")
    public ProductsPage loginWith(String username, String password) {
        return enterUsername(username)
                .enterPassword(password)
                .clickLogin();
    }

    @Step("Login with invalid credentials — expect error")
    public LoginPage loginWithInvalidCredentials(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        WaitUtil.waitForEnabled(loginButton, LOGIN_TIMEOUT_MS);
        loginButton.click();
        return this;
    }

    // ── Assertions ────────────────────────────────────────────────

    @Step("Verify error message is visible")
    public LoginPage verifyErrorVisible() {
        WaitUtil.waitForVisible(errorMessage, LOGIN_TIMEOUT_MS);
        return this;
    }

    @Step("Verify error message text: {expectedText}")
    public void verifyErrorText(String expectedText) {
        WaitUtil.waitForText(errorMessage, expectedText, LOGIN_TIMEOUT_MS);
    }

    @Step("Verify login page is loaded")
    public void verifyPageLoaded() {
        WaitUtil.waitForVisible(usernameInput, LOGIN_TIMEOUT_MS);
        WaitUtil.waitForVisible(passwordInput, LOGIN_TIMEOUT_MS);
        WaitUtil.waitForVisible(loginButton,   LOGIN_TIMEOUT_MS);
    }
}
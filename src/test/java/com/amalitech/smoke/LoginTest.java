package com.amalitech.smoke;

import com.amalitech.base.BaseTest;
import com.amalitech.dataprovider.TestDataProvider;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.Test;

@Feature("Authentication")
public class LoginTest extends BaseTest {

    // ── Happy path ────────────────────────────────────────────────

    @Test(
            dataProvider = "validLoginCredentials",
            dataProviderClass = TestDataProvider.class,
            description = "Valid credentials should land on the products page"
    )
    @Story("Valid login")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies that a valid user can log in and reach the products page")
    public void validLoginShouldReachProductsPage(String username, String password) {
        ProductsPage productsPage = new LoginPage()
                .loginWith(username, password);

        productsPage.verifyPageLoaded();
    }

    // ── Unhappy path ──────────────────────────────────────────────

    @Test(
            dataProvider = "invalidLoginCredentials",
            dataProviderClass = TestDataProvider.class,
            description = "Invalid credentials should show an error message"
    )
    @Story("Invalid login")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that invalid or missing credentials produce the correct error message")
    public void invalidLoginShouldShowError(String username, String password, String expectedError) {
        new LoginPage()
                .loginWithInvalidCredentials(username, password)
                .verifyErrorVisible()
                .verifyErrorText(expectedError);
    }

    // ── Page load ─────────────────────────────────────────────────

    @Test(
            description = "Login page should load all required elements"
    )
    @Story("Login page load")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the login page renders username, password and login button")
    public void loginPageShouldLoadCorrectly() {
        new LoginPage().verifyPageLoaded();
    }
}
package com.amalitech.regression;

import com.amalitech.base.BaseTest;
import com.amalitech.dataprovider.TestDataProvider;
import com.amalitech.pages.CartPage;
import com.amalitech.pages.LoginPage;
import com.amalitech.testdata.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    private CartPage cartPage;

    // ── Setup ─────────────────────────────────────────────────────

    @BeforeMethod
    public void loginAndAddProductToCart() {
        cartPage = new LoginPage()
                .loginWith(TestData.Users.STANDARD_USER, TestData.Users.PASSWORD)
                .addProductToCartByName(TestData.Products.BACKPACK)
                .goToCart();
    }

    // ── Happy path ────────────────────────────────────────────────

    @Test(
            dataProvider = "validCheckoutInfo",
            dataProviderClass = TestDataProvider.class,
            description = "Valid checkout info should complete the order successfully"
    )
    @Story("Successful checkout")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies the full checkout flow from cart to order confirmation with valid customer info")
    public void validCheckoutShouldCompleteOrder(String firstName,
                                                 String lastName,
                                                 String postalCode) {
        cartPage.verifyPageLoaded()
                .verifyCartContains(TestData.Products.BACKPACK)
                .proceedToCheckout()
                .verifyStepOneLoaded()
                .fillCustomerInfo(firstName, lastName, postalCode)
                .clickContinue()
                .verifySummaryLoaded()
                .verifySummaryContains(TestData.Products.BACKPACK)
                .clickFinish()
                .verifyOrderConfirmed();
    }

    // ── Order summary ─────────────────────────────────────────────

    @Test(description = "Order summary should show correct product and price")
    @Story("Order summary")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that the order summary page shows the correct product name and price")
    public void orderSummaryShouldShowCorrectProductAndPrice() {
        cartPage.proceedToCheckout()
                .fillCustomerInfo(
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                )
                .clickContinue()
                .verifySummaryLoaded()
                .verifySummaryContains(TestData.Products.BACKPACK)
                .verifySubtotal(TestData.Products.BACKPACK_PRICE);
    }

    @Test(description = "Order summary should show correct item count")
    @Story("Order summary")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the order summary shows the correct number of items")
    public void orderSummaryShouldShowCorrectItemCount() {
        cartPage.continueShopping()
                .addProductToCartByName(TestData.Products.BIKE_LIGHT)
                .goToCart()
                .proceedToCheckout()
                .fillCustomerInfo(
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                )
                .clickContinue()
                .verifySummaryLoaded()
                .verifySummaryItemCount(2);
    }

    // ── Tax and total ─────────────────────────────────────────────

    @Test(description = "Order summary should show correct tax and total for Sauce Labs Backpack")
    @Story("Order summary pricing")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the tax and total shown on the order summary are correct for a single backpack")
    public void orderSummaryShouldShowCorrectTaxAndTotal() {
        cartPage.proceedToCheckout()
                .fillCustomerInfo(
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                )
                .clickContinue()
                .verifySummaryLoaded()
                .verifySubtotal(TestData.Products.BACKPACK_PRICE)
                .verifyTax(TestData.Checkout.TAX)
                .verifyTotal(TestData.Checkout.TOTAL);
    }

    // ── Unhappy path ──────────────────────────────────────────────

    @Test(
            dataProvider = "invalidCheckoutInfo",
            dataProviderClass = TestDataProvider.class,
            description = "Missing checkout fields should show the correct error message"
    )
    @Story("Checkout validation")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that submitting checkout with missing fields produces the correct error message")
    public void missingCheckoutFieldShouldShowError(String firstName,
                                                    String lastName,
                                                    String postalCode,
                                                    String expectedError) {
        cartPage.proceedToCheckout()
                .verifyStepOneLoaded()
                .fillCustomerInfo(firstName, lastName, postalCode)
                .clickContinue()
                .verifyErrorVisible()
                .verifyErrorText(expectedError);
    }

    // ── Cancel checkout ───────────────────────────────────────────

    @Test(description = "Cancelling checkout on step one should return to cart")
    @Story("Cancel checkout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that clicking cancel on the checkout info page returns to the cart with items intact")
    public void cancelOnStepOneShouldReturnToCart() {
        cartPage.proceedToCheckout()
                .verifyStepOneLoaded()
                .cancelCheckout()
                .verifyPageLoaded()
                .verifyCartContains(TestData.Products.BACKPACK);
    }

    @Test(description = "Cancelling checkout on step two should return to cart")
    @Story("Cancel checkout")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that clicking back on the order summary page returns to the cart with items intact")
    public void cancelOnStepTwoShouldReturnToCart() {
        cartPage.proceedToCheckout()
                .fillCustomerInfo(
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                )
                .clickContinue()
                .verifySummaryLoaded()
                .goBackToCart()
                .verifyPageLoaded()
                .verifyCartContains(TestData.Products.BACKPACK);
    }

    // ── Post order ────────────────────────────────────────────────

    @Test(description = "Back to products button after confirmation should return to products page")
    @Story("Post order navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that after a successful order the back home button returns to the products page")
    public void backToProductsAfterOrderShouldWork() {
        cartPage.proceedToCheckout()
                .fillCustomerInfo(
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                )
                .clickContinue()
                .clickFinish()
                .verifyOrderConfirmed()
                .backToProducts()
                .verifyPageLoaded();
    }
}
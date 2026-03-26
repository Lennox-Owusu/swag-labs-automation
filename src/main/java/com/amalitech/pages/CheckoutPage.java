package com.amalitech.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CheckoutPage {

    // ── Step One: Customer Info ───────────────────────────────────
    private final SelenideElement firstNameInput   = $("[data-test='firstName']");
    private final SelenideElement lastNameInput    = $("[data-test='lastName']");
    private final SelenideElement postalCodeInput  = $("[data-test='postalCode']");
    private final SelenideElement continueButton   = $("[data-test='continue']");
    private final SelenideElement cancelButton     = $("[data-test='cancel']");
    private final SelenideElement errorMessage     = $("[data-test='error']");

    // ── Step Two: Order Summary ───────────────────────────────────
    private final SelenideElement summaryTitle     = $(".title");
    private final ElementsCollection summaryItems  = $$(".cart_item");
    private final ElementsCollection summaryNames  = $$(".inventory_item_name");
    private final SelenideElement subtotalLabel    = $(".summary_subtotal_label");
    private final SelenideElement taxLabel         = $(".summary_tax_label");
    private final SelenideElement totalLabel       = $(".summary_total_label");
    private final SelenideElement finishButton     = $("[data-test='finish']");
    private final SelenideElement backToCartButton = $("[data-test='back']");

    // ── Step Three: Confirmation ──────────────────────────────────
    private final SelenideElement confirmationHeader = $(".complete-header");
    private final SelenideElement confirmationText   = $(".complete-text");
    private final SelenideElement backHomeButton     = $("[data-test='back-to-products']");

    // ── Step One Actions ──────────────────────────────────────────

    @Step("Enter first name: {firstName}")
    public CheckoutPage enterFirstName(String firstName) {
        firstNameInput.shouldBe(visible).setValue(firstName);
        return this;
    }

    @Step("Enter last name: {lastName}")
    public CheckoutPage enterLastName(String lastName) {
        lastNameInput.shouldBe(visible).setValue(lastName);
        return this;
    }

    @Step("Enter postal code: {postalCode}")
    public CheckoutPage enterPostalCode(String postalCode) {
        postalCodeInput.shouldBe(visible).setValue(postalCode);
        return this;
    }

    @Step("Fill customer info — {firstName} {lastName}, {postalCode}")
    public CheckoutPage fillCustomerInfo(String firstName, String lastName, String postalCode) {
        return enterFirstName(firstName)
                .enterLastName(lastName)
                .enterPostalCode(postalCode);
    }

    @Step("Click continue to order summary")
    public CheckoutPage clickContinue() {
        continueButton.shouldBe(visible).click();
        return this;
    }

    @Step("Cancel checkout and return to cart")
    public CartPage cancelCheckout() {
        cancelButton.shouldBe(visible).click();
        return new CartPage();
    }

    // ── Step Two Actions ──────────────────────────────────────────

    @Step("Click finish to place order")
    public CheckoutPage clickFinish() {
        finishButton.shouldBe(visible).click();
        return this;
    }

    @Step("Go back to cart from order summary")
    public CartPage goBackToCart() {
        backToCartButton.shouldBe(visible).click();
        return new CartPage();
    }

    // ── Step Three Actions ────────────────────────────────────────

    @Step("Return to products page after order confirmation")
    public ProductsPage backToProducts() {
        backHomeButton.shouldBe(visible).click();
        return new ProductsPage();
    }

    // ── Step One Assertions ───────────────────────────────────────

    @Step("Verify checkout step one is loaded")
    public CheckoutPage verifyStepOneLoaded() {
        firstNameInput.shouldBe(visible);
        lastNameInput.shouldBe(visible);
        postalCodeInput.shouldBe(visible);
        return this;
    }

    @Step("Verify error message is visible")
    public CheckoutPage verifyErrorVisible() {
        errorMessage.shouldBe(visible);
        return this;
    }

    @Step("Verify error message text: {expectedText}")
    public void verifyErrorText(String expectedText) {
        errorMessage.shouldHave(text(expectedText));
    }

    // ── Step Two Assertions ───────────────────────────────────────

    @Step("Verify order summary is loaded")
    public CheckoutPage verifySummaryLoaded() {
        summaryTitle.shouldBe(visible).shouldHave(text("Checkout: Overview"));
        return this;
    }

    @Step("Verify summary contains product: {productName}")
    public CheckoutPage verifySummaryContains(String productName) {
        summaryNames.findBy(text(productName)).shouldBe(visible);
        return this;
    }

    @Step("Verify summary item count is: {expectedCount}")
    public void verifySummaryItemCount(int expectedCount) {
        summaryItems.shouldHave(CollectionCondition.size(expectedCount));
    }

    @Step("Verify subtotal label contains: {expectedSubtotal}")
    public CheckoutPage verifySubtotal(String expectedSubtotal) {
        subtotalLabel.shouldHave(text(expectedSubtotal));
        return this;
    }

    @Step("Verify tax label contains: {expectedTax}")
    public CheckoutPage verifyTax(String expectedTax) {
        taxLabel.shouldHave(text(expectedTax));
        return this;
    }

    @Step("Verify total label contains: {expectedTotal}")
    public void verifyTotal(String expectedTotal) {
        totalLabel.shouldHave(text(expectedTotal));
    }

    // ── Step Three Assertions ─────────────────────────────────────

    @Step("Verify order confirmation is displayed")
    public CheckoutPage verifyOrderConfirmed() {
        confirmationHeader.shouldBe(visible).shouldHave(text("Thank you for your order!"));
        confirmationText.shouldBe(visible);
        return this;
    }
}
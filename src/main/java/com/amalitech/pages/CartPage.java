package com.amalitech.pages;

import com.amalitech.utils.WaitUtil;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class CartPage {

    // ── Locators ──────────────────────────────────────────────────
    private final SelenideElement pageTitle         = $(".title");
    private final SelenideElement checkoutButton    = $("[data-test='checkout']");
    private final SelenideElement continueShopBtn   = $("[data-test='continue-shopping']");
    private final ElementsCollection cartItems      = $$(".cart_item");
    private final ElementsCollection itemNames      = $$(".inventory_item_name");
    private final ElementsCollection removeButtons  = $$("[data-test^='remove']");

    // ── Actions ───────────────────────────────────────────────────

    @Step("Remove product from cart by name: {productName}")
    public CartPage removeProductByName(String productName) {
        SelenideElement item = itemNames.findBy(text(productName))
                .closest(".cart_item");
        item.$("[data-test^='remove']")
                .shouldBe(visible)
                .click();
        WaitUtil.waitForHidden(item, 10000);
        return this;
    }
    @Step("Remove all products from cart")
    public CartPage removeAllProducts() {
        removeButtons.forEach(btn -> btn.shouldBe(visible).click());
        return this;
    }

    @Step("Proceed to checkout")
    public CheckoutPage proceedToCheckout() {
        checkoutButton.shouldBe(visible).click();
        return new CheckoutPage();
    }

    @Step("Continue shopping")
    public ProductsPage continueShopping() {
        continueShopBtn.shouldBe(visible).click();
        return new ProductsPage();
    }

    // ── Assertions ────────────────────────────────────────────────

    @Step("Verify cart page is loaded")
    public CartPage verifyPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(text("Your Cart"));
        return this;
    }

    @Step("Verify cart contains product: {productName}")
    public CartPage verifyCartContains(String productName) {
        itemNames.findBy(text(productName)).shouldBe(visible);
        return this;
    }

    @Step("Verify cart does not contain product: {productName}")
    public void verifyCartDoesNotContain(String productName) {
        itemNames.findBy(text(productName)).shouldNotBe(visible);
    }

    @Step("Verify cart item count is: {expectedCount}")
    public CartPage verifyCartItemCount(int expectedCount) {
        cartItems.shouldHave(CollectionCondition.size(expectedCount));
        return this;
    }

    @Step("Verify cart is empty")
    public void verifyCartIsEmpty() {
        cartItems.shouldHave(CollectionCondition.size(0));
    }

    @Step("Verify cart is not empty")
    public CartPage verifyCartIsNotEmpty() {
        WaitUtil.waitForCollectionNotEmpty(cartItems, 10000);
        return this;
    }

    @Step("Verify product quantity is: {expectedQuantity} for product: {productName}")
    public void verifyProductQuantity(String productName, String expectedQuantity) {
        itemNames.findBy(text(productName))
                .closest(".cart_item")
                .$(".cart_quantity")
                .shouldHave(text(expectedQuantity));
    }

    @Step("Verify product price is: {expectedPrice} for product: {productName}")
    public void verifyProductPrice(String productName, String expectedPrice) {
        itemNames.findBy(text(productName))
                .closest(".cart_item")
                .$(".inventory_item_price")
                .shouldHave(text(expectedPrice));
    }
}
package com.amalitech.pages;

import com.amalitech.utils.WaitUtil;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class ProductDetailsPage {

    // ── Locators ──────────────────────────────────────────────────
    private final SelenideElement productName        = $("[data-test='inventory-item-name']");
    private final SelenideElement productDescription = $("[data-test='inventory-item-desc']");
    private final SelenideElement productPrice       = $("[data-test='inventory-item-price']");
    private final SelenideElement productImage       = $(".inventory_details_img");
    private final SelenideElement addToCartButton    = $("[data-test^='add-to-cart']");
    private final SelenideElement removeButton       = $("[data-test^='remove']");
    private final SelenideElement backButton         = $("[data-test='back-to-products']");
    private final SelenideElement shoppingCartLink   = $(".shopping_cart_link");
    private final SelenideElement shoppingCartBadge  = $(".shopping_cart_badge");

    // ── Actions ───────────────────────────────────────────────────

    @Step("Add product to cart from details page")
    public ProductDetailsPage addToCart() {
        addToCartButton.shouldBe(visible).click();
        return this;
    }

    @Step("Remove product from cart on details page")
    public ProductDetailsPage removeFromCart() {
        removeButton.shouldBe(visible).click();
        return this;
    }

    @Step("Go back to products page")
    public ProductsPage goBackToProducts() {
        backButton.shouldBe(visible).click();
        return new ProductsPage();
    }

    @Step("Go to shopping cart from details page")
    public CartPage goToCart() {
        shoppingCartLink.shouldBe(visible).click();
        return new CartPage();
    }

    // ── Assertions ────────────────────────────────────────────────

    @Step("Verify product details page is loaded")
    public ProductDetailsPage verifyPageLoaded() {
        productName.shouldBe(visible);
        productDescription.shouldBe(visible);
        productPrice.shouldBe(visible);
        productImage.shouldBe(visible);
        return this;
    }

    @Step("Verify product name is: {expectedName}")
    public ProductDetailsPage verifyProductName(String expectedName) {
        productName.shouldHave(text(expectedName));
        return this;
    }

    @Step("Verify product price is: {expectedPrice}")
    public void verifyProductPrice(String expectedPrice) {
        productPrice.shouldHave(text(expectedPrice));
    }

    @Step("Verify product description is: {expectedDescription}")
    public void verifyProductDescription(String expectedDescription) {
        productDescription.shouldHave(text(expectedDescription));
    }

    @Step("Verify add to cart button is visible")
    public ProductDetailsPage verifyAddToCartButtonVisible() {
        addToCartButton.shouldBe(visible);
        return this;
    }

    @Step("Verify add to cart button is enabled and ready")
    public void verifyAddToCartButtonEnabled() {
        WaitUtil.waitForAttribute(addToCartButton, "id", "add-to-cart-sauce-labs-backpack", 10000);
    }

    @Step("Verify remove button is visible")
    public ProductDetailsPage verifyRemoveButtonVisible() {
        removeButton.shouldBe(visible);
        return this;
    }

    @Step("Verify cart badge count is: {expectedCount}")
    public ProductDetailsPage verifyCartBadgeCount(int expectedCount) {
        shoppingCartBadge.shouldBe(visible).shouldHave(text(String.valueOf(expectedCount)));
        return this;
    }
}
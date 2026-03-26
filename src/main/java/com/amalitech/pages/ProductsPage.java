package com.amalitech.pages;

import com.amalitech.testdata.TestData;
import com.amalitech.utils.WaitUtil;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class ProductsPage {

    // ── Locators ──────────────────────────────────────────────────
    private final SelenideElement pageTitle         = $(".title");
    private final SelenideElement shoppingCartBadge = $(".shopping_cart_badge");
    private final SelenideElement shoppingCartLink  = $(".shopping_cart_link");
    private final SelenideElement sortDropdown      = $("[data-test='product-sort-container']");
    private final ElementsCollection productItems   = $$(".inventory_item");
    private final ElementsCollection productNames   = $$(".inventory_item_name");
    private final ElementsCollection productPrices  = $$(".inventory_item_price");
    private final ElementsCollection addToCartBtns  = $$("[data-test^='add-to-cart']");

    // ── Actions ───────────────────────────────────────────────────

    @Step("Add product to cart by name: {productName}")
    public ProductsPage addProductToCartByName(String productName) {
        productNames.findBy(text(productName))
                .closest(".inventory_item")
                .$("[data-test^='add-to-cart']")
                .shouldBe(visible)
                .click();
        return this;
    }

    @Step("Add first product to cart")
    public ProductsPage addFirstProductToCart() {
        addToCartBtns.first().shouldBe(visible).click();
        return this;
    }

    @Step("Open product details for: {productName}")
    public ProductDetailsPage openProductDetails(String productName) {
        productNames.findBy(text(productName))
                .shouldBe(visible)
                .click();
        return new ProductDetailsPage();
    }

    @Step("Sort products by: {option}")
    public ProductsPage sortBy(String option) {
        sortDropdown.shouldBe(visible).selectOption(option);
        return this;
    }

    @Step("Go to shopping cart")
    public CartPage goToCart() {
        shoppingCartLink.shouldBe(visible).click();
        return new CartPage();
    }

    // ── Assertions ────────────────────────────────────────────────

    @Step("Verify products page is loaded")
    public ProductsPage verifyPageLoaded() {
        pageTitle.shouldBe(visible).shouldHave(text("Products"));
        return this;
    }

    @Step("Verify cart badge count is: {expectedCount}")
    public ProductsPage verifyCartBadgeCount(int expectedCount) {
        shoppingCartBadge.shouldBe(visible).shouldHave(text(String.valueOf(expectedCount)));
        return this;
    }

    @Step("Verify cart badge is not visible")
    public ProductsPage verifyCartBadgeNotVisible() {
        shoppingCartBadge.shouldNotBe(visible);
        return this;
    }

    @Step("Verify product is listed: {productName}")
    public void verifyProductListed(String productName) {
        productNames.findBy(text(productName)).shouldBe(visible);
    }

    @Step("Verify product count is: {expectedCount}")
    public void verifyProductCount(int expectedCount) {
        WaitUtil.waitForCollectionSize(productItems, expectedCount, 10000);
    }

    @Step("Verify all products have a price displayed")
    public void verifyAllProductsHavePrices() {
        productPrices.shouldHave(CollectionCondition.size(TestData.Products.TOTAL_PRODUCT_COUNT));
        productPrices.forEach(price -> price.shouldBe(visible));
    }

    @Step("Verify product price on listing: {expectedPrice} for product: {productName}")
    public void verifyProductPriceOnListing(String productName, String expectedPrice) {
        productNames.findBy(text(productName))
                .closest(".inventory_item")
                .$(".inventory_item_price")
                .shouldHave(text(expectedPrice));
    }
}
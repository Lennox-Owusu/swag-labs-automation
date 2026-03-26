package com.amalitech.regression;

import com.amalitech.base.BaseTest;
import com.amalitech.dataprovider.TestDataProvider;
import com.amalitech.pages.CartPage;
import com.amalitech.pages.LoginPage;
import com.amalitech.pages.ProductsPage;
import com.amalitech.testdata.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    private ProductsPage productsPage;

    // ── Setup ─────────────────────────────────────────────────────

    @BeforeMethod
    public void loginAndGoToProducts() {
        productsPage = new LoginPage()
                .loginWith(TestData.Users.STANDARD_USER, TestData.Users.PASSWORD);
    }

    // ── Add to cart ───────────────────────────────────────────────

    @Test(
            dataProvider = "cartProducts",
            dataProviderClass = TestDataProvider.class,
            description = "Adding products to cart should reflect correct item count in cart"
    )
    @Story("Add products to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that adding one or more products updates the cart badge and cart page correctly")
    public void addingProductsShouldUpdateCart(String[] productNames, int expectedCount) {
        for (String productName : productNames) {
            productsPage.addProductToCartByName(productName);
        }

        CartPage cartPage = productsPage
                .verifyCartBadgeCount(expectedCount)
                .goToCart();

        cartPage.verifyPageLoaded()
                .verifyCartItemCount(expectedCount);

        for (String productName : productNames) {
            cartPage.verifyCartContains(productName);
        }
    }

    // ── Remove from cart ──────────────────────────────────────────

    @Test(description = "Removing a product from cart should update cart item count")
    @Story("Remove product from cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that removing one product from a two-item cart leaves one item remaining")
    public void removingProductShouldUpdateCartCount() {
        CartPage cartPage = productsPage
                .addProductToCartByName(TestData.Products.BACKPACK)
                .addProductToCartByName(TestData.Products.BIKE_LIGHT)
                .goToCart();

        cartPage.verifyPageLoaded()
                .verifyCartItemCount(2)
                .removeProductByName(TestData.Products.BIKE_LIGHT)
                .verifyCartItemCount(1)
                .verifyCartContains(TestData.Products.BACKPACK)
                .verifyCartDoesNotContain(TestData.Products.BIKE_LIGHT);
    }

    @Test(description = "Removing all products from cart should leave cart empty")
    @Story("Remove all products from cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that removing all products one by one leaves the cart empty")
    public void removingAllProductsShouldEmptyCart() {
        CartPage cartPage = productsPage
                .addProductToCartByName(TestData.Products.BACKPACK)
                .addProductToCartByName(TestData.Products.BIKE_LIGHT)
                .goToCart();

        cartPage.verifyPageLoaded()
                .verifyCartItemCount(2)
                .removeAllProducts()
                .verifyCartIsEmpty();
    }

    // ── Cart persistence ──────────────────────────────────────────

    @Test(description = "Cart contents should persist after navigating back to products page")
    @Story("Cart persistence")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that products added to cart remain there after navigating away and back")
    public void cartShouldPersistAfterNavigation() {
        productsPage
                .addProductToCartByName(TestData.Products.BACKPACK)
                .goToCart()
                .verifyCartIsNotEmpty()
                .verifyCartContains(TestData.Products.BACKPACK)
                .continueShopping()
                .verifyPageLoaded()
                .verifyCartBadgeCount(1)
                .goToCart()
                .verifyCartIsNotEmpty()
                .verifyCartContains(TestData.Products.BACKPACK);
    }

    // ── Product price in cart ─────────────────────────────────────

    @Test(
            dataProvider = "productDetails",
            dataProviderClass = TestDataProvider.class,
            description = "Product price in cart should match the price on the products page"
    )
    @Story("Cart product price")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the price shown in the cart matches the price on the products page")
    public void cartShouldShowCorrectProductPrice(String productName, String productPrice) {
        productsPage
                .addProductToCartByName(productName)
                .goToCart()
                .verifyPageLoaded()
                .verifyProductPrice(productName, productPrice);
    }

    // ── Product quantity in cart ──────────────────────────────────

    @Test(
            dataProvider = "productNames",
            dataProviderClass = TestDataProvider.class,
            description = "Product quantity in cart should default to 1"
    )
    @Story("Cart product quantity")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that each product added to the cart has a default quantity of 1")
    public void cartShouldShowCorrectProductQuantity(String productName) {
        productsPage
                .addProductToCartByName(productName)
                .goToCart()
                .verifyPageLoaded()
                .verifyProductQuantity(productName, "1");
    }

    // ── Continue shopping ─────────────────────────────────────────

    @Test(description = "Continue shopping button should return to products page")
    @Story("Continue shopping")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the continue shopping button on the cart page returns to the products page")
    public void continueShoppingButtonShouldReturnToProductsPage() {
        productsPage
                .addProductToCartByName(TestData.Products.BACKPACK)
                .goToCart()
                .verifyPageLoaded()
                .continueShopping()
                .verifyPageLoaded();
    }
}
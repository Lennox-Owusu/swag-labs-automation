package com.amalitech.smoke;

import com.amalitech.base.BaseTest;
import com.amalitech.dataprovider.TestDataProvider;
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

@Feature("Products")
public class ProductPageTest extends BaseTest {

    private ProductsPage productsPage;

    // ── Setup ─────────────────────────────────────────────────────

    @BeforeMethod
    public void loginAndGoToProducts() {
        productsPage = new LoginPage()
                .loginWith(TestData.Users.STANDARD_USER, TestData.Users.PASSWORD);
    }

    // ── Page load ─────────────────────────────────────────────────

    @Test(description = "Products page should load with correct title and all products")
    @Story("Products page load")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Verifies the products page title and that all six products are listed")
    public void productsPageShouldLoadCorrectly() {
        productsPage
                .verifyPageLoaded()
                .verifyProductCount(TestData.Products.TOTAL_PRODUCT_COUNT);
    }

    @Test(description = "All products should have a price displayed on the listing page")
    @Story("Products page load")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that every product on the listing page has a visible price")
    public void allProductsShouldHavePricesDisplayed() {
        productsPage
                .verifyPageLoaded()
                .verifyAllProductsHavePrices();
    }

    // ── Cart badge ────────────────────────────────────────────────

    @Test(description = "Cart badge should not be visible before any product is added")
    @Story("Cart badge")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the cart badge is absent on the products page before any product is added to the cart")
    public void cartBadgeShouldNotBeVisibleInitially() {
        productsPage
                .verifyPageLoaded()
                .verifyCartBadgeNotVisible();
    }

    @Test(description = "Adding first product to cart should show cart badge with count 1")
    @Story("Cart badge")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that adding the first product using the first add to cart button increments the badge to 1")
    public void addingFirstProductShouldShowCartBadge() {
        productsPage
                .verifyCartBadgeNotVisible()
                .addFirstProductToCart()
                .verifyCartBadgeCount(1);
    }

    // ── Product listing ───────────────────────────────────────────

    @Test(
            dataProvider = "productNames",
            dataProviderClass = TestDataProvider.class,
            description = "Each product should be visible on the products page"
    )
    @Story("Product listing")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that each product name is visible in the product listing")
    public void productShouldBeListedOnPage(String productName) {
        productsPage.verifyProductListed(productName);
    }

    @Test(
            dataProvider = "productDetails",
            dataProviderClass = TestDataProvider.class,
            description = "Each product should show the correct price on the listing page"
    )
    @Story("Product listing")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that each product shows the correct price on the products listing page")
    public void productShouldShowCorrectPriceOnListing(String productName, String productPrice) {
        productsPage.verifyProductPriceOnListing(productName, productPrice);
    }

    // ── Product details ───────────────────────────────────────────

    @Test(
            dataProvider = "productDetails",
            dataProviderClass = TestDataProvider.class,
            description = "Product details page should show correct name and price"
    )
    @Story("Product details")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that clicking a product opens its details page with correct name and price")
    public void productDetailsShouldShowCorrectInfo(String productName, String productPrice) {
        productsPage
                .openProductDetails(productName)
                .verifyPageLoaded()
                .verifyProductName(productName)
                .verifyProductPrice(productPrice);
    }

    @Test(
            dataProvider = "productNamesWithDescription",
            dataProviderClass = TestDataProvider.class,
            description = "Product details page should show correct description"
    )
    @Story("Product details")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the product description on the details page contains the expected text")
    public void productDetailsShouldShowCorrectDescription(String productName,
                                                           String productDescription) {
        productsPage
                .openProductDetails(productName)
                .verifyPageLoaded()
                .verifyProductDescription(productDescription);
    }
    // ── Add to cart from details page ─────────────────────────────

    @Test(description = "Add to cart button should be visible on product details page before adding")
    @Story("Product details add to cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the add to cart button is visible when the product has not been added yet")
    public void addToCartButtonShouldBeVisibleOnDetailsPage() {
        productsPage
                .openProductDetails(TestData.Products.BACKPACK)
                .verifyPageLoaded()
                .verifyAddToCartButtonVisible()
                .verifyAddToCartButtonEnabled();
    }

    @Test(description = "Adding product from details page should swap button to remove and update badge")
    @Story("Product details add to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that adding a product from the details page shows the remove button and updates the cart badge to 1")
    public void addingFromDetailsShouldSwapButtonAndUpdateBadge() {
        productsPage
                .openProductDetails(TestData.Products.BACKPACK)
                .verifyPageLoaded()
                .verifyAddToCartButtonVisible()
                .addToCart()
                .verifyRemoveButtonVisible()
                .verifyCartBadgeCount(1);
    }

    // ── Remove from cart on details page ──────────────────────────

    @Test(description = "Removing product from details page should swap button back to add to cart")
    @Story("Product details remove from cart")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that removing a product from the details page swaps the button back to add to cart")
    public void removingFromDetailsShouldSwapButtonBack() {
        productsPage
                .openProductDetails(TestData.Products.BACKPACK)
                .addToCart()
                .verifyRemoveButtonVisible()
                .verifyCartBadgeCount(1)
                .removeFromCart()
                .verifyAddToCartButtonVisible();
    }

    // ── Go to cart from details page ──────────────────────────────

    @Test(description = "Cart icon on details page should navigate to cart page")
    @Story("Product details navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that clicking the cart icon from the product details page navigates to the cart page")
    public void cartIconOnDetailsShouldNavigateToCart() {
        productsPage
                .openProductDetails(TestData.Products.BACKPACK)
                .addToCart()
                .goToCart()
                .verifyPageLoaded()
                .verifyCartContains(TestData.Products.BACKPACK);
    }

    // ── Navigation back ───────────────────────────────────────────

    @Test(description = "Back button on product details should return to products page")
    @Story("Product details navigation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the back button on the product details page returns to the products page")
    public void backButtonShouldReturnToProductsPage() {
        productsPage
                .openProductDetails(TestData.Products.BACKPACK)
                .goBackToProducts()
                .verifyPageLoaded();
    }

    // ── Sort ──────────────────────────────────────────────────────

    @Test(
            dataProvider = "sortOptions",
            dataProviderClass = TestDataProvider.class,
            description = "Sort dropdown should apply each sort option without error"
    )
    @Story("Product sorting")
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that each sort option can be selected and the page remains stable")
    public void sortDropdownShouldApplyOption(String sortOption) {
        productsPage
                .sortBy(sortOption)
                .verifyPageLoaded();
    }

    // ── Add to cart from products page ────────────────────────────

    @Test(
            dataProvider = "productNames",
            dataProviderClass = TestDataProvider.class,
            description = "Adding a product to cart should increment the cart badge"
    )
    @Story("Add to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies that adding a product from the products page increments the cart badge to 1")
    public void addingProductShouldIncrementCartBadge(String productName) {
        productsPage
                .addProductToCartByName(productName)
                .verifyCartBadgeCount(1);
    }
}
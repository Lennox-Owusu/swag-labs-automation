package com.amalitech.testdata;

public final class TestData {

    private TestData() {}

    // ── Valid Users ───────────────────────────────────────────────
    public static final class Users {
        public static final String STANDARD_USER    = "standard_user";
        public static final String LOCKED_OUT_USER  = "locked_out_user";
        public static final String PERFORMANCE_USER = "performance_glitch_user";
        public static final String PASSWORD         = "secret_sauce";

        private Users() {}
    }

    // ── Products ──────────────────────────────────────────────────
    public static final class Products {
        public static final String BACKPACK            = "Sauce Labs Backpack";
        public static final String BIKE_LIGHT          = "Sauce Labs Bike Light";
        public static final String BOLT_SHIRT          = "Sauce Labs Bolt T-Shirt";
        public static final String FLEECE_JACKET       = "Sauce Labs Fleece Jacket";
        public static final String ONESIE              = "Sauce Labs Onesie";
        public static final String RED_SHIRT           = "Test.allTheThings() T-Shirt (Red)";

        public static final String BACKPACK_PRICE      = "$29.99";
        public static final String BIKE_LIGHT_PRICE    = "$9.99";
        public static final String BOLT_SHIRT_PRICE    = "$15.99";
        public static final String FLEECE_JACKET_PRICE = "$49.99";
        public static final String ONESIE_PRICE        = "$7.99";
        public static final String RED_SHIRT_PRICE     = "$15.99";

        public static final String BACKPACK_DESC       =
                "carry.allTheThings() with the sleek, streamlined Sly Pack";
        public static final String BIKE_LIGHT_DESC     =
                "A red light isn't the desired state in testing but it sure";
        public static final String FLEECE_JACKET_DESC  =
                "It's not every day that you come across a midweight quarter-zip fleece";

        public static final int TOTAL_PRODUCT_COUNT    = 6;

        private Products() {}
    }

    // ── Checkout ──────────────────────────────────────────────────
    public static final class Checkout {
        public static final String FIRST_NAME   = "John";
        public static final String LAST_NAME    = "Doe";
        public static final String POSTAL_CODE  = "12345";
        public static final String TAX          = "2.40";
        public static final String TOTAL        = "32.39";

        private Checkout() {}
    }

    // ── Sort Options ──────────────────────────────────────────────
    public static final class SortOptions {
        public static final String NAME_ASC   = "Name (A to Z)";
        public static final String NAME_DESC  = "Name (Z to A)";
        public static final String PRICE_ASC  = "Price (low to high)";
        public static final String PRICE_DESC = "Price (high to low)";

        private SortOptions() {}
    }

    // ── Error Messages ────────────────────────────────────────────
    public static final class ErrorMessages {
        public static final String LOCKED_OUT =
                "Epic sadface: Sorry, this user has been locked out.";
        public static final String MISSING_USERNAME =
                "Epic sadface: Username is required";
        public static final String MISSING_PASSWORD =
                "Epic sadface: Password is required";
        public static final String MISSING_FIRST_NAME =
                "Error: First Name is required";
        public static final String MISSING_LAST_NAME =
                "Error: Last Name is required";
        public static final String MISSING_POSTAL_CODE =
                "Error: Postal Code is required";

        private ErrorMessages() {}
    }
}
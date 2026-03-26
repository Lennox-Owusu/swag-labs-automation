package com.amalitech.dataprovider;

import com.amalitech.testdata.TestData;
import org.testng.annotations.DataProvider;

public final class TestDataProvider {

    private TestDataProvider() {}

    // ── Login ─────────────────────────────────────────────────────

    @DataProvider(name = "validLoginCredentials")
    public static Object[][] validLoginCredentials() {
        return new Object[][] {
                { TestData.Users.STANDARD_USER,    TestData.Users.PASSWORD },
                { TestData.Users.PERFORMANCE_USER, TestData.Users.PASSWORD }
        };
    }

    @DataProvider(name = "invalidLoginCredentials")
    public static Object[][] invalidLoginCredentials() {
        return new Object[][] {
                {
                        TestData.Users.LOCKED_OUT_USER,
                        TestData.Users.PASSWORD,
                        TestData.ErrorMessages.LOCKED_OUT
                },
                {
                        "",
                        TestData.Users.PASSWORD,
                        TestData.ErrorMessages.MISSING_USERNAME
                },
                {
                        TestData.Users.STANDARD_USER,
                        "",
                        TestData.ErrorMessages.MISSING_PASSWORD
                }
        };
    }

    // ── Products ──────────────────────────────────────────────────

    @DataProvider(name = "productDetails")
    public static Object[][] productDetails() {
        return new Object[][] {
                { TestData.Products.BACKPACK,      TestData.Products.BACKPACK_PRICE      },
                { TestData.Products.BIKE_LIGHT,    TestData.Products.BIKE_LIGHT_PRICE    },
                { TestData.Products.BOLT_SHIRT,    TestData.Products.BOLT_SHIRT_PRICE    },
                { TestData.Products.FLEECE_JACKET, TestData.Products.FLEECE_JACKET_PRICE },
                { TestData.Products.ONESIE,        TestData.Products.ONESIE_PRICE        },
                { TestData.Products.RED_SHIRT,     TestData.Products.RED_SHIRT_PRICE     }
        };
    }

    @DataProvider(name = "productNames")
    public static Object[][] productNames() {
        return new Object[][] {
                { TestData.Products.BACKPACK      },
                { TestData.Products.BIKE_LIGHT    },
                { TestData.Products.BOLT_SHIRT    },
                { TestData.Products.FLEECE_JACKET },
                { TestData.Products.ONESIE        },
                { TestData.Products.RED_SHIRT     }
        };
    }

    @DataProvider(name = "productDetailsWithDescription")
    public static Object[][] productDetailsWithDescription() {
        return new Object[][] {
                {
                        TestData.Products.BACKPACK,
                        TestData.Products.BACKPACK_PRICE,
                        TestData.Products.BACKPACK_DESC
                },
                {
                        TestData.Products.BIKE_LIGHT,
                        TestData.Products.BIKE_LIGHT_PRICE,
                        TestData.Products.BIKE_LIGHT_DESC
                },
                {
                        TestData.Products.FLEECE_JACKET,
                        TestData.Products.FLEECE_JACKET_PRICE,
                        TestData.Products.FLEECE_JACKET_DESC
                }
        };
    }

    @DataProvider(name = "productNamesWithDescription")
    public static Object[][] productNamesWithDescription() {
        return new Object[][] {
                { TestData.Products.BACKPACK,      TestData.Products.BACKPACK_DESC      },
                { TestData.Products.BIKE_LIGHT,    TestData.Products.BIKE_LIGHT_DESC    },
                { TestData.Products.FLEECE_JACKET, TestData.Products.FLEECE_JACKET_DESC }
        };
    }

    @DataProvider(name = "sortOptions")
    public static Object[][] sortOptions() {
        return new Object[][] {
                { TestData.SortOptions.NAME_ASC   },
                { TestData.SortOptions.NAME_DESC  },
                { TestData.SortOptions.PRICE_ASC  },
                { TestData.SortOptions.PRICE_DESC }
        };
    }

    // ── Cart ──────────────────────────────────────────────────────

    @DataProvider(name = "cartProducts")
    public static Object[][] cartProducts() {
        return new Object[][] {
                {
                        new String[] { TestData.Products.BACKPACK },
                        1
                },
                {
                        new String[] { TestData.Products.BACKPACK, TestData.Products.BIKE_LIGHT },
                        2
                },
                {
                        new String[] {
                                TestData.Products.BACKPACK,
                                TestData.Products.BIKE_LIGHT,
                                TestData.Products.BOLT_SHIRT
                        },
                        3
                }
        };
    }

    // ── Checkout ──────────────────────────────────────────────────

    @DataProvider(name = "validCheckoutInfo")
    public static Object[][] validCheckoutInfo() {
        return new Object[][] {
                {
                        TestData.Checkout.FIRST_NAME,
                        TestData.Checkout.LAST_NAME,
                        TestData.Checkout.POSTAL_CODE
                },
                { "Jane", "Smith", "54321" }
        };
    }

    @DataProvider(name = "invalidCheckoutInfo")
    public static Object[][] invalidCheckoutInfo() {
        return new Object[][] {
                { "",     "Doe",  "12345", TestData.ErrorMessages.MISSING_FIRST_NAME  },
                { "John", "",     "12345", TestData.ErrorMessages.MISSING_LAST_NAME   },
                { "John", "Doe",  "",      TestData.ErrorMessages.MISSING_POSTAL_CODE }
        };
    }
}
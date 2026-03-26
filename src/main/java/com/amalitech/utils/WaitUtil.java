package com.amalitech.utils;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;

public final class WaitUtil {

    private WaitUtil() {}

    // ── Element waits ─────────────────────────────────────────────

    // Wait for element to be visible with custom timeout (milliseconds)
    public static void waitForVisible(SelenideElement element, long timeoutMs) {
        element.shouldBe(visible, java.time.Duration.ofMillis(timeoutMs));
    }

    // Wait for element to disappear with custom timeout (milliseconds)
    public static void waitForHidden(SelenideElement element, long timeoutMs) {
        element.shouldBe(hidden, java.time.Duration.ofMillis(timeoutMs));
    }

    // Wait for element to have specific text with custom timeout (milliseconds)
    public static void waitForText(SelenideElement element, String text, long timeoutMs) {
        element.shouldHave(Condition.text(text), java.time.Duration.ofMillis(timeoutMs));
    }

    // Wait for element to be enabled with custom timeout (milliseconds)
    public static void waitForEnabled(SelenideElement element, long timeoutMs) {
        element.shouldBe(enabled, java.time.Duration.ofMillis(timeoutMs));
    }

    // Wait for element to have a specific attribute value
    public static void waitForAttribute(SelenideElement element,
                                        String attribute,
                                        String value,
                                        long timeoutMs) {
        element.shouldHave(
                Condition.attribute(attribute, value),
                java.time.Duration.ofMillis(timeoutMs)
        );
    }

    // ── Collection waits ──────────────────────────────────────────

    // Wait for collection to reach expected size
    public static void waitForCollectionSize(ElementsCollection collection,
                                             int expectedSize,
                                             long timeoutMs) {
        collection.shouldHave(
                CollectionCondition.size(expectedSize),
                java.time.Duration.ofMillis(timeoutMs)
        );
    }

    // Wait for collection to have at least one element
    public static void waitForCollectionNotEmpty(ElementsCollection collection,
                                                 long timeoutMs) {
        collection.shouldHave(
                CollectionCondition.sizeGreaterThan(0),
                java.time.Duration.ofMillis(timeoutMs)
        );
    }
}
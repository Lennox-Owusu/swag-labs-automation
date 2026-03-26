package com.amalitech.base;

import com.amalitech.config.AppConfig;
import com.amalitech.utils.LoggerUtil;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected final AppConfig config = AppConfig.get();
    private static final Logger log = LoggerUtil.getLogger(BaseTest.class);

    @BeforeMethod
    public void setUp(java.lang.reflect.Method method) {
        log.info("Starting test: {}", method.getName());
        log.info("Browser: {} | Headless: {}", config.browser(), config.headless());

        // ── Browser ───────────────────────────────────────────────
        Configuration.browser = config.browser();

        // ── Headless ──────────────────────────────────────────────
        Configuration.headless = config.headless();

        // ── Base URL ──────────────────────────────────────────────
        Configuration.baseUrl = config.baseUrl();

        // ── Timeouts ──────────────────────────────────────────────
        Configuration.timeout = config.implicitWait() * 1000L;
        Configuration.pageLoadTimeout = config.pageLoadTimeout() * 1000L;

        // ── Screenshots & reports ─────────────────────────────────
        Configuration.reportsFolder = "target/allure-results/screenshots";

        // ── Open browser ──────────────────────────────────────────
        Selenide.open(config.baseUrl());
        log.info("Browser opened at: {}", config.baseUrl());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            log.error("Test FAILED: {}", result.getName());
            log.error("Failure reason: {}", result.getThrowable().getMessage());
            takeScreenshot(result.getName());
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            log.info("Test PASSED: {}", result.getName());
        } else {
            log.warn("Test SKIPPED: {}", result.getName());
        }
        Selenide.closeWebDriver();
        log.info("Browser closed after test: {}", result.getName());
    }

    // ── Screenshot helper ─────────────────────────────────────────
    @Attachment(value = "Screenshot on failure: {testName}", type = "image/png")
    private void takeScreenshot(String testName) {
        log.info("Taking screenshot for failed test: {}", testName);
        ((TakesScreenshot) WebDriverRunner.getWebDriver())
                .getScreenshotAs(OutputType.BYTES);
    }
}
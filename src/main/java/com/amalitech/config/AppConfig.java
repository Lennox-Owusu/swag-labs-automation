package com.amalitech.config;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.ConfigFactory;

@Config.Sources({
        "system:properties",
        "classpath:config.properties"
})
public interface AppConfig extends Config {

    static AppConfig get() {
        return ConfigFactory.create(AppConfig.class, System.getProperties());
    }

    @Key("base.url")
    @DefaultValue("https://www.saucedemo.com")
    String baseUrl();

    @Key("browser")
    @DefaultValue("chrome")
    String browser();

    @Key("headless")
    @DefaultValue("false")
    boolean headless();

    @Key("implicit.wait")
    @DefaultValue("10")
    int implicitWait();

    @Key("page.load.timeout")
    @DefaultValue("30")
    int pageLoadTimeout();
}
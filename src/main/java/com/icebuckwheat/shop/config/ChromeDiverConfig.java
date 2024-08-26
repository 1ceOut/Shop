package com.icebuckwheat.shop.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChromeDiverConfig {

    @Bean
    public ChromeOptions ChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");

        //options.setCapability("ignoreProtectedModeSettings", true);
        return options;
    }

    @Bean(name = "web_driver")
    public WebDriver driver() {
        return new ChromeDriver(ChromeOptions());
    }

    @Bean(name = "goods")
    public WebDriver goods_driver() {
        return new ChromeDriver(ChromeOptions());
    }
}

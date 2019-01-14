package com.overgaauw.chat.config;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

public class SeleniumConfig {


    @Getter
    private WebDriver driver;

    public SeleniumConfig() {
        driver = new ChromeDriver();
    }

    static {
        System.setProperty("webdriver.chrome.driver", findFile("chromedriver-mac"));
    }

    static private String findFile(String filename) {
        String paths[] = {"", "bin/", "target/classes"};
        for (String path : paths) {
            if (new File(path + filename).exists())
                return path + filename;
        }
        return "";
    }

    public void close() {
        driver.close();
    }

}


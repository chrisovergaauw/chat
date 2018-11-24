package com.overgaauw.chat.integration;

import com.overgaauw.chat.config.SeleniumConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatAppIntegrationTests {

    @LocalServerPort
    int port;

    private SeleniumConfig config;
    private WebDriver driver;
    private String url;

    private String userDonatello = "Donatello";
    private String userRaphael = "Raphael";
    private String testPassword = "p1zza";

    public ChatAppIntegrationTests() {
        config = new SeleniumConfig();
        driver = config.getDriver();

    }

    @Before
    public void setup() {
        url = "http://localhost:" + port;
        performLoginSequence(driver, userDonatello, testPassword);

    }

    @After
    public void cleanup() {
        config.close();
        driver.quit();
    }

    @Test
    public void userIsAbleToLoginWithCorrectCredentials() {
        assertEquals("Hello WebSocket", driver.getTitle());
    }

    @Test
    public void userIsNOTAbleToLoginWithCorrectCredentials() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userDonatello, "incorrectPassword1!");
        String url = tmpDriver.getCurrentUrl();
        tmpConfig.close();
        tmpDriver.quit();

        assertTrue(url.endsWith("error"));
    }

    @Test
    public void welcomeMessageDisplayed(){
        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"greetings\"]/tr")));
        String.format("%s has joined the channel!", userDonatello).equals(dynamicElement);
    }

    @Test
    public void secondUserIsAbleToLogin() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);
        String title = tmpDriver.getTitle();
        tmpConfig.close();
        tmpDriver.quit();

        assertEquals("Hello WebSocket", title);
    }

    @Test
    public void secondWelcomeMessageDisplayed(){
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);

        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"greetings\"]/tr[2]")));
        String.format("%s has joined the channel!", userRaphael).equals(dynamicElement);

        tmpConfig.close();
        tmpDriver.quit();
    }

    @Test
    public void usersCanSendAndReadMessages(){
        final String msgToSend = String.format("Hello %s", userDonatello);
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);
        driver.findElement(By.id("message")).sendKeys(msgToSend);
        driver.findElement(By.id("sendMessage")).click();

        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"greetings\"]/tr[3]")));
        String.format(msgToSend).equals(dynamicElement);

        tmpConfig.close();
        tmpDriver.quit();
    }

    private void performLoginSequence(WebDriver driver, String username, String password) {
        driver.get(url);
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("submit")).click();
    }
}

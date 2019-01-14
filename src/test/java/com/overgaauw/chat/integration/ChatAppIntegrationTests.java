package com.overgaauw.chat.integration;

import com.overgaauw.chat.config.SeleniumConfig;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChatAppIntegrationTests {

    @LocalServerPort
    int port;

    private SeleniumConfig config;
    private WebDriver driver;
    private String url;
    private String userDonatello = "Donatello";
    private String userRaphael = "Raphael";
    private String userLeonardo = "Leonardo";
    private String testPassword = "p1zza";

    public ChatAppIntegrationTests() {
        config = new SeleniumConfig();
        driver = config.getDriver();

    }

    @BeforeEach
    public void setup() {
        url = "http://localhost:" + port;
        performLoginSequence(driver, userDonatello, testPassword);
    }

    @AfterEach
    public void reset() {
        performLogoutSequence(driver);
    }

    @AfterAll
    public void cleanup() {
        config.close();
    }

    @Test
    public void userIsAbleToLoginWithCorrectCredentials() {
        assertEquals("Hello WebSocket", driver.getTitle());
    }

    @Test
    public void userIsAbleToLoginWithCorrectCredentialsAndLogout() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userLeonardo, testPassword);

        String loggedInTitle = tmpDriver.getTitle();
        performLogoutSequence(tmpDriver);
        String loggedOutURL = tmpDriver.getCurrentUrl();

        tmpConfig.close();

        assertEquals("Hello WebSocket",loggedInTitle);
        assertTrue(loggedOutURL.endsWith("logout"));
    }

    @Test
    public void userIsNOTAbleToLoginWithIncorrectCredentials() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userLeonardo, "incorrectPassword1!");
        String url = tmpDriver.getCurrentUrl();
        tmpConfig.close();

        assertTrue(url.endsWith("error"));
    }

    @Test
    public void welcomeMessageDisplayed(){
        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"messages\"]/tr")));
        assertTrue(dynamicElement.getText().contains(String.format("%s has joined the channel!", userDonatello)));
    }

    @Test
    public void secondUserIsAbleToLogin() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);
        String title = tmpDriver.getTitle();

        performLogoutSequence(tmpDriver);
        tmpConfig.close();

        assertEquals("Hello WebSocket", title);
    }

    @Test
    public void secondWelcomeMessageDisplayedForFirstUser() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);

        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"messages\"]/tr[2]")));

        performLogoutSequence(tmpDriver);
        tmpConfig.close();

        assertTrue(dynamicElement.getText().contains(String.format("%s has joined the channel!", userRaphael)));
    }

    @Test
    public void usersCanSendAndReadMessages() {
        final String msgToSend = String.format("Hello %s", userDonatello);
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);
        driver.findElement(By.id("message")).sendKeys(msgToSend);
        driver.findElement(By.id("sendMessage")).click();

        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"messages\"]/tr[3]")));

        performLogoutSequence(tmpDriver);
        tmpConfig.close();

        assertTrue(dynamicElement.getText().contains(msgToSend));
    }

    @Test
    public void userNameDisplayedInUserlist() {
        WebElement dynamicElement = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]/tr")));
        assertEquals(userDonatello,dynamicElement.getText());
    }

    @Test
    public void multipleUserNamesDisplayedInUserlist() {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);

        String firstUsernameAsSeenByFirstUser = (new WebDriverWait(driver, 15))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]/tr")))
                .getText();
        String secondUsernameAsSeenBySecondUser = (new WebDriverWait(tmpDriver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]/tr[2]")))
                .getText();
        String secondUsernameAsSeenByFirstUser = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]/tr[2]")))
                .getText();

        performLogoutSequence(tmpDriver);
        tmpConfig.close();

        assertTrue(String.format("%s%s",userDonatello,userRaphael).contains(firstUsernameAsSeenByFirstUser));
        assertTrue(String.format("%s%s",userDonatello,userRaphael).contains(secondUsernameAsSeenBySecondUser));
        assertTrue(String.format("%s%s",userDonatello,userRaphael).contains(secondUsernameAsSeenByFirstUser));
    }

    @Test
    public void userListShrinksAfterAnotherUserLeaves() throws InterruptedException {
        final SeleniumConfig tmpConfig = new SeleniumConfig();
        final WebDriver tmpDriver = tmpConfig.getDriver();
        performLoginSequence(tmpDriver, userRaphael, testPassword);

        String secondUsernameAsSeenByFirstUser = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]/tr[2]")))
                .getText();
        performLogoutSequence(tmpDriver);
        String userListAsSeenByFirstUser = (new WebDriverWait(driver, 10))
                .until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id=\"users\"]")))
                .getText();

        tmpConfig.close();

        assertTrue(String.format("%s%s",userDonatello,userRaphael).contains(secondUsernameAsSeenByFirstUser));
        assertTrue(String.format("%s%s",userDonatello,userRaphael).contains(userListAsSeenByFirstUser));
    }

    private void performLoginSequence(WebDriver driver, String username, String password) {
        driver.get(url);
        Select usernameField = new Select(driver.findElement(By.id("username")));
        usernameField.selectByValue(username);
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys(password);
        driver.findElement(By.id("submit")).click();
    }

    private void performLogoutSequence(WebDriver driver) {
        driver.get(url);
        driver.findElement(By.id("logout")).click();
    }
}

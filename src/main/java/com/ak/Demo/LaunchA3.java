package com.ak.Demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class LaunchA3 {
    private static String _username="a3VtYXJhYWw=";
    private static String _tokenPre="MTEwMDM0";
    private static String _password="OTc3NDEyMjA5M0BTb251";
    private static String _gotURL="aHR0cHM6Ly9pdGFueXdoZXJlLXNuZy5yYS51YnMuY29tL3Zwbi9pdGFueXdoZXJlLmh0bWw=";
    static WebDriver driver;
    private static void _pressEnterWithTab(){
        Robot robot= null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    private static void _pressEnter(){
        Robot robot= null;
        try {
            robot = new Robot();
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (AWTException e) {
            e.printStackTrace();
        }

    }
    static void invokeBrowser(String _tokenId){
        try {
            System.setProperty("webdriver.chrome.driver","E:\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().deleteAllCookies();
            driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
            driver.manage().timeouts().pageLoadTimeout(30,TimeUnit.SECONDS);
            driver.get(Decrypt(_gotURL));
            driver.findElement(By.name("login")).sendKeys(Decrypt(_username));
            driver.findElement(By.name("passwd1")).sendKeys(Decrypt(_password));
            driver.findElement(By.name("passwd")).sendKeys(Decrypt(_tokenPre)+_tokenId);
            driver.findElement(By.id("Log_On")).click();
            Thread.sleep(5000);
            _pressEnterWithTab();
            Thread.sleep(2000);
            _pressEnter();
            Thread.sleep(10000);
            driver.findElement(By.className("iconImage")).click();
            Thread.sleep(2000);
            _pressEnter();
            Thread.sleep(10000);
            driver.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static String Encrypt(String _stringtoEncrypt){
        String encodedString = Base64.getEncoder().encodeToString(_stringtoEncrypt.getBytes());
        System.out.println(encodedString);
        return encodedString;
    }
    private static String Decrypt(String _stringToDecrypt){
        byte[] decodedBytes = Base64.getDecoder().decode(_stringToDecrypt);
        String decodedString = new String(decodedBytes);

        return decodedString;
    }
    public static void main(String[] args) {
        String input="110034";
        Decrypt(Encrypt(input));
    }

}


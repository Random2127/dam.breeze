package com.example.appiumtest;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.By;

import java.net.URL;

public class LoginTest {
    public static void main(String[] args) throws Exception {
        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName("Android")
                .setDeviceName("emulator-5554")
                .setApp("/home/random/Breeze/app")
                .setAppPackage("com.example.breeze")
                .setAppActivity(".MainActivity");

        AppiumDriver driver = new AndroidDriver(new URL("http://localhost:4723/wd/hub"), options);

        Thread.sleep(3000);

        driver.findElement(By.id("com.example.breeze:id/email")).sendKeys("org@prueba.es");
        driver.findElement(By.id("com.example.breeze:id/password")).sendKeys("org");
        driver.findElement(By.id("com.example.breeze:id/boton1_main")).click();

        Thread.sleep(3000);

        String activity = ((AndroidDriver) driver).currentActivity();
        System.out.println("Current Activity: " + activity);

        if (activity.contains("ClientHomeActivity") || activity.contains("OrganizerHomeActivity")) {
            System.out.println("✅ Login test passed!");
        } else {
            System.out.println("❌ Login test failed.");
        }

        driver.quit();
    }
}

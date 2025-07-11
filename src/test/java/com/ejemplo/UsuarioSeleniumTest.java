package com.ejemplo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

public class UsuarioSeleniumTest {

    private static WebDriver driver;

    @BeforeAll
    static void iniciarServidorYDriver() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3000); // esperar a que Spark inicie

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterAll
    static void cerrarTodo() {
        if (driver != null)
            driver.quit();
        spark.Spark.stop();

        try {
            Thread.sleep(1000); // espera que Spark se detenga
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deberiaCrearUsuarioConPesoValido() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("nombre")).sendKeys("Paulino");
        driver.findElement(By.name("peso")).sendKeys("80");
        driver.findElement(By.tagName("button")).click();

        String nombre = driver.findElement(By.id("nombre")).getText();
        String peso = driver.findElement(By.id("peso")).getText();

        assertEquals("Nombre: Paulino", nombre);
        assertEquals("Peso: 80.0", peso);
    }
}

package com.ejemplo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRegresionTest {

    private WebDriver driver;

    @BeforeAll
    static void iniciarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(2000); // espera a que Spark levante
    }

    @AfterAll
    static void detenerServidor() {
        spark.Spark.stop();
    }

    @BeforeEach
    void iniciarDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void cerrarDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void actualizarPesoValido_deberiaFuncionar() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("nombre")).sendKeys("Paulino");
        driver.findElement(By.name("peso")).sendKeys("65");
        driver.findElement(By.tagName("button")).click();

        String nombre = driver.findElement(By.id("nombre")).getText();
        String peso = driver.findElement(By.id("peso")).getText();

        assertEquals("Nombre: Paulino", nombre);
        assertEquals("Peso: 65.0", peso);
    }

    @Test
    @Order(2)
    void actualizarPesoNegativo_deberiaMostrarError() {
        driver.get("http://localhost:8080/");

        driver.findElement(By.name("nombre")).sendKeys("Carlos");
        driver.findElement(By.name("peso")).sendKeys("-5");
        driver.findElement(By.tagName("button")).click();

        String pageSource = driver.getPageSource();
        System.out.println("HTML recibido:");
        System.out.println(pageSource);

        assertTrue(pageSource.contains("El peso no puede ser negativo")
                || pageSource.contains("Exception")
                || pageSource.contains("error"),
                "Se esperaba un mensaje de error al ingresar peso negativo");
    }
}

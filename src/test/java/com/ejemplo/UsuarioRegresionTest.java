package com.ejemplo;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsuarioRegresionTest {

    private WebDriver driver;

    @BeforeAll
    static void iniciarServidor() throws InterruptedException {
        new Thread(() -> App.main(null)).start();
        Thread.sleep(3000); // espera a que Spark levante
    }

    @AfterAll
    static void detenerServidor() {
        spark.Spark.stop();

        try {
            Thread.sleep(1000); // espera a que Spark libere el puerto y rutas
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

        // Esperar a que aparezca el mensaje de error
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("error")));

        // Validar que el texto del error sea exactamente el esperado
        assertEquals("El peso no puede ser negativo", error.getText());
    }
}

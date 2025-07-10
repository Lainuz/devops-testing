package com.ejemplo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsuarioTest {

    @Test
    void actualizarPeso_deberiaActualizarCorrectamente() {
        Usuario u = new Usuario("Paulino", 85.0);
        u.actualizarPeso(87.0);
        assertEquals(87.0, u.getPeso(), "El peso debería actualizarse al valor nuevo");
    }
}

package com.ejemplo;

import static spark.Spark.*;

public class Servidor {
    public static void iniciar() {
        port(8080);

        get("/", (req, res) -> """
                    <html>
                    <body>
                        <form method='post'>
                            Nombre: <input name='nombre' /><br/>
                            Peso: <input name='peso' /><br/>
                            <button type='submit'>Enviar</button>
                        </form>
                    </body>
                    </html>
                """);

        post("/", (req, res) -> {
            String nombre = req.queryParams("nombre");
            String pesoStr = req.queryParams("peso");

            try {
                double peso = Double.parseDouble(pesoStr);
                Usuario usuario = new Usuario(nombre, peso);

                return """
                            <html>
                            <body>
                                <p id='nombre'>Nombre: %s</p>
                                <p id='peso'>Peso: %.1f</p>
                            </body>
                            </html>
                        """.formatted(usuario.getNombre(), usuario.getPeso());

            } catch (NumberFormatException e) {
                return """
                            <html>
                            <body>
                                <p class='error'>Error: El peso debe ser un número válido.</p>
                                <a href="/">Volver</a>
                            </body>
                            </html>
                        """;
            } catch (IllegalArgumentException e) {
                return """
                            <html>
                            <body>
                                <p class='error'>%s</p>
                                <a href="/">Volver</a>
                            </body>
                            </html>
                        """.formatted(e.getMessage());
            }
        });
    }
}

package com.ejemplo;

public class Usuario {

    private String nombre;
    private double peso;

    public Usuario(String nombre, double peso) {
        if (peso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.nombre = nombre;
        this.peso = peso;
    }

    public void actualizarPeso(double nuevoPeso) {
        if (nuevoPeso < 0) {
            throw new IllegalArgumentException("El peso no puede ser negativo");
        }
        this.peso = nuevoPeso;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPeso() {
        return peso;
    }

}

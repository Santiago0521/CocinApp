package com.example.cocinapp;

import android.graphics.Bitmap;

public class Receta {
    private String titulo;
    private String ingredientes;
    private String procedimiento;
    private byte[] imagen;  // Cambiar el tipo a byte[]

    // Constructor
    public Receta(String titulo, String ingredientes, String procedimiento, byte[] imagen) {
        this.titulo = titulo;
        this.ingredientes = ingredientes;
        this.procedimiento = procedimiento;
        this.imagen = imagen;
    }

    // Métodos getter y setter
    public String getTitulo() {
        return titulo;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public byte[] getImagen() {
        return imagen;
    }
}

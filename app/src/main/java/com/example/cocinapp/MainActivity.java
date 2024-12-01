package com.example.cocinapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar los botones en el XML
        Button agregarButton = findViewById(R.id.button);
        Button visualizarButton = findViewById(R.id.button1);

        // Configurar el listener para el botón "Agregar"
        agregarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para navegar a Agregar.java
                Intent intent = new Intent(MainActivity.this, Agregar.class);
                startActivity(intent);
            }
        });

        // Configurar el listener para el botón "Visualizar"
        visualizarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un Intent para navegar a Visualizar.java
                Intent intent = new Intent(MainActivity.this, Visualizar.class);
                startActivity(intent);
            }
        });
    }
}

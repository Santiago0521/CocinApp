package com.example.cocinapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Visualizar extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecetasAdapter recetasAdapter;
    private ArrayList<Receta> listaRecetas;
    private Button volverButton;  // Declaramos el botón volver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listaRecetas = obtenerRecetas();

        recetasAdapter = new RecetasAdapter(listaRecetas);
        recyclerView.setAdapter(recetasAdapter);

        // Inicializar el botón Volver
        volverButton = findViewById(R.id.button4);

        // Configurar la acción del botón Volver
        volverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finaliza la actividad actual y vuelve a la actividad anterior
                onBackPressed();
            }
        });
    }

    private ArrayList<Receta> obtenerRecetas() {
        ArrayList<Receta> recetas = new ArrayList<>();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        // Consultar todas las recetas
        Cursor cursor = db.query("recetas", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                // Obtener los valores de las columnas
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow("titulo"));
                String ingredientes = cursor.getString(cursor.getColumnIndexOrThrow("ingredientes"));
                String procedimiento = cursor.getString(cursor.getColumnIndexOrThrow("procedimiento"));

                // Obtener los bytes de la imagen almacenada como BLOB
                byte[] imagenBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("imagen"));

                // Crear una nueva receta con los datos obtenidos (pasar imagenBytes en lugar de Bitmap)
                recetas.add(new Receta(titulo, ingredientes, procedimiento, imagenBytes));  // Usar byte[] en lugar de Bitmap

            } while (cursor.moveToNext());
        }

        // Cerrar el cursor y la base de datos
        cursor.close();
        db.close();

        return recetas;
    }
}

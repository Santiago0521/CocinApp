package com.example.cocinapp;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.IOException;

public class Agregar extends Activity {

    private EditText editTextTitulo, editTextIngredientes, editTextProcedimiento;
    private ImageView recetaImageView;
    private Button buttonGuardar, buttonCerrar, buttonFoto;
    private static final int PICK_IMAGE_REQUEST = 1;  // Código para la actividad de selección de imagen
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        // Inicializando las vistas
        editTextTitulo = findViewById(R.id.editTextText);
        editTextIngredientes = findViewById(R.id.editTextText2);
        editTextProcedimiento = findViewById(R.id.editTextProcedimiento);
        recetaImageView = findViewById(R.id.recetaImageView);
        buttonGuardar = findViewById(R.id.button2);
        buttonCerrar = findViewById(R.id.button3);
        buttonFoto = findViewById(R.id.button7);

        // Configuración del botón de guardar
        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarReceta();
            }
        });

        // Configuración del botón de cerrar
        buttonCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra la actividad
            }
        });

        // Configuración del botón de seleccionar foto
        buttonFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarFoto();
            }
        });
    }

    private void guardarReceta() {
        // Obtener los datos de los campos
        String titulo = editTextTitulo.getText().toString().trim();
        String ingredientes = editTextIngredientes.getText().toString().trim();
        String procedimiento = editTextProcedimiento.getText().toString().trim();

        // Validar que no estén vacíos los campos requeridos
        if (titulo.isEmpty() || ingredientes.isEmpty() || procedimiento.isEmpty()) {
            Toast.makeText(Agregar.this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el Bitmap de la URI de la imagen
        Bitmap imagenBitmap = null;
        if (imageUri != null) {
            try {
                // Convertir la imagen URI a Bitmap
                imagenBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Guardar la receta en la base de datos
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.insertarReceta(titulo, ingredientes, procedimiento, imagenBitmap);

        // Mostrar un mensaje de éxito
        Toast.makeText(Agregar.this, "Receta guardada exitosamente", Toast.LENGTH_SHORT).show();

        // Limpiar los campos después de guardar
        editTextTitulo.setText("");
        editTextIngredientes.setText("");
        editTextProcedimiento.setText("");
        recetaImageView.setImageResource(0);  // Esto eliminará cualquier imagen del ImageView
    }


    private void seleccionarFoto() {
        // Iniciar un Intent para seleccionar una imagen desde la galería
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Verificar si la imagen se seleccionó correctamente
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            recetaImageView.setImageURI(imageUri); // Mostrar la imagen seleccionada en el ImageView
        }
    }
}

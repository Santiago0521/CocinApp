package com.example.cocinapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cocinapp.db";
    private static final int DATABASE_VERSION = 1;

    // Nombre de la tabla y columnas
    public static final String TABLE_RECETAS = "recetas";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITULO = "titulo";
    public static final String COLUMN_INGREDIENTES = "ingredientes";
    public static final String COLUMN_PROCEDIMIENTO = "procedimiento";
    public static final String COLUMN_IMAGEN = "imagen";

    // Sentencia para crear la tabla
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_RECETAS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITULO + " TEXT, " +
                    COLUMN_INGREDIENTES + " TEXT, " +
                    COLUMN_PROCEDIMIENTO + " TEXT, " +
                    COLUMN_IMAGEN + " BLOB);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE); // Crear la tabla si no existe
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECETAS);
        onCreate(db);
    }

    // Método para insertar una receta
    public void insertarReceta(String titulo, String ingredientes, String procedimiento, Bitmap imagenBitmap) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITULO, titulo);
        values.put(COLUMN_INGREDIENTES, ingredientes);
        values.put(COLUMN_PROCEDIMIENTO, procedimiento);

        // Convertir el Bitmap a un array de bytes (BLOB)
        if (imagenBitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            imagenBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            values.put(COLUMN_IMAGEN, byteArray);  // Insertar los bytes de la imagen en la base de datos
        }

        db.insert(TABLE_RECETAS, null, values);
        db.close();
    }

    // Método para obtener todas las recetas
    public ArrayList<Receta> getAllRecetas() {
        ArrayList<Receta> recetas = new ArrayList<>();

        // Acceder a la base de datos
        SQLiteDatabase db = this.getReadableDatabase();

        // Obtener todas las recetas
        Cursor cursor = db.query(TABLE_RECETAS, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Obtener los valores de cada columna
                String titulo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITULO));
                String ingredientes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTES));
                String procedimiento = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PROCEDIMIENTO));

                // Obtener los bytes de la imagen
                byte[] imagenBytes = cursor.getBlob(cursor.getColumnIndexOrThrow(COLUMN_IMAGEN));

                // Crear un objeto Receta y agregarlo a la lista
                recetas.add(new Receta(titulo, ingredientes, procedimiento, imagenBytes));
            } while (cursor.moveToNext());

            cursor.close();  // Asegúrate de cerrar el cursor después de usarlo
        }

        db.close();  // Cerrar la base de datos

        return recetas;
    }


    // Método para obtener una imagen de la base de datos a partir de un ID de receta
    public Bitmap getImagenFromDB(int recetaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RECETAS, new String[]{COLUMN_IMAGEN}, COLUMN_ID + "=?",
                new String[]{String.valueOf(recetaId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex(COLUMN_IMAGEN));
            cursor.close();  // Asegúrate de cerrar el cursor después de usarlo

            // Convertir los bytes en un Bitmap
            return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        } else {
            cursor.close();
            return null;  // Si no se encuentra la receta, retornar null
        }
    }
}

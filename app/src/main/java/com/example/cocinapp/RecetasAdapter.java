package com.example.cocinapp;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecetasAdapter extends RecyclerView.Adapter<RecetasAdapter.RecetaViewHolder> {

    private ArrayList<Receta> listaRecetas;

    public RecetasAdapter(ArrayList<Receta> listaRecetas) {
        this.listaRecetas = listaRecetas;
    }

    @Override
    public RecetaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receta, parent, false);
        return new RecetaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecetaViewHolder holder, int position) {
        Receta receta = listaRecetas.get(position);

        // Seteamos el título, ingredientes y procedimiento
        holder.tituloTextView.setText(receta.getTitulo());
        holder.ingredientesTextView.setText(receta.getIngredientes());
        holder.procedimientoTextView.setText(receta.getProcedimiento());

        // Convertimos los bytes a Bitmap para mostrar la imagen
        if (receta.getImagen() != null) {
            Bitmap imagenBitmap = BitmapFactory.decodeByteArray(receta.getImagen(), 0, receta.getImagen().length);
            holder.imagenImageView.setImageBitmap(imagenBitmap); // Asignamos la imagen al ImageView
        } else {
            holder.imagenImageView.setImageResource(R.drawable.ic_launcher_background); // Imagen por defecto si no existe
        }
    }

    @Override
    public int getItemCount() {
        return listaRecetas.size();
    }

    // ViewHolder para cada item del RecyclerView
    public static class RecetaViewHolder extends RecyclerView.ViewHolder {
        TextView tituloTextView;
        TextView ingredientesTextView;
        TextView procedimientoTextView;
        ImageView imagenImageView;

        public RecetaViewHolder(View itemView) {
            super(itemView);
            tituloTextView = itemView.findViewById(R.id.tituloReceta);
            ingredientesTextView = itemView.findViewById(R.id.ingredientesReceta);
            procedimientoTextView = itemView.findViewById(R.id.procedimientoReceta);
            imagenImageView = itemView.findViewById(R.id.imagenImageView);
        }
    }
}

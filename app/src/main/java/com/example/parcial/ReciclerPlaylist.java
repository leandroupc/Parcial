package com.example.parcial;

import android.content.Context;
import android.os.strictmode.Violation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parcial.Modelo.PlayList;

import java.util.List;

public class ReciclerPlaylist extends RecyclerView.Adapter<ReciclerPlaylist.ViewHolder> {
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txt_nombre, txt_artista;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nombre = (TextView) itemView.findViewById(R.id.nombre);

        }
    }
    public List<PlayList> lista;
    public Context context;

    public ReciclerPlaylist(List<PlayList> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final PlayList playList = lista.get(position);
        holder.txt_nombre.setText(playList.getNombre()+"-"+ playList.getArtista());

    }
    @Override
    public int getItemCount() {
        return lista.size();
    }
}

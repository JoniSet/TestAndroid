package com.example.testandroiddeveloper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.testandroiddeveloper.Model.DataPokemon;
import com.example.testandroiddeveloper.R;

import java.util.ArrayList;

public class AdapterPokemon extends RecyclerView.Adapter<AdapterPokemon.ViewHolder> {
    private Context context;
    private ArrayList<DataPokemon> dataPokemon;

    public AdapterPokemon(Context context, ArrayList<DataPokemon> dataPokemon) {
        this.context        = context;
        this.dataPokemon    = dataPokemon;
    }


    @NonNull
    @Override
    public AdapterPokemon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_pokemon, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPokemon.ViewHolder holder, int position) {
        DataPokemon data    = dataPokemon.get(position);
//        Glide.with(context)
//                .load(data.url)
//                .placeholder(R.drawable.image_default)
//                .into(holder.img_pokemon);
    }

    @Override
    public int getItemCount() {
        return dataPokemon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView img_pokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_pokemon     = itemView.findViewById(R.id.img_pokemon);
        }
    }
}

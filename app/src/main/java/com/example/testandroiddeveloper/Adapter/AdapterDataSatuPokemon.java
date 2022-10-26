package com.example.testandroiddeveloper.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testandroiddeveloper.Model.DataCiriPokemon;
import com.example.testandroiddeveloper.R;

import java.util.ArrayList;

public class AdapterDataSatuPokemon extends RecyclerView.Adapter<AdapterDataSatuPokemon.ViewHolder> {
    private Context context;
    private ArrayList<DataCiriPokemon> dataPokemon;
    public static ClickCiri clickCiri;

    public AdapterDataSatuPokemon(Context context, ArrayList<DataCiriPokemon> dataPokemon) {
        this.context        = context;
        this.dataPokemon    = dataPokemon;
    }


    @NonNull
    @Override
    public AdapterDataSatuPokemon.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_ciri, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDataSatuPokemon.ViewHolder holder, int position) {
        DataCiriPokemon data    = dataPokemon.get(position);
        holder.txt_pokemon.setText(data.getCiri());
    }

    @Override
    public int getItemCount() {
        return dataPokemon.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView txt_pokemon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_pokemon     = itemView.findViewById(R.id.txt_pokemon);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickCiri.onClick(view, getAdapterPosition());
        }
    }

    public interface ClickCiri{
        void onClick(View v, int position);
    }

    public void onClickListener(ClickCiri clickCiri){
        AdapterDataSatuPokemon.clickCiri    = clickCiri;
    }
}

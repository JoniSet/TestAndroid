package com.example.testandroiddeveloper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.testandroiddeveloper.Adapter.AdapterCiriPokemon;
import com.example.testandroiddeveloper.Model.DataCiriPokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<DataCiriPokemon> dataPokemon  = new ArrayList<>();
    private RecyclerView list_pokemon;
    int pastVisiblesItems, visibleItemCount, totalItemCount, totalData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_pokemon        = findViewById(R.id.list_ciri_pokemon);
        list_pokemon.setLayoutManager(new LinearLayoutManager(this));
        list_pokemon.setItemAnimator(new DefaultItemAnimator());
        list_pokemon.setHasFixedSize(true);

        AndroidNetworking.initialize(this);

        AndroidNetworking.get("https://pokeapi.co/api/v2/")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray title     = response.names();
                        for (int i = 0; i < title.length(); i++){
                            try {
                                String url  = title.getString(i);
                                dataPokemon.add(new DataCiriPokemon(url, response.getString(url)));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        AdapterCiriPokemon adapterPokemon   = new AdapterCiriPokemon(MainActivity.this, dataPokemon);
                        adapterPokemon.onClickListener(new AdapterCiriPokemon.ClickCiri() {
                            @Override
                            public void onClick(View v, int position) {
                                Intent intent   = new Intent(MainActivity.this, DataPokemonActivity.class);
                                intent.putExtra("url", dataPokemon.get(position).getUrl());
                                startActivity(intent);
                            }
                        });
                        list_pokemon.setAdapter(adapterPokemon);
                        LinearLayoutManager mLayoutManager = (LinearLayoutManager) list_pokemon.getLayoutManager();
                        list_pokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
                            @Override
                            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                super.onScrollStateChanged(recyclerView, newState);
                            }

                            @Override
                            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                super.onScrolled(recyclerView, dx, dy);
                                if (dy > 0) { //check for scroll down
                                    visibleItemCount = mLayoutManager.getChildCount();
                                    totalItemCount = mLayoutManager.getItemCount();
                                    pastVisiblesItems = mLayoutManager.findLastVisibleItemPosition();

                                    Log.d("item", pastVisiblesItems + "");
//                                    if (loading) {
//                                        if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
//                                            loading = false;
//                                            Log.v("...", "Last Item Wow !");
//                                            // Do pagination.. i.e. fetch new data
//
//                                            loading = true;
//                                        }
//                                    }
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("hasil", anError.toString());
                    }
                });
    }
}
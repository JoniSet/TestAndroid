package com.example.testandroiddeveloper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.testandroiddeveloper.Adapter.AdapterCiriPokemon;
import com.example.testandroiddeveloper.Adapter.AdapterDataSatuPokemon;
import com.example.testandroiddeveloper.Model.DataCiriPokemon;
import com.example.testandroiddeveloper.Model.DataPokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;

public class DataPokemonActivity extends AppCompatActivity {
    private ArrayList<DataCiriPokemon> dataPokemon  = new ArrayList<>();
    private ArrayList<DataCiriPokemon> dataCari     = new ArrayList<>();
    private RecyclerView list_data_pokemon, list_cari_pokemon;
    private ProgressBar loading;
    private Button btn_previous, btn_next;
    private EditText edt_cari;
    int pastVisiblesItems, totalItemCount, totalData;
    String next, previous;

    boolean cari = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pokemon);

        loading             = findViewById(R.id.loading);
        btn_previous        = findViewById(R.id.btn_previous);
        btn_next            = findViewById(R.id.btn_next);
        edt_cari            = findViewById(R.id.edt_cari);

        list_data_pokemon   = findViewById(R.id.list_data_pokemon);
        list_data_pokemon.setLayoutManager(new LinearLayoutManager(this));
        list_data_pokemon.setItemAnimator(new DefaultItemAnimator());
        list_data_pokemon.setHasFixedSize(true);

        list_cari_pokemon   = findViewById(R.id.list_cari_pokemon);
        list_cari_pokemon.setLayoutManager(new LinearLayoutManager(this));
        list_cari_pokemon.setItemAnimator(new DefaultItemAnimator());
        list_cari_pokemon.setHasFixedSize(true);

        loading.setVisibility(View.VISIBLE);
        AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loading.setVisibility(View.GONE);
                            totalData   = Integer.parseInt(response.getString("count"));
                            next        = response.getString("next");
                            previous    = response.getString("previous");

                            if (next.equals("null")){
                                btn_next.setVisibility(View.GONE);
                            }
                            else{
                                btn_next.setVisibility(View.VISIBLE);
                            }

                            if (previous.equals("null")){
                                btn_previous.setVisibility(View.GONE);
                            }
                            else{
                                btn_previous.setVisibility(View.VISIBLE);
                            }

                            JSONArray results       = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++){
                                JSONObject object   = results.getJSONObject(i);
                                DataCiriPokemon data= new DataCiriPokemon(object.getString("name"), object.getString("url"));
                                dataPokemon.add(data);
                            }

                            AdapterDataSatuPokemon adapterDataSatuPokemon   = new AdapterDataSatuPokemon(DataPokemonActivity.this, dataPokemon);
                            list_data_pokemon.setAdapter(adapterDataSatuPokemon);

                            LinearLayoutManager mLayoutManager = (LinearLayoutManager) list_data_pokemon.getLayoutManager();
                            adapterDataSatuPokemon.onClickListener(new AdapterDataSatuPokemon.ClickCiri() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent   = new Intent(DataPokemonActivity.this, DetailPokemonActivity.class);
                                    intent.putExtra("url", dataPokemon.get(position).getUrl());
                                    startActivity(intent);
                                }
                            });

                            list_data_pokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                }

                                @Override
                                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                    pastVisiblesItems = mLayoutManager.findLastVisibleItemPosition();
                                    if (dy > 0){
                                        if (dataPokemon.size() - 1 == pastVisiblesItems){
                                            nextData();
                                        }
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.setVisibility(View.GONE);
                        Log.d("hasil", anError.toString());
                    }
                });



        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextData();
            }
        });

        btn_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                previousData();
            }
        });

        edt_cari.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    InputMethodManager inputManager = (InputMethodManager) DataPokemonActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(DataPokemonActivity.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

                    cari = !cari;
                    list_data_pokemon.setVisibility(View.GONE);
                    list_cari_pokemon.setVisibility(View.VISIBLE);

                    setDataCari();

                    return true;
                }
                return false;
            }
        });

    }

    private void setDataCari() {
        ArrayList<DataCiriPokemon> matches = new ArrayList<DataCiriPokemon>();
        // go through list of members and compare name with given name
        for(DataCiriPokemon member : dataPokemon) {
            if (member.getCiri().equals(edt_cari.getText().toString())) {
                matches.add(member);
            }
        }
        AdapterDataSatuPokemon adapterDataSatuPokemon   = new AdapterDataSatuPokemon(DataPokemonActivity.this, matches);
        list_cari_pokemon.setAdapter(adapterDataSatuPokemon);
        adapterDataSatuPokemon.onClickListener(new AdapterDataSatuPokemon.ClickCiri() {
            @Override
            public void onClick(View v, int position) {
                Intent intent   = new Intent(DataPokemonActivity.this, DetailPokemonActivity.class);
                intent.putExtra("url", matches.get(position).getUrl());
                startActivity(intent);
            }
        });
        edt_cari.setText("");
    }

    public void nextData(){
        loading.setVisibility(View.VISIBLE);
        AndroidNetworking.get(next)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loading.setVisibility(View.GONE);
                            totalData   = Integer.parseInt(response.getString("count"));
                            next        = response.getString("next");
                            previous    = response.getString("previous");

                            JSONArray results       = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++){
                                JSONObject object   = results.getJSONObject(i);
                                DataCiriPokemon data= new DataCiriPokemon(object.getString("name"), object.getString("url"));
                                dataPokemon.add(data);
                            }

                            AdapterDataSatuPokemon adapterDataSatuPokemon   = new AdapterDataSatuPokemon(DataPokemonActivity.this, dataPokemon);
                            list_data_pokemon.setAdapter(adapterDataSatuPokemon);
                            adapterDataSatuPokemon.onClickListener(new AdapterDataSatuPokemon.ClickCiri() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent   = new Intent(DataPokemonActivity.this, DetailPokemonActivity.class);
                                    intent.putExtra("url", dataPokemon.get(position).getUrl());
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.setVisibility(View.GONE);
                        Log.d("hasil", anError.toString());
                    }
                });
    }

    public void previousData(){
        loading.setVisibility(View.VISIBLE);
        list_data_pokemon.setAdapter(null);
        dataPokemon.clear();
        AndroidNetworking.get(previous)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            loading.setVisibility(View.GONE);
                            totalData   = Integer.parseInt(response.getString("count"));
                            next        = response.getString("next");
                            previous    = response.getString("previous");

                            if (next.equals("null")){
                                btn_next.setVisibility(View.GONE);
                            }
                            else{
                                btn_next.setVisibility(View.VISIBLE);
                            }

                            if (previous.equals("null")){
                                btn_previous.setVisibility(View.GONE);
                            }
                            else{
                                btn_previous.setVisibility(View.VISIBLE);
                            }

                            JSONArray results       = response.getJSONArray("results");
                            for (int i = 0; i < results.length(); i++){
                                JSONObject object   = results.getJSONObject(i);
                                DataCiriPokemon data= new DataCiriPokemon(object.getString("name"), object.getString("url"));
                                dataPokemon.add(data);
                            }

                            AdapterDataSatuPokemon adapterDataSatuPokemon   = new AdapterDataSatuPokemon(DataPokemonActivity.this, dataPokemon);
                            list_data_pokemon.setAdapter(adapterDataSatuPokemon);
                            adapterDataSatuPokemon.onClickListener(new AdapterDataSatuPokemon.ClickCiri() {
                                @Override
                                public void onClick(View v, int position) {
                                    Intent intent   = new Intent(DataPokemonActivity.this, DetailPokemonActivity.class);
                                    intent.putExtra("url", dataPokemon.get(position).getUrl());
                                    startActivity(intent);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        loading.setVisibility(View.GONE);
                        Log.d("hasil", anError.toString());
                    }
                });
    }

    @Override
    public void onBackPressed() {
        cari = !cari;
        list_data_pokemon.setVisibility(View.VISIBLE);
        list_cari_pokemon.setVisibility(View.GONE);
        dataCari.clear();
        list_cari_pokemon.setAdapter(null);
    }
}
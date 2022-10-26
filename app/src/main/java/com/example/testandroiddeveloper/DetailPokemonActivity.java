package com.example.testandroiddeveloper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interceptors.GzipRequestInterceptor;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.testandroiddeveloper.Adapter.AdapterDataSatuPokemon;
import com.example.testandroiddeveloper.Model.DataCiriPokemon;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;

public class DetailPokemonActivity extends AppCompatActivity {

    private ImageView img_pokemon, img1, img2, img3;
    private TextView txt_nama, txt_weight, txt_height;

    String url_image, url1, url2, url3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pokemon);


        initView();
    }

    private void initView() {
        img_pokemon     = findViewById(R.id.img_pokemon);
        img1            = findViewById(R.id.img1);
        img2            = findViewById(R.id.img2);
        img3            = findViewById(R.id.img3);
        txt_nama        = findViewById(R.id.txt_nama);
        txt_weight      = findViewById(R.id.txt_weight);
        txt_height      = findViewById(R.id.txt_height);

        img_pokemon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url_image.isEmpty()){
                    Dialog dialog       = new Dialog(DetailPokemonActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_gambar);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                    Button img_share        = dialog.findViewById(R.id.btn_share);
                    ImageView img_pokemon   = dialog.findViewById(R.id.img_pokemon);

                    Glide.with(DetailPokemonActivity.this)
                            .load(url_image)
                            .into(img_pokemon);

                    img_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent shareIntent = ShareCompat.IntentBuilder.from(DetailPokemonActivity.this)
                                    .setType("text/plain")
                                    .setText(url_image)
                                    .getIntent();
                            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(shareIntent);
                            }
                        }
                    });
                }
            }
        });

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url_image.isEmpty()){
                    Dialog dialog       = new Dialog(DetailPokemonActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_gambar);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                    Button img_share        = dialog.findViewById(R.id.btn_share);
                    ImageView img_pokemon   = dialog.findViewById(R.id.img_pokemon);

                    Glide.with(DetailPokemonActivity.this)
                            .load(url1)
                            .into(img_pokemon);

                    img_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent shareIntent = ShareCompat.IntentBuilder.from(DetailPokemonActivity.this)
                                    .setType("text/plain")
                                    .setText(url1)
                                    .getIntent();
                            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(shareIntent);
                            }
                        }
                    });
                }
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url_image.isEmpty()){
                    Dialog dialog       = new Dialog(DetailPokemonActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_gambar);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                    Button img_share        = dialog.findViewById(R.id.btn_share);
                    ImageView img_pokemon   = dialog.findViewById(R.id.img_pokemon);

                    Glide.with(DetailPokemonActivity.this)
                            .load(url2)
                            .into(img_pokemon);

                    img_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent shareIntent = ShareCompat.IntentBuilder.from(DetailPokemonActivity.this)
                                    .setType("text/plain")
                                    .setText(url2)
                                    .getIntent();
                            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(shareIntent);
                            }
                        }
                    });
                }
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!url_image.isEmpty()){
                    Dialog dialog       = new Dialog(DetailPokemonActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_gambar);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());
                    lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                    dialog.getWindow().setAttributes(lp);
                    dialog.show();

                    Button img_share        = dialog.findViewById(R.id.btn_share);
                    ImageView img_pokemon   = dialog.findViewById(R.id.img_pokemon);

                    Glide.with(DetailPokemonActivity.this)
                            .load(url3)
                            .into(img_pokemon);

                    img_share.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent shareIntent = ShareCompat.IntentBuilder.from(DetailPokemonActivity.this)
                                    .setType("text/plain")
                                    .setText(url3)
                                    .getIntent();
                            if (shareIntent.resolveActivity(getPackageManager()) != null) {
                                startActivity(shareIntent);
                            }
                        }
                    });
                }
            }
        });

        setData();
    }

    private void setData() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .addInterceptor(new GzipRequestInterceptor())
                .build();

        AndroidNetworking.get(getIntent().getStringExtra("url"))
                .setPriority(Priority.LOW)
                .setOkHttpClient(okHttpClient)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            txt_nama.setText("Name : " + response.getString("name"));
                            txt_weight.setText("Weight : " + response.getString("weight"));
                            txt_height.setText("Height : " + response.getString("height"));

                            String a            = response.getString("sprites");
                            JSONObject sprites  = new JSONObject(a);
                            String b            = sprites.getString("other");
                            JSONObject other    = new JSONObject(b);
                            String c            = other.getString("official-artwork");
                            JSONObject home     = new JSONObject(c);

                            url_image           = home.getString("front_default");
                            url1                = sprites.getString("back_default");
                            url2                = sprites.getString("back_shiny");
                            url3                = sprites.getString("front_default");

                            Glide.with(DetailPokemonActivity.this)
                                    .load(home.getString("front_default"))
                                    .into(img_pokemon);

                            Glide.with(DetailPokemonActivity.this)
                                    .load(sprites.getString("back_default"))
                                    .into(img1);

                            Glide.with(DetailPokemonActivity.this)
                                    .load(sprites.getString("back_shiny"))
                                    .into(img2);

                            Glide.with(DetailPokemonActivity.this)
                                    .load(sprites.getString("front_default"))
                                    .into(img3);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("hasil", anError.toString());
                    }
                });
    }


}
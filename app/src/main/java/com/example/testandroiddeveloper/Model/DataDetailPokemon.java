package com.example.testandroiddeveloper.Model;

import java.util.ArrayList;

public class DataDetailPokemon {
    String id, height, name, weight, foto;
    ArrayList<DataForms> dataForms;

    public DataDetailPokemon(String id, String height, String name, String weight, String foto, ArrayList<DataForms> dataForms) {
        this.id = id;
        this.height = height;
        this.name = name;
        this.weight = weight;
        this.foto = foto;
        this.dataForms = dataForms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public ArrayList<DataForms> getDataForms() {
        return dataForms;
    }

    public void setDataForms(ArrayList<DataForms> dataForms) {
        this.dataForms = dataForms;
    }
}
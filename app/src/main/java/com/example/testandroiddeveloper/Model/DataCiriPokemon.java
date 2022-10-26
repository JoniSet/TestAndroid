package com.example.testandroiddeveloper.Model;

public class DataCiriPokemon {
    String Ciri, url;

    public DataCiriPokemon(String ciri, String url) {
        Ciri = ciri;
        this.url = url;
    }

    public String getCiri() {
        return Ciri;
    }

    public void setCiri(String ciri) {
        Ciri = ciri;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

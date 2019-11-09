package com.example.noticias;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class Pais {
    private String titulo;
    private String subtitulo;
    private String url;

    public Pais() throws JSONException {
        //titulo = a.getString("Name").toString();
        subtitulo = "";
        //url = "http://www.geognos.com/api/en/countries/flag/"+a.getString("iso2").toString();




    }

    public static ArrayList<Pais> JsonObjectsBuild(JSONArray datos) throws JSONException {
        ArrayList<Pais> noticias = new ArrayList<>();

        /*JSONObject jresults = datos.getJSONObject("Results");
        Iterator<?> iterator = jresults.keys();
        while (iterator.hasNext()){

            noticias.add(new Pais(datos.getJSONObject(i)));
        }*/
        return noticias;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

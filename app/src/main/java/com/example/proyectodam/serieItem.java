package com.example.proyectodam;

import android.graphics.Bitmap;

public class serieItem {
    private int id;
    private Bitmap caratula;
    private String titulo;
    private String genero;
    private String estado;
    private String tipo;


    //#region   GETTER
    public Bitmap getImagen(){
        return caratula;
    }
    public String getTitulo(){
        return titulo;
    }
    public String getGenero(){
        return genero;
    }
    public String getEstado(){return estado;}
    public String getTipo(){
        return tipo;
    }
    public int getID(){
        return id;
    }
    //#endregion

    //#region   SETTER
    public void setImagen(Bitmap imagen){
        this.caratula = caratula;
    }
    public void setTitulo(String titulo){
        this.titulo = titulo;
    }
    public void setGenero(String genero){this.genero = genero;}
    public void setEstado(String estado){
        this.estado = estado;
    }
    public void setTipo(String tipo){
        this.tipo = tipo;
    }
    public void setID(int id){
        this.id = id;
    }
    //#endregion

    public serieItem(String titulo, String genero, String estado, String tipo){
        this.titulo = titulo;
        this.genero = genero;
        this.estado = estado;
        if (Integer.parseInt(tipo) == 1){
            this.tipo = "Anime";
        }
        else if(Integer.parseInt(tipo) == 2){
            this.tipo = "Serie";
        }
        else{
            this.tipo = "Pelicula";
        }
    }

    public serieItem(int id, String estado, String genero, String tipo, String titulo){
        this.id = id;
        this.titulo = titulo;
        this.genero = genero;
        this.estado = estado;
        if (Integer.parseInt(tipo) == 1){
            this.tipo = "Anime";
        }
        else if(Integer.parseInt(tipo) == 2){
            this.tipo = "Serie";
        }
        else{
            this.tipo = "Pelicula";
        }
    }
}
package com.example.proyectodam;

import android.graphics.Bitmap;

public class serieItem {
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
    //#endregionD

    public serieItem(String titulo, String genero, String estado, String tipo){
        this.titulo = titulo;
        this.genero = genero;
        this.estado = estado;
        this.tipo = tipo;
    }
}
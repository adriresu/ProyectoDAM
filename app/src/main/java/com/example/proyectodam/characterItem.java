package com.example.proyectodam;

import android.graphics.Bitmap;

public class characterItem {
    private int id;
    private Bitmap imagen;
    private String nombre;
    private String apellidos;
    private Integer edad;
    private String poder;
    private String actor;
    private String personalidad;
    private String origen;
    private String descripcion;
    private int idSerie;

    //#region   GETTER
    public int getID(){
        return id;
    }
    public Bitmap getImagen(){
        return imagen;
    }
    public String getNombre(){
        return nombre;
    }
    public String getApellidos(){
        return apellidos;
    }
    public int getEdad(){return edad;}
    public String getPoder(){
        return poder;
    }
    public String getActorn(){
        return actor;
    }
    public String getPersonalidad(){
        return personalidad;
    }
    public String getOrigen(){
        return origen;
    }
    public String getDescripcion(){return descripcion;}
    public int getIdSerie(){
        return idSerie;
    }
    //#endregion

    //#region   SETTER
    public void setCharacterImagen(Bitmap imagen){
        this.imagen = imagen;
    }
    public void setNombre(String titulo){
        this.nombre = nombre;
    }
    public void setApellidos(String genero){this.apellidos = apellidos;}
    public void setEdad(String estado){
        this.edad = edad;
    }
    public void setPoder(Bitmap imagen){
        this.poder = poder;
    }
    public void setActor(String titulo){
        this.actor = actor;
    }
    public void setPersonalidad(String genero){this.personalidad = personalidad;}
    public void setOrigen(String estado){
        this.origen = origen;
    }
    public void setDescripcion(String tipo){
        this.descripcion = descripcion;
    }
    public void setID(int id){
        this.id = id;
    }
    public void setIDserie(int idSerie){
        this.idSerie = idSerie;
    }
    //#endregion

    public characterItem(int id, String nombre, String apellidos, int edad, String poder, String actor, String personalidad, String origen, String descripcion, int idSerie){
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.poder = poder;
        this.actor = actor;
        this.personalidad = personalidad;
        this.origen = origen;
        this.descripcion = descripcion;
        this.idSerie = idSerie;
    }
    public characterItem(int id, String nombreCharacter, String apellidosCharacter, int edad, String poderCharacter, String actorCharacter, String personalidadCharacter, String origenCharacter, String descripcionCharacter) {
        this.id = id;
        this.nombre = nombreCharacter;
        this.apellidos = apellidosCharacter;
        this.edad = edad;
        this.poder = poderCharacter;
        this.actor = actorCharacter;
        this.personalidad = personalidadCharacter;
        this.origen = origenCharacter;
        this.descripcion = descripcionCharacter;
    }
}
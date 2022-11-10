package com.example.proyectodam;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class adaptadorCharacter extends BaseAdapter {
    private Context Context;
    private ArrayList<characterItem> listaCharacters;
    private LayoutInflater Inflater;


    private int id;
    private Bitmap imagen;
    private String nombre;
    private String apellidos;
    private String personalidad;
    private String origen;

    public adaptadorCharacter(Activity Context, ArrayList<characterItem> listaCharacters) {
        this.Context = Context;
        this.listaCharacters = listaCharacters;
        Inflater = LayoutInflater.from(Context);
    }

    static class ViewHolder{
        TextView nombre;
        TextView apellidos;
        TextView personalidad;
        TextView origen;
        ImageView imagen;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder Holder = null;
        if(view == null){
            view = Inflater.inflate(R.layout.structuremain, null);
            Holder = new ViewHolder();
            Holder.nombre = (TextView) view.findViewById(R.id.nombreCharacterView);
            Holder.apellidos = (TextView) view.findViewById(R.id.apellidosCharacterView);
            Holder.personalidad = (TextView) view.findViewById(R.id.personalidadCharacterView);
            Holder.origen = (TextView) view.findViewById(R.id.origenCharacterView);
            Holder.imagen = (ImageView) view.findViewById(R.id.imageView);
            view.setTag(Holder);
        }
        else{
            Holder = (ViewHolder) view.getTag();
        }

        characterItem character = listaCharacters.get(position);
        Holder.nombre.setText(character.getNombre());
        Holder.apellidos.setText(character.getApellidos());
        Holder.personalidad.setText(String.valueOf(character.getPersonalidad()));
        Holder.origen.setText(String.valueOf(character.getOrigen()));

        return view;
    }

    @Override
    public int getCount() {
        return listaCharacters.size();
    }

    @Override
    public Object getItem(int i) {
        return listaCharacters.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}

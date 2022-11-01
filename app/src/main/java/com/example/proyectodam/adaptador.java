package com.example.proyectodam;

import android.app.Activity;
import android.content.Context;
import android.media.Image;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;

public class adaptador extends BaseAdapter {
    private Context Context;
    private ArrayList<serieItem> listaSeries;
    private LayoutInflater Inflater;

    public adaptador(Activity Context, ArrayList<serieItem> listaSeries) {
        this.Context = Context;
        this.listaSeries = listaSeries;
        Inflater = LayoutInflater.from(Context);
    }

    static class ViewHolder{
        TextView Titulo;
        TextView Genero;
        TextView Estado;
        TextView Tipo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder Holder = null;
        if(view == null){
            view = Inflater.inflate(R.layout.structuremain, null);
            Holder = new ViewHolder();
            Holder.Titulo = (TextView) view.findViewById(R.id.tituloLV);
            Holder.Genero = (TextView) view.findViewById(R.id.generoLV);
            Holder.Estado = (TextView) view.findViewById(R.id.estadoLV);
            Holder.Tipo = (TextView) view.findViewById(R.id.tipoLV);
            view.setTag(Holder);
        }
        else{
            Holder = (ViewHolder) view.getTag();
        }

        serieItem serie = listaSeries.get(position);
        Holder.Titulo.setText(serie.getTitulo());
        Holder.Genero.setText(serie.getGenero());
        Holder.Estado.setText(String.valueOf(serie.getEstado()));
        Holder.Tipo.setText(String.valueOf(serie.getTipo()));
        return view;
    }

    @Override
    public int getCount() {
        return listaSeries.size();
    }

    @Override
    public Object getItem(int i) {
        return listaSeries.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}

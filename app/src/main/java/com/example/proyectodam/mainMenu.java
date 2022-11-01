package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class mainMenu extends AppCompatActivity {

    public static ArrayList<serieItem> listaSeries = new ArrayList<serieItem>();
    ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ListView listView = (ListView)findViewById(R.id.customListaSeries);
        serieItem serie = new serieItem("El senhor de los ajillos", "Fantasia","Finalizada","Pelicula");
        serieItem serie2 = new serieItem("One Piece", "Fantasia","Emision","Anime");
        serieItem serie3 = new serieItem("La que se avencina", "Comedia","Emision","Serie");
        listaSeries.add(serie);
        listaSeries.add(serie2);
        listaSeries.add(serie3);
        adaptador adaptador = new adaptador(this, listaSeries);
        listView.setAdapter(adaptador);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.headerIconOption1:
                Toast.makeText(mainMenu.this, "Opcion 1 pulsado", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
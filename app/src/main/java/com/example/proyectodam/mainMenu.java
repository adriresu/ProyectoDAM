package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class mainMenu extends AppCompatActivity {

    public static ArrayList<serieItem> listaSeries = new ArrayList<serieItem>();

    //Loggin Info
    String name;
    String password;
    String idUser;
    int defaultType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        name = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
        idUser = getIntent().getStringExtra("IDuser");
    }

    @Override
    public void onStart(){
        super.onStart();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("SERIES");
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradientcabecera));
        ListView listView = (ListView)findViewById(R.id.customListaSeries);
        listaSeries.clear();
        JSONArray array;
        JSONArray jsonArray;
        try {
            array = makeRequest(defaultType);
            jsonArray = (JSONArray)array;
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    JSONObject dataSerie = (JSONObject) jsonArray.get(i);
                    String idSerie = dataSerie.getString("ID");
                    String estadoSerie = dataSerie.getString("Estado");
                    String caratulaSerie = dataSerie.getString("Caratula");
                    String tipoSerie = dataSerie.getString("Tipo");
                    String tituloSerie = dataSerie.getString("Titulo");
                    serieItem serieTemp = new serieItem(Integer.parseInt(idSerie), BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.op), estadoSerie, tipoSerie, tituloSerie);
                    listaSeries.add(serieTemp);
                }
            }
            else{
                Toast.makeText(this, "Any media in the BBDD", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        adaptador adaptador = new adaptador(this, listaSeries);
        listView.setAdapter(adaptador);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mainMenu.this, Serie.class);
                intent.putExtra("user", name);
                intent.putExtra("password", password);
                intent.putExtra("IDserie", Integer.toString(listaSeries.get(position).getID()));
                intent.putExtra("IDuser", idUser);
                startActivity(intent);
            }
        });

    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header1, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int typeMedia = -1;
        ListView listView = (ListView)findViewById(R.id.customListaSeries);
        switch (item.getItemId()){
            case R.id.headerIcon:
                Intent intent = new Intent(mainMenu.this, Profile.class);
                intent.putExtra("user", name);
                intent.putExtra("password", password);
                intent.putExtra("IDuser", idUser);
                startActivity(intent);
                break;
            case R.id.headerIconOption1:
                typeMedia = 0;
                getSupportActionBar().setTitle("SERIES");
                break;
            case R.id.headerIconOption2:
                typeMedia = 1;
                getSupportActionBar().setTitle("FILMS");
                break;
            case R.id.headerIconOption3:
                typeMedia = 2;
                getSupportActionBar().setTitle("ANIMES");
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        listaSeries.clear();
        JSONArray array;
        JSONArray jsonArray;
        try {
            array = makeRequest(typeMedia);
            jsonArray = (JSONArray)array;
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    JSONObject dataSerie = (JSONObject) jsonArray.get(i);
                    String idSerie = dataSerie.getString("ID");
                    String estadoSerie = dataSerie.getString("Estado");
                    String caratulaSerie = dataSerie.getString("Caratula");
                    String tipoSerie = dataSerie.getString("Tipo");
                    String tituloSerie = dataSerie.getString("Titulo");
                    serieItem serieTemp = new serieItem(Integer.parseInt(idSerie), BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.op), estadoSerie, tipoSerie, tituloSerie);
                    listaSeries.add(serieTemp);
                }
            }
            else{
                Toast.makeText(this, "Any media in the BBDD", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
            adaptador adaptador = new adaptador(this, listaSeries);
            listView.setAdapter(adaptador);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(mainMenu.this, Serie.class);
                    intent.putExtra("user", name);
                    intent.putExtra("password", password);
                    intent.putExtra("IDserie", Integer.toString(listaSeries.get(position).getID()));
                    intent.putExtra("IDuser", idUser);
                    startActivity(intent);
                }
            });
        return true;
    }

    private JSONArray makeRequest(int Tipo) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        if (Tipo == 0){
            multipartRequest.addFormField("Tipo", "Series");
        }
        else if(Tipo == 1){
            multipartRequest.addFormField("Tipo", "Peliculas");
        }
        else if(Tipo == 2){
            multipartRequest.addFormField("Tipo", "Animes");
        }

        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
}
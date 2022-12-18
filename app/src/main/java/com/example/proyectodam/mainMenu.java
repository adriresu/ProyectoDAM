package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class mainMenu extends AppCompatActivity {

    public static ArrayList<serieItem> listaSeries = new ArrayList<serieItem>();

    //Loggin Info
    int defaultType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Login.name = getIntent().getStringExtra("user");
        Login.password = getIntent().getStringExtra("password");
        Login.idUser = getIntent().getStringExtra("IDuser");
    }

    @Override
    public void onStart(){
        super.onStart();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.series));
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradientcabecera));

        if (Login.name.equals("")){
            Intent intent = new Intent(mainMenu.this, Login.class);
            startActivity(intent);
        }

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
                    String tipoSerie = dataSerie.getString("Tipo");
                    String tituloSerie = dataSerie.getString("Titulo");

                    byte[] decodedString = Base64.decode(dataSerie.getString("Caratula"), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    serieItem serieTemp = new serieItem(Integer.parseInt(idSerie), decodedByte, estadoSerie, tipoSerie, tituloSerie);
                    listaSeries.add(serieTemp);
                }
            }
            else{
                Toast.makeText(this, R.string.not_media_bd , Toast.LENGTH_SHORT).show();
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
                intent.putExtra("user", Login.name);
                intent.putExtra("password", Login.password);
                intent.putExtra("IDserie", Integer.toString(listaSeries.get(position).getID()));
                intent.putExtra("IDuser", Login.idUser);
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
        boolean passed = false;
        ListView listView = (ListView)findViewById(R.id.customListaSeries);
        switch (item.getItemId()){
            case R.id.headerIcon:
                Intent intent = new Intent(mainMenu.this, Profile.class);
                intent.putExtra("user", Login.name);
                intent.putExtra("password", Login.password);
                intent.putExtra("IDuser", Login.idUser);
                startActivity(intent);
                passed = true;
                break;
            case R.id.headerIconOption1:
                typeMedia = 0;
                getSupportActionBar().setTitle(getResources().getString(R.string.series));
                break;
            case R.id.headerIconOption2:
                typeMedia = 1;
                getSupportActionBar().setTitle(getResources().getString(R.string.films));
                break;
            case R.id.headerIconOption3:
                typeMedia = 2;
                getSupportActionBar().setTitle(getResources().getString(R.string.animes));
                break;
            case R.id.headerIconOption4:
                typeMedia = 3;
                getSupportActionBar().setTitle(getResources().getString(R.string.favourites));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }
        if (!passed){
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
                        String tipoSerie = dataSerie.getString("Tipo");
                        String tituloSerie = dataSerie.getString("Titulo");

                        byte[] decodedString = Base64.decode(dataSerie.getString("Caratula"), Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        serieItem serieTemp = new serieItem(Integer.parseInt(idSerie), decodedByte, estadoSerie, tipoSerie, tituloSerie);
                        listaSeries.add(serieTemp);
                    }
                    if (typeMedia == 3){
                        try {
                            array = makeRequestViewedSeries(Login.idUser);
                            jsonArray = (JSONArray)array;
                            if (jsonArray != null) {
                                int len1 = listaSeries.size();
                                int len2 = jsonArray.length();
                                for (int ii=0;ii<len1;ii++){
                                    boolean fav = false;
                                    for (int i=0;i<len2;i++){
                                        JSONObject dataSerie = (JSONObject) jsonArray.get(i);
                                        String idSerie = dataSerie.getString("ID_serie");
                                        if (listaSeries.get(ii).getID() == Integer.parseInt(idSerie)) {
                                            fav = true;
                                        }
                                    }
                                    if (!fav){
                                        listaSeries.remove(ii);
                                    }
                                    //Object items = listView.getItemAtPosition(listaSeries.get(ii).getID());
                                    //Toast.makeText(this, listaSeries.get(ii).getTitulo(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(this, R.string.not_media_bd , Toast.LENGTH_SHORT).show();
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
                    intent.putExtra("user", Login.name);
                    intent.putExtra("password", Login.password);
                    intent.putExtra("IDserie", Integer.toString(listaSeries.get(position).getID()));
                    intent.putExtra("IDuser", Login.idUser);
                    startActivity(intent);
                }
            });
        };

    return true;
    }

    private JSONArray makeRequest(int Tipo) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180:80", "UTF-8");
        if (Tipo == 0){
            multipartRequest.addFormField("Tipo", "Series");
        }
        else if(Tipo == 1){
            multipartRequest.addFormField("Tipo", "Peliculas");
        }
        else if(Tipo == 2){
            multipartRequest.addFormField("Tipo", "Animes");
        }
        else if(Tipo == 3){
            multipartRequest.addFormField("Tipo", "Favourites");
        }

        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private JSONArray makeRequestViewedSeries(String idUser) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "SeriesViewed");
        multipartRequest.addFormField("id", idUser);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
}
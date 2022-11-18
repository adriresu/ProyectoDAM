package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
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
    ListView listview;

    //Loggin Info
    String name;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        name = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
    }

    @Override
    public void onStart(){
        super.onStart();
        ListView listView = (ListView)findViewById(R.id.customListaSeries);
        listaSeries.clear();
        try {
            JSONArray array =  makeRequest();
            JSONArray jsonArray = (JSONArray)array;
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){

                    JSONObject dataSerie = (JSONObject) jsonArray.get(i);

                    String idSerie = dataSerie.getString("ID");
                    String estadoSerie = dataSerie.getString("Estado");
                    String generoSerie = dataSerie.getString("Genero");
                    String caratulaSerie = dataSerie.getString("Caratula");
                    String tipoSerie = dataSerie.getString("Tipo");
                    String tituloSerie = dataSerie.getString("Titulo");
                    serieItem serieTemp = new serieItem(Integer.parseInt(idSerie), BitmapFactory.decodeResource(this.getApplicationContext().getResources(), R.drawable.op), estadoSerie, generoSerie, tipoSerie, tituloSerie);
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
        switch (item.getItemId()){
            case R.id.headerIconOption1:
                Toast.makeText(mainMenu.this, "Opcion 1 pulsado", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private JSONArray makeRequest() throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "Series");
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
}
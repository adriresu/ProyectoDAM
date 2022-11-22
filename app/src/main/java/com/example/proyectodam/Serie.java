package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Serie extends AppCompatActivity {

    public static ArrayList<characterItem> listaCharacter = new ArrayList<characterItem>();
    ListView listview;
    String user;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serie);

        user = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
    }
    @Override
    public void onStart(){
        super.onStart();
        ListView listView = (ListView)findViewById(R.id.listaPersonajes);
        listaCharacter.clear();
        String idSerie = getIntent().getStringExtra("IDserie");
        //Control data
        TextView txtTittle = (TextView) findViewById(R.id.tituloSerieView);
        TextView txtGenero = (TextView) findViewById(R.id.generoSerieView);
        TextView txtAnhoEstreno = (TextView) findViewById(R.id.nombreCharacterView);
        TextView txtEstado = (TextView) findViewById(R.id.personalidadCharacterView);
        TextView txtTipo = (TextView) findViewById(R.id.tipoSerieView);
        TextView txtMediaNota = (TextView) findViewById(R.id.averageSerieView);
        TextView txtSinopsis = (TextView) findViewById(R.id.nombreCharacterView);
        TextView txtDirection = (TextView) findViewById(R.id.directionSerieView);
        ImageView imageSerie = (ImageView) findViewById(R.id.imageSerie);

        try {
            JSONArray array = makeRequest(idSerie);
            if (array != null) {
                int len = array.length();
                for (int i=0;i<len;i++){
                    JSONObject dataSerie = (JSONObject) array.get(i);

                    if (Integer.parseInt(dataSerie.getString("Tipo")) == 1){
                        txtTipo.setText("Anime");
                    }
                    else if(Integer.parseInt(dataSerie.getString("Tipo")) == 2){
                        txtTipo.setText("Serie");
                    }
                    else{
                        txtTipo.setText("Pelicula");
                    }

                    txtTittle.setText(dataSerie.getString("Titulo"));
                    txtEstado.setText(dataSerie.getString("Estado"));
                    txtGenero.setText(dataSerie.getString("Genero"));
                    txtAnhoEstreno.setText(dataSerie.getString("Anho_estreno"));
                    txtSinopsis.setText(dataSerie.getString("Sinopsis"));
                    txtDirection.setText(dataSerie.getString("Direccion"));

                    //characterItem characterTemp = new characterItem();
                }

                try {
                    JSONArray arrayCharacter =  makeRequestCharacters(idSerie);
                    JSONArray jsonArrayCharacter = (JSONArray)arrayCharacter;
                    if (jsonArrayCharacter != null) {
                        int lenCharacter = jsonArrayCharacter.length();
                        for (int i=0;i<lenCharacter;i++){

                            JSONObject dataCharacter = (JSONObject) jsonArrayCharacter.get(i);

                            String idCharacter = dataCharacter.getString("ID");
                            String nombreCharacter = dataCharacter.getString("Nombre");
                            String apellidosCharacter = dataCharacter.getString("Apellidos");
                            String edadCharacter = dataCharacter.getString("Edad");
                            String poderCharacter = dataCharacter.getString("Poder");
                            String actorCharacter = dataCharacter.getString("Actor");
                            String personalidadCharacter = dataCharacter.getString("Personalidad");
                            String origenCharacter = dataCharacter.getString("Origen");
                            String descripcionCharacter = dataCharacter.getString("Descripcion");
                            characterItem characterTemp = new characterItem(Integer.parseInt(idCharacter), nombreCharacter, apellidosCharacter, Integer.parseInt(edadCharacter), poderCharacter, actorCharacter, personalidadCharacter, origenCharacter, descripcionCharacter);
                            listaCharacter.add(characterTemp);
                        }
                    }
                    else{
                        Toast.makeText(this, "Any media in the BBDD", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, "Any media in the BBDD", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        adaptadorCharacter adaptadorCharacter = new adaptadorCharacter(this, listaCharacter);
        listView.setAdapter(adaptadorCharacter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Serie.this, Character.class);
                intent.putExtra("user", user);
                intent.putExtra("password", password);
                intent.putExtra("id", Integer.toString(listaCharacter.get(position).getID()));
                startActivity(intent);
            }
        });

    }


    private JSONArray makeRequest(String id) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieInfo");
        multipartRequest.addFormField("id", id);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private JSONArray makeRequestCharacters(String idSerie) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieInfoCharacter");
        multipartRequest.addFormField("id", idSerie);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
}
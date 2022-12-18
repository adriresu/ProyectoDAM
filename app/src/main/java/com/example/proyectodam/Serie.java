package com.example.proyectodam;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Serie extends AppCompatActivity {

    public static ArrayList<characterItem> listaCharacter = new ArrayList<characterItem>();
    String idSerie;
    Boolean viewed = false;
    Boolean exists = false;
    ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getResources().getString(R.string.options));
        actionBar.setBackgroundDrawable(getDrawable(R.drawable.gradientcabecera));

        setContentView(R.layout.activity_serie);
    }
    @Override
    public void onStart(){
        super.onStart();
        ListView listView = (ListView)findViewById(R.id.listaPersonajes);
        listaCharacter.clear();

        JSONArray array;
        JSONArray jsonArray;

        idSerie = getIntent().getStringExtra("IDserie");
        //Control data
        TextView txtTittle = (TextView) findViewById(R.id.tituloSerieView);
        TextView txtGenero = (TextView) findViewById(R.id.generoSerieView);
        TextView txtAnhoEstreno = (TextView) findViewById(R.id.anhoCharacterView);
        TextView txtEstado = (TextView) findViewById(R.id.personalidadCharacterView);
        TextView txtTipo = (TextView) findViewById(R.id.tipoSerieView);
        TextView txtMediaNota = (TextView) findViewById(R.id.averageSerieView);
        TextView txtSinopsis = (TextView) findViewById(R.id.synopsisCharacterView);
        TextView txtDirection = (TextView) findViewById(R.id.directionSerieView);
        ImageView imageSerie = (ImageView) findViewById(R.id.imageSerie);
        ImageView imageViewed = (ImageView) findViewById(R.id.viewed);

        imageViewed.setOnClickListener(view -> {
            try {
                int viewedSerie = 0;
                if (viewed == true){
                    viewedSerie = 0;
                    imageViewed.setImageResource(R.drawable.eyewatch);
                    viewed = false;
                }
                else{
                    viewedSerie = 1;
                    imageViewed.setImageResource(R.drawable.viewed);
                    viewed = true;
                }
                String respuesta = makeRequestViewed(idSerie, Login.idUser, viewedSerie);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        try {
            array = makeRequest(idSerie);
            jsonArray = (JSONArray)array;
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){
                    JSONObject dataSerie = (JSONObject) jsonArray.get(i);

                    if (Integer.parseInt(dataSerie.getString("Tipo")) == 1){
                        txtTipo.setText("Film");
                    }
                    else if(Integer.parseInt(dataSerie.getString("Tipo")) == 2){
                        txtTipo.setText("Anime");
                    }
                    else{
                        txtTipo.setText("Serie");
                    }

                    txtTittle.setText(dataSerie.getString("Titulo"));
                    txtEstado.setText(dataSerie.getString("Estado"));
                    txtGenero.setText(dataSerie.getString("Genero"));
                    txtAnhoEstreno.setText(dataSerie.getString("Anho_estreno"));
                    txtSinopsis.setText(dataSerie.getString("Sinopsis"));
                    txtDirection.setText(dataSerie.getString("Direccion"));
                    if (dataSerie.getString("Media").contains(".")){
                        String nota = dataSerie.getString("Media");
                        String[] NotaSplit = nota.split("\\.");
                        txtMediaNota.setText(NotaSplit[0]);
                    }
                    else{
                        if (dataSerie.getString("Media") != "null" && dataSerie.getString("Media") != "Null" && dataSerie.getString("Media") != null){
                            txtMediaNota.setText(dataSerie.getString("Media"));
                        }
                    }
                    String preImagen2 = dataSerie.getString("Caratula");
                    if (preImagen2 != "null"){
                        byte [] encodeByte = Base64.decode(preImagen2, Base64.DEFAULT);
                        Bitmap Imagen = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        imageSerie.setImageBitmap(Imagen);
                    }
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
                            String Preimagen = dataCharacter.getString("Imagen");

                            byte [] encodeByte = Base64.decode(Preimagen, Base64.DEFAULT);
                            Bitmap Imagen = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                            characterItem characterTemp = new characterItem(Imagen, Integer.parseInt(idCharacter), nombreCharacter, apellidosCharacter, Integer.parseInt(edadCharacter), poderCharacter, actorCharacter, personalidadCharacter, origenCharacter, descripcionCharacter);
                            listaCharacter.add(characterTemp);
                        }

                        lista = (ListView) findViewById(R.id.listaPersonajes);
                        ViewGroup.LayoutParams params = lista.getLayoutParams();
                        int totalHeight = 450 * listaCharacter.size();
                        params.height = totalHeight;
                        lista.setLayoutParams(params);

                    }
                    else{
                        Toast.makeText(this, getResources().getString(R.string.not_media_bd) , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    JSONArray arrayExtraSerieInfo =  makeRequestextraSerieInfo(idSerie, Login.idUser);
                    JSONArray jsonarrayExtraSerieInfo = (JSONArray)arrayExtraSerieInfo;
                    if (jsonarrayExtraSerieInfo != null) {
                        exists = true;
                        int lenExtraSerieInfo = jsonarrayExtraSerieInfo.length();
                        JSONObject extraInfo = (JSONObject) jsonarrayExtraSerieInfo.get(0);
                        String estadoSerie = extraInfo.getString("Estado");
                        if (Integer.parseInt(estadoSerie) == 1){
                            viewed = true;
                            imageViewed.setImageResource(R.drawable.viewed);
                        }
                        else{
                            viewed = false;
                            imageViewed.setImageResource(R.drawable.eyewatch);
                        }
                        String votacionSerie = extraInfo.getString("Votacion");
                        String notaSerie = extraInfo.getString("Notas");
                    }
                    else{
                        exists = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Toast.makeText(this, getResources().getString(R.string.not_media_bd) , Toast.LENGTH_SHORT).show();
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
                intent.putExtra("user", Login.name);
                intent.putExtra("password", Login.password);
                intent.putExtra("id", Integer.toString(listaCharacter.get(position).getID()));
                intent.putExtra("IDuser", Login.idUser);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.header2, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean voted = false;
        int lod = -1;
        switch (item.getItemId()){
            case R.id.headerIconDislike:
                voted = true;
                lod = 0;
                Toast.makeText(this, getResources().getString(R.string.media_voted) , Toast.LENGTH_SHORT).show();
                break;
            case R.id.headerIconLike:
                voted = true;
                lod = 1;
                Toast.makeText(this, getResources().getString(R.string.media_voted) , Toast.LENGTH_SHORT).show();
                break;
            case R.id.headerIconFavourite:
                voted = false;
                try {
                    makeRequestAddFavourite(idSerie, Login.name);
                    Toast.makeText(this, getResources().getString(R.string.fav_media), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, getResources().getString(R.string.fav_media), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + item.getItemId());
        }

        if (voted){
            try {
                String response = makeRequestLikeDislike(idSerie, Login.idUser, String.valueOf(lod));
                if (response.equals("True")){
                    Toast.makeText(this, getResources().getString(R.string.media_voted), Toast.LENGTH_SHORT).show();
                    JSONArray array;
                    JSONArray jsonArray;
                    try{
                        array = makeRequest(idSerie);
                        jsonArray = (JSONArray)array;
                        TextView txtMediaNota = (TextView) findViewById(R.id.averageSerieView);
                        if (jsonArray != null) {
                            int len = jsonArray.length();
                            for (int i=0;i<len;i++){
                                JSONObject dataSerie = (JSONObject) jsonArray.get(i);
                                if (dataSerie.getString("Media").contains(".")){
                                    String nota = dataSerie.getString("Media").split("\\.")[0];
                                    txtMediaNota.setText(nota);
                                }
                                else{
                                    txtMediaNota.setText(dataSerie.getString("Media"));
                                }
                            }}
                    }catch (Exception e){

                    }
                }
                else{
                    Toast.makeText(this, "Media couldn't been voted", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                Toast.makeText(this, "Media couldn't been voted", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }
        return true;
    }


    private JSONArray makeRequest(String id) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieInfo");
        multipartRequest.addFormField("id", id);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private JSONArray makeRequestCharacters(String idSerie) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieInfoCharacter");
        multipartRequest.addFormField("id", idSerie);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private JSONArray makeRequestextraSerieInfo(String idSerie, String idUser) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieExtraInfo");
        multipartRequest.addFormField("id", idSerie);
        multipartRequest.addFormField("idUser", idUser);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private String makeRequestViewed(String idSerie, String idUser, int viewed) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "serieViewed");
        multipartRequest.addFormField("id", idSerie);
        multipartRequest.addFormField("idUser", idUser);
        multipartRequest.addFormField("viewed", String.valueOf(viewed));
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONObject json = new JSONObject(response.get(0));
        return json.getString("response");
    };

    private JSONArray makeRequestAddFavourite(String idSerie, String idUser) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "AddFavourite");
        multipartRequest.addFormField("usuario", idUser);
        multipartRequest.addFormField("favorita", idSerie);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };

    private String makeRequestLikeDislike(String idSerie, String idUser, String like) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "serieLikeDislike");
        multipartRequest.addFormField("idSerie", idSerie);
        multipartRequest.addFormField("idUser", idUser);
        multipartRequest.addFormField("like", like);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONObject json = new JSONObject(response.get(0));
        return json.getString("response");
    };


}
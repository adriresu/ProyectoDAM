package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Character extends AppCompatActivity {

    String characterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_character);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");


        TextView txtNombre = (TextView) findViewById(R.id.nombreCharacter);
        TextView txtApellidos = (TextView) findViewById(R.id.apellidosCharacter);
        TextView txtEdad = (TextView) findViewById(R.id.edadCharacter);
        TextView txtPoder = (TextView) findViewById(R.id.poderCharacter);
        TextView txtActor = (TextView) findViewById(R.id.actorCharacter);
        TextView txtOrigen = (TextView) findViewById(R.id.origenCharacter);
        TextView txtPersonalidad = (TextView) findViewById(R.id.personalidadCharacter);
        TextView txtDescripcion = (TextView) findViewById(R.id.descripcionCharacter);
        ImageView imageCharacter = (ImageView) findViewById(R.id.imageCharacter);

        try {
            JSONArray array =  makeRequest(id);
            JSONArray jsonArray = (JSONArray)array;
            if (jsonArray != null) {
                int len = jsonArray.length();
                for (int i=0;i<len;i++){

                    JSONObject dataCharacter = (JSONObject) jsonArray.get(i);

                    txtNombre.setText(dataCharacter.getString("Nombre"));
                    txtApellidos.setText(dataCharacter.getString("Apellidos"));
                    txtEdad.setText(dataCharacter.getString("Edad"));
                    txtPoder.setText(dataCharacter.getString("Poder"));
                    txtActor.setText(dataCharacter.getString("Actor"));
                    txtOrigen.setText(dataCharacter.getString("Origen"));
                    txtPersonalidad.setText(dataCharacter.getString("Personalidad"));
                    txtDescripcion.setText(dataCharacter.getString("Descripcion"));

                    String preImagen2 = dataCharacter.getString("Imagen");
                    if (preImagen2 != "null"){
                        byte [] encodeByte = Base64.decode(preImagen2, Base64.DEFAULT);
                        Bitmap Imagen = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                        imageCharacter.setImageBitmap(Imagen);
                    }
                }
            }
            else{
                Toast.makeText(this, R.string.not_media_bd , Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private JSONArray makeRequest(String idSerie) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "CharacterInfo");
        multipartRequest.addFormField("id", idSerie);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
};
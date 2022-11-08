package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class Serie extends AppCompatActivity {

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
        String idSerie = getIntent().getStringExtra("IDserie");
        //Control data
        TextView txtTittle = (TextView) findViewById(R.id.tituloSerieView);
        TextView txtGenero = (TextView) findViewById(R.id.generoSerieView);
        TextView txtAnhoEstreno = (TextView) findViewById(R.id.anhoEstrenoSerieView);
        TextView txtEstado = (TextView) findViewById(R.id.estadoSerieView);
        TextView txtTipo = (TextView) findViewById(R.id.tipoSerieView);
        TextView txtMediaNota = (TextView) findViewById(R.id.averageSerieView);
        TextView txtSinopsis = (TextView) findViewById(R.id.sinopsisSerieView);
        TextView txtDirection = (TextView) findViewById(R.id.directionSerieView);
        ImageView imageSerie = (ImageView) findViewById(R.id.imageSerie);

        try {
            JSONArray array = makeRequest(idSerie);
            if (array != null) {
                int len = array.length();
                for (int i=0;i<len;i++){
                    JSONObject dataSerie = (JSONObject) array.get(i);
                    txtTittle.setText(dataSerie.getString("Titulo"));
                    txtEstado.setText(dataSerie.getString("Estado"));
                    txtGenero.setText(dataSerie.getString("Genero"));
                    txtTipo.setText(dataSerie.getString("Tipo"));
                    txtAnhoEstreno.setText(dataSerie.getString("Anho_estreno"));
                    txtSinopsis.setText(dataSerie.getString("Sinopsis"));
                    txtDirection.setText(dataSerie.getString("Direccion"));

                    //characterItem characterTemp = new characterItem();
                }
            }
            else{
                Toast.makeText(this, "Any media in the BBDD", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

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
}
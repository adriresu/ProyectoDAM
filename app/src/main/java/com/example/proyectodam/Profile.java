package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {

    //Declaramos una constante para identificar el resultado del intent
    public static final int PICK_IMAGE = 1;
    List<String> seriesName = new ArrayList<String>();
    int selectedSerie;
    int typeUser;
    String name;
    String password;

    //Creamos el picker
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == PICK_IMAGE) {
            if (data != null){
                try {
                    InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());
                    ImageView imagen = (ImageView)findViewById(R.id.avatar);
                    imagen.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(this, "Algo ha salido mal", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        name = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);



        //Declaramos el boton para el avatar
        ImageView botonAvatar = (ImageView) findViewById(R.id.avatar);

        //On click select image
        botonAvatar.setOnClickListener(
                view -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Selecciona una iamgen"), PICK_IMAGE);
                }
        );

    }

    @Override
    public void onStart() {
        super.onStart();

        TextView labelName = (TextView) findViewById(R.id.txtNameProfile);
        TextView labelSurName = (TextView) findViewById(R.id.txtSurname);
        TextView labelPhone = (TextView) findViewById(R.id.txtTelfProfile);
        TextView labelEmail = (TextView) findViewById(R.id.txtEmailProfile);
        TextView labelFavourite = (TextView) findViewById(R.id.txtSerieFav);

        TextView txtName = (TextView) findViewById(R.id.editTextPersonName);
        TextView txtSurname = (TextView) findViewById(R.id.edtTextSurname);
        TextView txtPhone = (TextView) findViewById(R.id.editTextPhone);
        TextView txtEmail = (TextView) findViewById(R.id.editTextEmailAddress);
        Spinner dropdown = findViewById(R.id.spinner1);
        ImageView imgAvatar = (ImageView) findViewById(R.id.avatar);

        try {
            JSONArray array = makeRequestProfile(name);
            if (array != null) {
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject dataUser = (JSONObject) array.get(i);

                    txtName.setText(dataUser.getString("Nombre"));
                    txtSurname.setText(dataUser.getString("Apellidos"));
                    txtPhone.setText(dataUser.getString("Telefono"));
                    txtEmail.setText(dataUser.getString("Correo"));
                    selectedSerie = Integer.parseInt(dataUser.getString("Favorita"));
                    typeUser = Integer.parseInt(dataUser.getString("Rol"));
                    //characterItem characterTemp = new characterItem();
                }

                if (typeUser == 0){
                    imgAvatar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                    labelName.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                    labelSurName.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                    labelPhone.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                    labelEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                    labelFavourite.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabecera));
                }
                else if(typeUser == 1){
                    imgAvatar.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                    labelName.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                    labelSurName.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                    labelPhone.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                    labelEmail.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                    labelFavourite.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradientcabeceragold));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        };

        seriesName.clear();
        try {
            JSONArray array = makeRequest();
            if (array != null) {
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject dataSerie = (JSONObject) array.get(i);
                    seriesName.add(dataSerie.getString("ID") + "- " +  dataSerie.getString("Titulo"));
                }
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(Profile.this, android.R.layout.simple_spinner_dropdown_item, seriesName);
            dropdown.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
        };

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String serie = parent.getItemAtPosition(position).toString();
                String[] parts = serie.split("-");
                selectedSerie = Integer.parseInt(parts[0]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            };
        });
    };

    private JSONArray makeRequest() throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "SerieName");
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };
    private JSONArray makeRequestProfile(String name) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "UserInfo");
        multipartRequest.addFormField("usuario", name);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    };


}
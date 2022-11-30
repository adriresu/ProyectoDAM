package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
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
                    Toast.makeText(this, R.string.wrong, Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){};
        setContentView(R.layout.activity_profile);
        name = getIntent().getStringExtra("user");
        password = getIntent().getStringExtra("password");
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);

        //Declaramos el boton para el avatar
        ImageView botonAvatar = (ImageView) findViewById(R.id.avatar);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        Spinner spinner = (Spinner)findViewById(R.id.spinner1);

        TextView labelName = (TextView) findViewById(R.id.txtNameProfile);
        TextView labelSurName = (TextView) findViewById(R.id.txtSurname);
        TextView labelPhone = (TextView) findViewById(R.id.txtTelfProfile);
        TextView labelEmail = (TextView) findViewById(R.id.txtEmailProfile);
        TextView labelFavourite = (TextView) findViewById(R.id.txtSerieFav);
        TextView txtName = (TextView) findViewById(R.id.editTextPersonName);
        TextView txtSurname = (TextView) findViewById(R.id.edtTextSurname);
        TextView txtPhone = (TextView) findViewById(R.id.editTextPhone);
        TextView txtEmail = (TextView) findViewById(R.id.editTextEmailAddress);
        ImageView imgAvatar = (ImageView) findViewById(R.id.avatar);

        try {
            JSONArray array = makeRequestProfile(name);
            if (array != null) {
                int len = array.length();
                for (int i = 0; i < len; i++) {
                    JSONObject dataUser = (JSONObject) array.get(i);

                    txtName.setText(dataUser.getString("Nombre"));
                    txtSurname.setText(dataUser.getString("Apellidos"));
                    if (dataUser.getString("Telefono").equals("null")){
                        txtPhone.setText("");
                    }
                    else{
                        txtPhone.setText(dataUser.getString("Telefono"));
                    }

                    txtEmail.setText(dataUser.getString("Correo"));
                    selectedSerie = Integer.parseInt(dataUser.getString("Favorita"));
                    typeUser = Integer.parseInt(dataUser.getString("Rol"));

                    ImageView imgAvatar2 = (ImageView) findViewById(R.id.avatar);
                    byte[] decodedString = Base64.decode(dataUser.getString("Imagen"), Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                    imgAvatar2.setImageBitmap(decodedByte);

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

        //On click save
        btnSave.setOnClickListener(view -> {
                    Boolean flag = true;

                    Bitmap bitmap = ((BitmapDrawable)imgAvatar.getDrawable()).getBitmap();
                    String imageSend = getStringImage(bitmap);



                    if (txtName.getText().toString().equals("") || txtName.getText().toString().length() < 3) {
                        txtName.setError(getString(R.string.errorRegister08));
                        flag = false;
                    }
                    if (txtSurname.getText().toString().equals("") || txtSurname.getText().toString().length() < 3) {
                        txtSurname.setError(getString(R.string.errorRegister09));
                        flag = false;
                    }
                    try {
                        String phoneNumber = txtPhone.getText().toString();
                        if (phoneNumber.length() != 0){
                            Integer.valueOf(phoneNumber);
                            if ((phoneNumber.length() < 9 && phoneNumber.length() > 0) || phoneNumber.length() > 9) {
                                txtPhone.setError(getString(R.string.errorRegister11));
                                flag = false;
                            }
                        }
                    } catch (Exception e) {
                        txtPhone.setError(getString(R.string.errorRegister12));
                        flag = false;
                    }
                    ;
                    if (txtEmail.getText().toString().isEmpty()) {
                        txtEmail.setError(getString(R.string.errorRegister07));
                        flag = false;
                    }
                    //Regex match
                    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                    if (!(txtEmail.getText().toString().matches(emailPattern))) {
                        txtEmail.setError(getString(R.string.errorRegister06));
                        flag = false;
                    }
                    String stringSelected = spinner.getSelectedItem().toString();
                    String selectedSerie = (stringSelected.split("-"))[0];
                    if (flag) {
                        try {
                    makeRequestUpdateProfile(imageSend, txtName.getText().toString(), txtSurname.getText().toString(), txtPhone.getText().toString(), txtEmail.getText().toString(), selectedSerie, name);
                            Toast.makeText(this, R.string.updated, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(this, R.string.error_update , Toast.LENGTH_SHORT).show();
                        }
                    }
        });

        //On click select image
        botonAvatar.setOnClickListener(
            view -> {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_img)), PICK_IMAGE);
            }
        );
    };

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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
                    if (dataUser.getString("Telefono").equals("null")){
                        txtPhone.setText("");
                    }
                    else{
                        txtPhone.setText(dataUser.getString("Telefono"));
                    }

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
    private void makeRequestUpdateProfile(String binaryImage, String name, String apellidos, String phone, String email, String favorita, String usuario) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "UpdateUserInfo");
        multipartRequest.addFormField("nombre", name);
        multipartRequest.addFormField("apellidos", apellidos);
        multipartRequest.addFormField("phone", phone);
        multipartRequest.addFormField("email", email);
        multipartRequest.addFormField("favorita", favorita);
        multipartRequest.addFormField("usuario", usuario);
        multipartRequest.addFormField("bitmap", binaryImage);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
    };


}
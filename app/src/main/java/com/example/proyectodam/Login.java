package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_login);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        //Control definitions
        EditText txtUsername = (EditText) findViewById(R.id.inputNameLogin);
        EditText txtPassword = (EditText) findViewById(R.id.inputPasswordLogin);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);
        Button btnRegister = (Button) findViewById(R.id.buttonRegister);

        //Button to Login
        btnLogin.setOnClickListener(view -> {
            Boolean flag = true;

            if (txtUsername.getText().length() <= 0){
                txtUsername.setError(getResources().getString(R.string.enter_username));
                flag = false;
            }
            if (txtPassword.getText().length() <= 0){
                txtPassword.setError(getResources().getString(R.string.enter_password));
                flag = false;
            }

            if (flag) { //Proceed to Login on BBDD (WS)
                try {
                    JSONArray array = makeRequest(txtUsername.getText().toString(), txtPassword.getText().toString());
                    JSONArray jsonArrayCharacter = (JSONArray)array;

                    if (array != null) {
                        JSONObject dataUser = (JSONObject) jsonArrayCharacter.get(0);
                        String id = dataUser.getString("ID");
                        Intent intent = new Intent(Login.this, mainMenu.class);
                        intent.putExtra("user", txtUsername.getText().toString());
                        intent.putExtra("password", txtPassword.getText().toString());
                        intent.putExtra("IDuser", id);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, R.string.not_media_bd , Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                //If fails show messages
            }
        });
        btnRegister.setOnClickListener(view -> {
            Intent intent = new Intent(Login.this, Register.class);
            startActivity(intent);
        });
    }

    private JSONArray makeRequest(String username, String password) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.136:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "Login");
        multipartRequest.addFormField("username", username);
        multipartRequest.addFormField("password", password);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    }

};
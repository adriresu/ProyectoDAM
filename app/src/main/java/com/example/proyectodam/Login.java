package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //Control definitions
        EditText txtUsername = (EditText) findViewById(R.id.inputNameLogin);
        EditText txtPassword = (EditText) findViewById(R.id.inputPasswordLogin);
        Button btnRegister = (Button) findViewById(R.id.buttonLogin);

        //Button to Login
        btnRegister.setOnClickListener(view -> {
            Boolean flag = true;
            String[] errors = new String[10];

            if (flag) { //Proceed to register on BBDD (WS)
                try {
                    String respuesta = makeRequest(txtUsername.getText().toString(), txtPassword.getText().toString());
                    if (respuesta == "true"){
                        Toast.makeText(this, "BIEN", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {
                //If fails show messages
            }
        });
    }

    private String makeRequest(String username, String password) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.1.132:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "Login");
        multipartRequest.addFormField("username", username);
        multipartRequest.addFormField("password", password);
        List<String> response = multipartRequest.finish();
        JsonObject obj = new JsonParser().parse(response.get(0)).getAsJsonObject();
        String respuesta = obj.get("response").getAsString();
        Toast.makeText(this, respuesta, Toast.LENGTH_SHORT).show();
        return respuesta;
    }


};
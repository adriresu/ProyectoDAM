package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Control definitions
        EditText txtUsername = (EditText) findViewById(R.id.inputName);
        EditText txtPassword = (EditText) findViewById(R.id.inputPassword);
        EditText txtPassword2 = (EditText) findViewById(R.id.inputPassword2);
        EditText txtEmail = (EditText) findViewById(R.id.inputEmail);



    }
}
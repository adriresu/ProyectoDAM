package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Control definitions
        EditText txtUsername = (EditText) findViewById(R.id.inputNameLogin);
        EditText txtPassword = (EditText) findViewById(R.id.inputPasswordLogin);
        Button btnRegister = (Button) findViewById(R.id.buttonLogin);

        //Button to Login
        btnRegister.setOnClickListener(view -> {
            Boolean flag = true;
            String[] errors = new String[10];

            if (flag){ //Proceed to register on BBDD (WS)
                Intent intent = new Intent(Login.this, mainMenu.class);
                intent.putExtra("user", txtUsername.getText().toString());
                intent.putExtra("password", txtPassword.getText().toString());
                startActivity(intent);
            }
            else{
                //If fails show messages
            }
        });

    }

}
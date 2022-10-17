package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Control definitions
        EditText txtUsername = (EditText) findViewById(R.id.inputName);
        EditText txtPassword = (EditText) findViewById(R.id.inputPassword);
        EditText txtPassword2 = (EditText) findViewById(R.id.inputPassword2);
        EditText txtEmail = (EditText) findViewById(R.id.inputEmail);
        Button btnRegister = (Button) findViewById(R.id.buttonRegister);

        //Button to register, connect to BBDD
        btnRegister.setOnClickListener(view -> {
            Boolean flag = true;
            String[] errors = new String[10];
            if (txtUsername.getText().toString().equals("") || txtUsername.getText().toString().length() < 6) {
                errors[0] = getString(R.string.errorRegister01);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
            if (txtPassword.getText().toString().isEmpty() && !txtPassword2.getText().toString().isEmpty()){
                errors[1] = getString(R.string.errorRegister02);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
            if (txtPassword.getText().toString().length() < 8){
                errors[1] = getString(R.string.errorRegister02);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
            if (!(txtPassword.getText().toString()).equals(txtPassword2.getText().toString())){
                errors[2] = getString(R.string.errorRegister05);
                flag = false;
                Toast.makeText(this,errors[1] , Toast.LENGTH_SHORT).show();
            }
            if (txtEmail.getText().toString().isEmpty()) {
                errors[0] = getString(R.string.errorRegister07);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
            //Regex match
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (!(txtEmail.getText().toString().matches(emailPattern))) {
                errors[2] = getString(R.string.errorRegister06);
                flag = false;
                Toast.makeText(this,errors[6] , Toast.LENGTH_SHORT).show();
            }

            if (flag){ //Proceed to register on BBDD (WS)
                Toast.makeText(this, "El usuario " + txtUsername.getText().toString() + " se ha creado correctamente", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Register.this, mainMenu.class);
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
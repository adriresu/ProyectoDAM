package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.List;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_register);

        //Control definitions
        EditText txtName = (EditText) findViewById(R.id.inputNameRegister);
        EditText txtSurname = (EditText) findViewById(R.id.inputSurnameRegister);
        EditText txtUsername = (EditText) findViewById(R.id.inputUserNameRegister);
        EditText txtPassword = (EditText) findViewById(R.id.inputPasswordRegister);
        EditText txtPassword2 = (EditText) findViewById(R.id.inputPassword2Register);
        EditText txtEmail = (EditText) findViewById(R.id.inputEmailRegister);
        Button btnRegister = (Button) findViewById(R.id.buttonRegister);
        Button btnLogin = (Button) findViewById(R.id.buttonLogin);

        //Button to register, connect to BBDD
        btnRegister.setOnClickListener(view -> {
            Boolean flag = true;
            String[] errors = new String[10];
            if (txtName.getText().toString().equals("") || txtName.getText().toString().length() < 6) {
                errors[0] = getString(R.string.errorRegister08);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
            if (txtSurname.getText().toString().equals("") || txtSurname.getText().toString().length() < 6) {
                errors[0] = getString(R.string.errorRegister09);
                flag = false;
                Toast.makeText(this,errors[0] , Toast.LENGTH_SHORT).show();
            }
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
                try {
                    String respuesta = makeRequest(txtName.getText().toString(), txtSurname.getText().toString(), txtUsername.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString());
                    if (respuesta.equals("True")){
                        Toast.makeText(this, "Se ha creado correctamente el usuario", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Register.this, mainMenu.class);
                        intent.putExtra("user", txtUsername.getText().toString());
                        intent.putExtra("password", txtPassword.getText().toString());
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(this, "Please fill all the fields correctly", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                //If fails show messages
            }


        });
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(Register.this, Login.class);
            startActivity(intent);
        });
    }

    private String makeRequest(String name, String surname, String username, String password, String email) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://192.168.0.17:80", "UTF-8");
        multipartRequest.addFormField("Tipo", "Register");
        multipartRequest.addFormField("name", username);
        multipartRequest.addFormField("surname", password);
        multipartRequest.addFormField("username", username);
        multipartRequest.addFormField("password", password);
        multipartRequest.addFormField("email", email);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONObject json = new JSONObject(response.get(0));
        return json.getString("response");
    }
}
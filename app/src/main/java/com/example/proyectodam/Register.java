package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
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

            if (txtName.getText().toString().equals("") || txtName.getText().toString().length() < 3) {
                txtName.setError(getString(R.string.errorRegister08));
                flag = false;
            }
            if (txtSurname.getText().toString().equals("") || txtSurname.getText().toString().length() < 3) {
                txtSurname.setError(getString(R.string.errorRegister09));
                flag = false;
            }
            if (txtUsername.getText().toString().equals("") || txtUsername.getText().toString().length() < 4) {
                txtUsername.setError(getString(R.string.errorRegister01));
                flag = false;
            }
            else{
                try {
                    JSONArray array = makeRequestCheck(txtUsername.getText().toString());

                    if (array != null) {
                        txtUsername.setError(getString(R.string.errorRegister10));
                        flag = false;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if (txtPassword.getText().toString().isEmpty()){
                txtPassword.setError(getString(R.string.errorRegister02));
                flag = false;
                if (!txtPassword2.getText().toString().isEmpty()){
                    txtPassword2.setError(getString(R.string.errorRegister02));
                }
            }
            if (txtPassword.getText().toString().length() < 8){
                txtPassword.setError(getString(R.string.errorRegister05));
                flag = false;
            }
            if (!(txtPassword.getText().toString()).equals(txtPassword2.getText().toString())){
                txtPassword2.setError(getString(R.string.errorRegister03));
                flag = false;
            }
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


            if (flag){ //Proceed to register on BBDD (WS)
                try {
                    JSONArray array = makeRequest(txtName.getText().toString(), txtSurname.getText().toString(), txtUsername.getText().toString(), txtPassword.getText().toString(), txtEmail.getText().toString());
                    JSONArray jsonArrayCharacter = (JSONArray)array;

                    Toast.makeText(this, R.string.createduser_successfull , Toast.LENGTH_SHORT).show();
                    JSONObject dataUser = (JSONObject) jsonArrayCharacter.get(0);
                    String id = dataUser.getString("ID");
                    Login.name = txtUsername.getText().toString();
                    Login.password = txtPassword.getText().toString();
                    Intent intent = new Intent(Register.this, mainMenu.class);
                    intent.putExtra("user", txtUsername.getText().toString());
                    intent.putExtra("password", txtPassword.getText().toString());
                    intent.putExtra("IDuser", id);
                    startActivity(intent);

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

    private JSONArray makeRequest(String name, String surname, String username, String password, String email) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "Register");
        multipartRequest.addFormField("name", username);
        multipartRequest.addFormField("surname", password);
        multipartRequest.addFormField("username", username);
        multipartRequest.addFormField("password", password);
        multipartRequest.addFormField("email", email);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    }

    private JSONArray makeRequestCheck(String username) throws Exception {
        MultipartUtility multipartRequest = new MultipartUtility("http://86.127.253.180", "UTF-8");
        multipartRequest.addFormField("Tipo", "CheckIfUser");
        multipartRequest.addFormField("username", username);
        multipartRequest.addFormField("End", "End");
        List<String> response = multipartRequest.finish();
        JSONArray json = new JSONArray(response.get(0));
        return json;
    }
}
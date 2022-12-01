package com.example.proyectodam;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

import java.util.Timer;
import java.util.TimerTask;

public class Splash extends AppCompatActivity {

    TimerTask Tarea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_splash);

        //Creamos el timer
        Tarea = new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, Login.class);
                startActivity(intent);
                finish();
            }
        };
        Timer tiempo = new Timer();
        tiempo.schedule(Tarea, 4000);
    }

    //En vez de poner un boton para saltarse la pantalla inicial, vale con hacer click en cualquier parte de la pantalla
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Tarea.cancel();
        Intent intent = new Intent(Splash.this, Login.class);
        startActivity(intent);
        finish();
        return super.onTouchEvent(event);
    }
}
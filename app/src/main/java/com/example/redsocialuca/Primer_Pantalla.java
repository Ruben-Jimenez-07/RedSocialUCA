package com.example.redsocialuca;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Primer_Pantalla extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_de_carga);
        //Este metodo me permite cuanto timepo dura la pantalla principal
        final int Duracion= 2500;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Esto es lo que se ejecutara para comenzar la aplicacion
                Intent intent =new Intent(Primer_Pantalla.this, MainActivity.class);
                startActivity(intent);
                //Esto nos permite cambiar de una actividad a otra
            }
        },Duracion);
    }
}
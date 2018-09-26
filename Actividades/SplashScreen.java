package com.barajasoft.higienedelsueo.Actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.barajasoft.higienedelsueo.Datos.Configuracion;
import com.barajasoft.higienedelsueo.R;

public class SplashScreen extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuracion configuracion = new Configuracion();
        setContentView(R.layout.splash_screen_layout);
        Thread timer = new Thread() {
            private int MAX_TIME = 3000;
            @Override
            public void run() {
                while (MAX_TIME > 0) {
                    try {
                        sleep(100);
                        MAX_TIME -= 100;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (configuracion.checkIfExists()) {
                    Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashScreen.this, ConfiguracionInicial.class);
                    startActivity(intent);
                }
                finish();
            }
        };
        timer.start();
    }
}

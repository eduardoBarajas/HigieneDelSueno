package com.barajasoft.higienedelsueo.Actividades;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import com.barajasoft.higienedelsueo.Datos.Configuracion;
import com.barajasoft.higienedelsueo.R;
import org.json.JSONObject;

import static android.view.KeyEvent.KEYCODE_BACK;

public class ConfiguracionInicial extends AppCompatActivity {
    private Button btnHombre, btnMujer, btnGuardar;
    private TimePicker dormir, despertar;
    private EditText txtNombre,txtEdad;
    private String horaDormir = "", horaDespertar = "", sexo = "";
    private int RESULT_CODE = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuracion configuracion = new Configuracion();
        setContentView(R.layout.first_init_layout);
        txtEdad = findViewById(R.id.txtEdad);
        txtNombre = findViewById(R.id.txtNombreEdit);
        dormir = findViewById(R.id.timePicker2);
        dormir.setIs24HourView(true);
        despertar = findViewById(R.id.timePicker3);
        despertar.setIs24HourView(true);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnMujer = findViewById(R.id.btnMujer);
        btnHombre = findViewById(R.id.btnHombre);
        if(getIntent().hasExtra("Conf")){
            String[] conf = getIntent().getStringArrayExtra("Conf");
            txtNombre.setText(conf[0]);
            sexo = conf[1];
            if(sexo.equals("Hombre")){
                btnHombre.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btnMujer.setBackgroundColor(Color.DKGRAY);
            }else{
                btnMujer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                btnHombre.setBackgroundColor(Color.DKGRAY);
            }

            String[] tiempoDormir = conf[2].split(":");
            if(Build.VERSION.SDK_INT < 23){
                dormir.setCurrentHour(Integer.valueOf(tiempoDormir[0]));
                dormir.setCurrentMinute(Integer.valueOf(tiempoDormir[1]));
                horaDormir = conf[2];
            } else{
                dormir.setHour(Integer.valueOf(tiempoDormir[0]));
                dormir.setMinute(Integer.valueOf(tiempoDormir[1]));
                horaDormir = conf[2];
            }
            String[] tiempoDespertar = conf[3].split(":");
            if(Build.VERSION.SDK_INT < 23){
                despertar.setCurrentHour(Integer.valueOf(tiempoDespertar[0]));
                despertar.setCurrentMinute(Integer.valueOf(tiempoDespertar[1]));
                horaDespertar = conf[3];
            } else{
                despertar.setHour(Integer.valueOf(tiempoDespertar[0]));
                despertar.setMinute(Integer.valueOf(tiempoDespertar[1]));
                horaDespertar = conf[3];
            }
            txtEdad.setText(conf[4]);
        }
        btnGuardar.setOnClickListener(e->{
            if(!horaDormir.isEmpty() && !horaDespertar.isEmpty() && !sexo.isEmpty() && !txtNombre.getText().toString().isEmpty()&& !txtEdad.getText().toString().isEmpty()){
                int hora_dormir = Integer.parseInt(horaDormir.split(":")[0])*60 + Integer.parseInt(horaDormir.split(":")[1]);
                int hora_despertar = Integer.parseInt(horaDespertar.split(":")[0])*60 + Integer.parseInt(horaDespertar.split(":")[1]);
                if(Math.abs(hora_dormir-hora_despertar) >= (4*60) ){
                    if(getIntent().hasExtra("Conf")){
                        configuracion.deleteConfiguration();
                        createJson(configuracion);
                        RESULT_CODE = RESULT_OK;
                        finish();
                    }else{
                        createJson(configuracion);
                        Toast.makeText(getApplicationContext(),"Se supone que se agrego",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ConfiguracionInicial.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"La diferencia entre la hora de dormir y la hora de despertar debe de ser de 4 horas minimo.",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(),"Ingresa bien los datos",Toast.LENGTH_SHORT).show();
            }
        });
        btnHombre.setOnClickListener(e->{
            sexo = "Hombre";
            btnHombre.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnMujer.setBackgroundColor(Color.DKGRAY);
        });
        btnMujer.setOnClickListener(e->{
            sexo = "Mujer";
            btnMujer.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            btnHombre.setBackgroundColor(Color.DKGRAY);
        });
        despertar.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if(Build.VERSION.SDK_INT < 23){
                    int getHour = timePicker.getCurrentHour();
                    int getMinute = timePicker.getCurrentMinute();
                    horaDespertar = String.valueOf(getHour)+":"+String.valueOf(getMinute);
                } else{
                    int getHour = timePicker.getHour();
                    int getMinute = timePicker.getMinute();
                    horaDespertar = String.valueOf(getHour)+":"+String.valueOf(getMinute);
                }
            }
        });
        dormir.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                if(Build.VERSION.SDK_INT < 23){
                    int getHour = timePicker.getCurrentHour();
                    int getMinute = timePicker.getCurrentMinute();
                    horaDormir = String.valueOf(getHour)+":"+String.valueOf(getMinute);
                } else{
                    int getHour = timePicker.getHour();
                    int getMinute = timePicker.getMinute();
                    horaDormir = String.valueOf(getHour)+":"+String.valueOf(getMinute);
                }
            }
        });
    }

    private void createJson(Configuracion configuracion){
        JSONObject obj = new JSONObject();
        try{
            obj.put("Nombre",txtNombre.getText().toString());
            obj.put("Sexo",sexo);
            obj.put("Hora_Dormir",horaDormir);
            obj.put("Hora_Despertar",horaDespertar);
            obj.put("Edad",txtEdad.getText().toString());
        }catch (Exception ex){
            Log.e("Error","Al crear el json");
        }
        configuracion.createFile();
        configuracion.writeJsonFile(obj.toString());
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        //si se recibe una tecla se limpia la pantalla
        super.onKeyDown(keyCode,event);
        if(keyCode == KEYCODE_BACK){
            RESULT_CODE = RESULT_CANCELED;
            finish();
        }
        return true;
    }

    @Override
    public void finish() {
        if(RESULT_CODE==RESULT_OK){
            Intent intent = new Intent();
            intent.putExtra("NuevaConf",new String[]{txtNombre.getText().toString(),
            sexo,horaDormir,horaDespertar,txtEdad.getText().toString()});
            setResult(RESULT_CODE,intent);
        }else{
            setResult(RESULT_CODE);
        }
        super.finish();
    }
}

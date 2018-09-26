package com.barajasoft.higienedelsueo.Dialogos;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Listeners.DlgResult;
import com.barajasoft.higienedelsueo.Listeners.OperationFinished;
import com.barajasoft.higienedelsueo.R;
import com.samsung.android.sdk.SsdkUnsupportedException;
import com.samsung.android.sdk.sensorextension.Ssensor;
import com.samsung.android.sdk.sensorextension.SsensorEvent;
import com.samsung.android.sdk.sensorextension.SsensorEventListener;
import com.samsung.android.sdk.sensorextension.SsensorExtension;
import com.samsung.android.sdk.sensorextension.SsensorManager;

import java.util.LinkedList;
import java.util.List;

import static java.lang.Thread.sleep;

public class DlgRitmoCardiaco extends Dialog implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor heart;
    private float ritmo_cardiaco;
    private List<Float> mediciones = new LinkedList<>();
    private float ritmo_cardiaco_promedio;
    private boolean medicion_activa = false;

    private Button btn_Stop = null;
    private TextView ritmo;
    private ProgressBar progressBar;

    public DlgRitmoCardiaco(@NonNull Context context, DlgResult listener) {
        super(context);
        setContentView(R.layout.dlg_ritmo_cardiaco);
        ritmo = findViewById(R.id.txtCardiac);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(75);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        heart = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(heart == null){
            Toast.makeText(context,"No tiene sensor de ritmo cardiaco!!",Toast.LENGTH_SHORT).show();
        }else{
            sensorManager.registerListener(this,heart,SensorManager.SENSOR_DELAY_FASTEST);
        }
       btn_Stop = findViewById(R.id.AceptarBtn);
       btn_Stop.setOnClickListener(e->{
            listener.result("dlg_ritmo_cardiaco",String.valueOf((int)ritmo_cardiaco_promedio/mediciones.size()));
            dismiss();
       });
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("STOP","SE CERRO EL DLG");
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE){
            if(progressBar.getProgress()==75){
                btn_Stop.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(),"Se completo la medicion",Toast.LENGTH_SHORT).show();
            }else{
                if(ritmo_cardiaco!=0){
                    medicion_activa = true;
                    mediciones.add(ritmo_cardiaco);
                    progressBar.setProgress(mediciones.size());
                }else{
                    mediciones.clear();
                    progressBar.setProgress(0);
                    if(medicion_activa){
                        Toast.makeText(getContext(),"Se interrumpio la medicion, por favor vuelve a colocar el dedo sobre sensor",Toast.LENGTH_SHORT).show();
                        medicion_activa = false;
                    }
                }
            }
            ritmo_cardiaco_promedio = 0;
            ritmo_cardiaco = sensorEvent.values[0];
            if(mediciones.size()==0){
                ritmo.setText(String.valueOf((int)ritmo_cardiaco));
            }else{
                for(Float f : mediciones){
                    ritmo_cardiaco_promedio += f;
                }
                ritmo.setText(String.valueOf((int)ritmo_cardiaco_promedio/mediciones.size()));
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

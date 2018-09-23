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
import android.widget.Button;
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

import java.util.List;

public class DlgRitmoCardiaco extends Dialog implements SensorEventListener{

    private SensorManager sensorManager;
    private Sensor heart;
    private float ritmo_cardiaco;

    private Button btn_Start = null;
    private Button btn_Stop = null;
    private TextView ritmo;

    public DlgRitmoCardiaco(@NonNull Context context, DlgResult listener) {
        super(context);
        setContentView(R.layout.dlg_ritmo_cardiaco);
        ritmo = findViewById(R.id.txtCardiac);
        sensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);

        heart = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        if(heart != null){
            Toast.makeText(context,"Si tiene sensor de ritmo cardiaco!!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context,"No tiene sensor de ritmo cardiaco!!",Toast.LENGTH_SHORT).show();
        }

        btn_Start = findViewById(R.id.startButton);
        btn_Start.setOnClickListener(e->{
            sensorManager.registerListener(this,heart,SensorManager.SENSOR_DELAY_FASTEST);
        });

       btn_Stop = findViewById(R.id.stopBtn);
       btn_Stop.setOnClickListener(e->{
            listener.result("dlg_ritmo_cardiaco",String.valueOf(127));
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
            if(sensorEvent.values[0]==0){
                Log.e("Estas muerto","Y a parte ella no te quiere :) ");
            }else{
                ritmo_cardiaco = sensorEvent.values[0];
            }
            Log.e("Ritmo cardiaco",String.valueOf(sensorEvent.values[0]));
            ritmo.setText(String.valueOf(sensorEvent.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}

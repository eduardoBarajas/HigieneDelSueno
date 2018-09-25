package com.barajasoft.higienedelsueo.Actividades;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Datos.Sesion;
import com.barajasoft.higienedelsueo.Entidades.CurrentTime;
import com.barajasoft.higienedelsueo.R;

import java.util.ArrayList;

public class NFCActivity extends AppCompatActivity{
    private NfcAdapter nfcAdapter;
    private IntentFilter[] filter;
    private String[][] techList;
    private PendingIntent nfcPendingIntent;
    private TextView mensaje, txtSustancia;
    private ImageView img;

    private Sesion sesion_actual;
    private CurrentTime tiempo_actual;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sesion_actual = Sesion.getInstance();
        tiempo_actual = CurrentTime.getInstance(getApplicationContext());
        setContentView(R.layout.nfc_layout_activity);
        img = findViewById(R.id.imageView2);
        txtSustancia = findViewById(R.id.txtNFC);
        mensaje = findViewById(R.id.textView12);
        Intent nfcIntent = new Intent(this,getClass());
        nfcIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        nfcPendingIntent = PendingIntent.getActivity(this,0,nfcIntent,0);

        IntentFilter tagIntentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);

        try{
            tagIntentFilter.addDataType("text/plain");
            filter = new IntentFilter[]{tagIntentFilter};
        }catch (IntentFilter.MalformedMimeTypeException e) {
            Log.e("Mime error","Check your mime type.");
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if(nfcAdapter == null){
            Toast.makeText(this,"Este dispositivo no soporta NFC!",Toast.LENGTH_SHORT).show();
        }
        if(!nfcAdapter.isEnabled()){
            Toast.makeText(this,"El NFC esta deshabilitado",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,"El NFC esta Habilitado",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        int horaDormir = 0;
        int tiempoActual = Integer.parseInt(tiempo_actual.getCurrentHour())*60+Integer.parseInt(tiempo_actual.getCurrentMinute());
        if(tiempoActual==0){
            Toast.makeText(this,"Hubo un problema con la obtencion de la hora, por favor intentalo en un minuto.",Toast.LENGTH_LONG).show();
            finish();
        }
        if(sesion_actual!=null){
            Log.e("Tiempo Dormir",sesion_actual.getPaciente().getHora_dormir());
            horaDormir = (Integer.parseInt(sesion_actual.getPaciente().getHora_dormir().split(":")[0])*60+Integer.parseInt(sesion_actual.getPaciente().getHora_dormir().split(":")[1])) - (4 * 60);
        }else{
            Toast.makeText(this,"Hubo un problema recuerda que debes tener la aplicacion abierta.",Toast.LENGTH_LONG).show();
            finish();
        }
        Log.e("Tiempo Actual",String.valueOf(tiempoActual));
        Log.e("Tiempo Actual",tiempo_actual.getCurrentHour()+":"+tiempo_actual.getCurrentMinute());
        Log.e("Tiempo Dormir",String.valueOf(horaDormir));
        if(tiempoActual >= horaDormir && tiempoActual <= (horaDormir+(4*60))){
            if(!sesion_actual.getEsta_drogado()){
                //Se configura el foreground dispatcher
                nfcAdapter.enableForegroundDispatch(this,nfcPendingIntent,filter,null);
                handleIntent(getIntent());
            }else{
                Toast.makeText(this,"Ya se registro al menos una sustancia de dopado.",Toast.LENGTH_SHORT).show();
                finish();
            }
        }else{
            Toast.makeText(this,"No se puede registrar la etiqueta, procura que sea dentro de las cuatro horas antes de dormir",Toast.LENGTH_SHORT).show();
            finish();
        }
   }

    private void handleIntent(Intent intent) {
        getTag(intent);
    }

    private void getTag(Intent intent) {
        if(intent == null)
            return;

        String type = intent.getType();
        String action = intent.getAction();

        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)){
            Parcelable[] parcelables = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            for(Parcelable p : parcelables){
                NdefMessage msg = (NdefMessage)p;
                NdefRecord[] records = msg.getRecords();
                for(NdefRecord record: records){
                    byte status =  record.getPayload()[0];
                    int encoding = status & 0x80; // Operacion AND en 7mo bit 1
                    String encString = null;
                    if(encoding == 0)
                        encString = "UTF-8";
                    else
                        encString = "UTF-16";
                    int len = status & 0x3F;
                    try{
                        String content = new String(record.getPayload(),len+1,record.getPayload().length - 1 - len, encString);
                        if(content.equals("cafe")){
                            img.setVisibility(View.VISIBLE);
                            img.setImageResource(R.drawable.coffe);
                            mensaje.setText("Se detecto la siguiente sustancia:");
                            txtSustancia.setText(content);
                            sesion_actual.setEsta_drogado(1);
                        }else{
                            if(content.equals("Alcohol")){
                                img.setVisibility(View.VISIBLE);
                                img.setImageResource(R.drawable.alcohol);
                                mensaje.setText("Se detecto la siguiente sustancia:\n");
                                txtSustancia.setText(content);
                                sesion_actual.setEsta_drogado(1);
                            }else{
                                img.setVisibility(View.INVISIBLE);
                                mensaje.setText("Esta etiqueta no tiene soporte actualmente:\n");
                                txtSustancia.setText(content);
                                sesion_actual.setEsta_drogado(0);
                            }
                        }

                    }catch (Exception ex){
                        Log.e("error",ex.getMessage());
                    }
                }
            }
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if(intent.getType().equals("android.nfc.tech.Ndef")){
            Toast.makeText(this,"ES DE NFC",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Se para el foreground dispatcher
        nfcAdapter.disableForegroundDispatch(this);
    }


}

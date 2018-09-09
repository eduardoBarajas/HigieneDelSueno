package com.barajasoft.higienedelsueo.Actividades;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.R;

public class NFCActivity extends AppCompatActivity{
    private NfcAdapter nfcAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_layout_activity);
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
        //Se configura el foreground dispatcher
        setupForegroundDispatch(this,nfcAdapter);
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
        stopForegroundDispatch(this,nfcAdapter);
    }

    private void setupForegroundDispatch(final Activity activity, NfcAdapter adapter){
        Intent intent = new Intent(activity.getApplicationContext(),NFCActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent pendingIntent = PendingIntent.getActivity(activity.getApplicationContext(),0,intent,0);

        IntentFilter[] filter = new IntentFilter[1];
        String[][] techList = new String[][]{};

        //Tiene que ser el mismo filtro que en el manifest
        filter[0].addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter[0].addCategory(Intent.CATEGORY_DEFAULT);
        try{
            filter[0].addDataType("text/plain");
        }catch (IntentFilter.MalformedMimeTypeException e) {
            throw new RuntimeException("Check your mime type.");
        }
        adapter.enableForegroundDispatch(activity,pendingIntent,filter,techList);
    }

    private void stopForegroundDispatch(final Activity activity, NfcAdapter adapter){
        adapter.disableForegroundDispatch(activity);
    }
}

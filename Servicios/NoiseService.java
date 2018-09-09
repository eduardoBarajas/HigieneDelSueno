package com.barajasoft.higienedelsueo.Servicios;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.barajasoft.higienedelsueo.BroadcastReceivers.Alarm;
import com.barajasoft.higienedelsueo.Datos.SensoresVal;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class NoiseService extends Service {
    AudioManager audioManager;
    int previousVolume;
    boolean firstRun = true;
    SensoresVal sensores_valores;
    MediaRecorder mediaRecorder;
    final float MAX_AMPLITUDE = 8000;
    Alarm alarm = Alarm.getInstance();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sensores_valores = SensoresVal.getInstance();
        audioManager = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 10, 0);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(alarm.isTimer_active()){
            alarm.setAlarm(this);
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setAudioSamplingRate(44100);
            mediaRecorder.setAudioEncodingBitRate(96000);
            String outputFile = Environment.getExternalStorageDirectory()+"/grabacion.3gp";
            mediaRecorder.setOutputFile(outputFile);
            ArrayList<Integer> amplitudes = new ArrayList<>();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Log.e("Inicio","La grabacion");
                mediaRecorder.getMaxAmplitude();
            } catch (Exception e1) {
                Log.e("Error",e1.getMessage());
                Log.e("Error","SE cancelo el servicio");
                alarm.cancelAlarm(this);
            }
            Thread timer = new Thread(new Runnable() {
                int time = 25000;
                @Override
                public void run() {
                    while(time > 0){
                        try {
                            sleep(100);
                            time -= 100;
                            amplitudes.add(mediaRecorder.getMaxAmplitude());
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                    int currentAmplitude = mediaRecorder.getMaxAmplitude();
                    mediaRecorder.stop();
                    mediaRecorder.reset();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,previousVolume,0);
                    for(Integer integer : amplitudes){
                        currentAmplitude += integer;
                    }
                    Log.e("Termino","La grabacion");
                    Log.e("Nivel de dB",String.valueOf(20*Math.log10(currentAmplitude/amplitudes.size())));
                    Log.e("Nivel de amplitud",String.valueOf(currentAmplitude/amplitudes.size()));
                    sensores_valores.setDb(20*Math.log10(currentAmplitude/amplitudes.size()));
                }
            });
            timer.start();
        }else{
            if(firstRun){
                alarm.setAlarm(this);
                firstRun = false;
                Log.e("Inicio"," El servicio de grabacion");
            }
        }
        return super.onStartCommand(intent, flags, startId);

    }
}

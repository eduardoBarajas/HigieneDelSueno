package com.barajasoft.higienedelsueo.BroadcastReceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.barajasoft.higienedelsueo.Servicios.IluminationService;
import com.barajasoft.higienedelsueo.Servicios.NoiseService;

import static java.lang.Thread.sleep;

public class Alarm extends BroadcastReceiver {
    boolean service_active = false;
    boolean timer_active = false;

    private static Alarm instance = null;

    public static Alarm getInstance(){
        if(instance==null)
            instance = new Alarm();
        return instance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "");
        wakeLock.acquire();
        // Put here YOUR code.
        Thread timer = new Thread(new Runnable() {
            int time = 1000*60;
            @Override
            public void run() {
                while(time > 0){
                    try {
                        sleep(100);
                        time -= 100;
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                Log.e("SE ACABO","EL TIMER DENTRO DE ONRECEIVE");
                context.startService(new Intent(context, NoiseService.class));
            }
        });
        timer.start();
        Toast.makeText(context, "Iniciando Grabacion Sonido!!!!!!!!!!", Toast.LENGTH_LONG).show(); // For example
        wakeLock.release();
    }

    public void setAlarm(Context context)
    {
        if(!service_active){
            AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            Intent i = new Intent(context, Alarm.class);
            PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*60, pi); // Millisec * Second * Minute
            service_active = true;
            timer_active = true;
        }
    }

    public void cancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
        timer_active = false;
    }

    public boolean isTimer_active() {
        return timer_active;
    }

    public void setTimer_active(boolean timer_active) {
        this.timer_active = timer_active;
    }
}

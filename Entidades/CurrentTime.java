package com.barajasoft.higienedelsueo.Entidades;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.barajasoft.higienedelsueo.BroadcastReceivers.Alarm;
import com.barajasoft.higienedelsueo.BroadcastReceivers.Time;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Observable;

import static android.content.Context.ALARM_SERVICE;

public class CurrentTime extends Observable {
    private String currentHour = "0";
    private String currentMinute = "0";
    private String currentDay = "0";

    private static CurrentTime instance = null;

    private Context context;

    public static CurrentTime getInstance(Context context){
        if(instance==null)
            instance = new CurrentTime(context);
        return instance;
    }

    private CurrentTime(Context context){
        this.context = context;
        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Time.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 123, i, 0);
        //am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*60*10, pi);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 50, pi);
    }

    public String getCurrentHour() {
        return currentHour;
    }

    public void setCurrentHour(String currentHour) {
        this.currentHour = currentHour;
    }

    public String getCurrentMinute() {
        return currentMinute;
    }

    public void setCurrentMinute(String currentMinute) {
        this.currentMinute = currentMinute;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay;
    }

    public void setCurrentTime(String hora, String minutos){
        this.currentMinute = minutos;
        this.currentHour = hora;
        setChanged();
        notifyObservers(new String[]{hora,minutos});
        Log.e("hora",hora);
        Log.e("minutos",minutos);
    }

    public void notifyObservers(){
        super.notifyObservers();
        Log.e("se notifico","SEGUUUUUN");
    }

    public void stop()
    {
        instance = null;
        Intent intent = new Intent(context, Time.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 123, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}

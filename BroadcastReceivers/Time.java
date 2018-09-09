package com.barajasoft.higienedelsueo.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.barajasoft.higienedelsueo.Entidades.CurrentTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class Time extends BroadcastReceiver {
    CurrentTime time = null;
    @Override
    public void onReceive(Context context, Intent intent) {
        if(time == null){
            time = CurrentTime.getInstance(context);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("kk:mm");
        format.setTimeZone(TimeZone.getTimeZone("GMT-7:00"));
        String[] fecha = format.format(calendar.getTime()).split(":");
        time.setCurrentTime(fecha[0],fecha[1]);
    }
}

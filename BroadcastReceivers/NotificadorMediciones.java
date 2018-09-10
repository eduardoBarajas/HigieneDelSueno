package com.barajasoft.higienedelsueo.BroadcastReceivers;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.barajasoft.higienedelsueo.Actividades.MainActivity;
import com.barajasoft.higienedelsueo.R;

public class NotificadorMediciones extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String tipo = "";
        if(intent.hasExtra("tipo")){
            tipo = intent.getStringExtra("tipo");
        }
        createNotification(tipo,context);
    }

    private void createNotification(String type,Context context){
        String summaryText = "Da click aqui para realizar la medicion";
        String bigTxt = "Tipo Medicion: "+type;
        String title = "Nueva Medicion";
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "notify_001");
        Intent ii = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 777, ii, 0);
        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText(bigTxt);
        bigText.setBigContentTitle(title);
        bigText.setSummaryText(summaryText);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.drawable.green_icon);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(bigTxt);
        mBuilder.setAutoCancel(true);
        mBuilder.setPriority(Notification.PRIORITY_MAX);
        mBuilder.setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
        mBuilder.setStyle(bigText);

        Vibrator vibrator = (Vibrator) context.getSystemService(context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(channel);
        }

        mNotificationManager.notify(0, mBuilder.build());
    }
}

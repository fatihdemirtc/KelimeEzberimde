package com.kelimeezberimde;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.util.Log;


public class AlarmReceiver extends BroadcastReceiver {
    Notification notification;
    NotificationManager notificationManager;
    @Override
    public void onReceive(Context context, Intent ıntent) {
        Intent notificationIntent = new Intent(context, ListWord.class);
        PendingIntent intent2 = PendingIntent.getActivity(context, 0, notificationIntent, 0);

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                        .setTicker("Kelime Ezberimde")
                        .setContentTitle("Kelime Ezberimde")
                        .setContentText("Ödevlerini Tamamla")
                        .setContentIntent(intent2);

        mBuilder.setDefaults(NotificationCompat.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());

        Log.i("MyTestService", "Test");

        int interval = 15; // minutes

        Intent receiverIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 123456789, receiverIntent, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) Log.i("MyTestService", "alarmManager es null");


        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+interval*60*1000, interval*60*1000, sender);
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+interval*60*1000, interval*60*1000, sender);

        Log.i("MyTestService", "End Test");
    }

}

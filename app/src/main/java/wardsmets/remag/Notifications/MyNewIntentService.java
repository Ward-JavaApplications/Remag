package wardsmets.remag.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import java.util.Date;

import wardsmets.remag.MainActivity;
import wardsmets.remag.R;

public class MyNewIntentService extends IntentService {
    private static final int NOTIFICATION_ID = 3;


    public MyNewIntentService() {
        super("MyNewIntentService");
    }



    @Override
    protected void onHandleIntent(Intent intent) {
        String notificationTitle = intent.getStringExtra("title");
        fireCurrentNotification(intent,notificationTitle);
        Date date = new Date(System.currentTimeMillis());
        new MyNotificationManager(getApplicationContext());
    }
    private void fireCurrentNotification(Intent intent,String notificationTitle){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.getNotificationChannelID())
                .setSmallIcon(R.drawable.logo_edited)
                .setContentTitle("Remag")
                .setContentText(notificationTitle)
                .setSound(null)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        Intent notifyIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 2, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //to be able to launch your activity from the notification
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);

    }
}
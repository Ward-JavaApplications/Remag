package wardsmets.remag.Notifications;

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.getNotificationChannelID())
                .setSmallIcon(R.drawable.logo_edited)
                .setContentTitle("Remag")
                .setContentText(notificationTitle)
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
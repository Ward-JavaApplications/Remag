package wardsmets.remag.tests;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import wardsmets.remag.R;

public class NotificationTester extends AppCompatActivity {
    NotificationTester(){
        createNotificationChannel();
        buildNotification();


    }

    private void buildNotification(){
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(TestActivity.context, "Test Notification")
                    .setSmallIcon(R.drawable.small_icon_add_bell_foreground)
                    .setContentTitle("Testing Bruuuuv")
                    .setContentText("Dit is wat ik allemaal te zeggen heb man")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(TestActivity.context);
            notificationManager.notify(1, builder.build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Test Notification", "Test Notification", importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}

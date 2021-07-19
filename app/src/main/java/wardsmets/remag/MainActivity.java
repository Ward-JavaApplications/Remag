package wardsmets.remag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import wardsmets.remag.tests.TestActivity;

public class MainActivity extends AppCompatActivity {
    private static String notificationChannelID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationChannelID = getString(R.string.notification_channel);
        notificationChannelID = "markelChannel";

        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                CharSequence name = "Main Notification Channel";
                int importance = NotificationManager.IMPORTANCE_DEFAULT;
                NotificationChannel channel = new NotificationChannel(notificationChannelID, name, importance);
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static String getNotificationChannelID() {
        return notificationChannelID;
    }
}
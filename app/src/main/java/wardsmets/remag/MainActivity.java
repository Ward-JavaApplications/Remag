package wardsmets.remag;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import wardsmets.remag.ReminderContainers.CustomDayReminderContainer;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.ReminderContainers.XDaysReminderContainer;
import wardsmets.remag.tests.TestActivity;

public class MainActivity extends AppCompatActivity {
    private static String notificationChannelID;
    public static PreferenceManager preferenceManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationChannelID = getString(R.string.notification_channel);
        notificationChannelID = "markelChannel";

        preferenceManager = new PreferenceManager(getApplicationContext());
        //efkes voor debuggin
        preferenceManager.updateReminders(new XDaysReminderContainer(new String[]{"12:56", "11:00", "08:00"}, "de hollanders",3));

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadNextActivity();
            }
        },1000);


    }
    private void loadNextActivity(){
        Intent intent = new Intent(this, MainMenuActivity.class);
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
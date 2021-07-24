package wardsmets.remag.tests;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import wardsmets.remag.MainActivity;
import wardsmets.remag.R;

public class TestActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.activity_notification_test);
    }

    private void buildNotification(){
        try {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, MainActivity.getNotificationChannelID())
                    .setSmallIcon(R.drawable.small_icon_add_bell_foreground)
                    .setContentTitle("textTitle")
                    .setContentText("textContent")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
            notificationManager.notify(1, builder.build());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    public void OnButtonClick(View view){
        try {
            //System.out.println("button clicked");
            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
            buildNotification();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

package wardsmets.remag.tests;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import wardsmets.remag.R;

public class TestActivity extends AppCompatActivity {
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getApplicationContext();

        setContentView(R.layout.activity_notification_test);
    }

    public void OnButtonClick(View view){
        try {
            System.out.println("Bruv is this even doing anything?");

            Toast.makeText(context, "Button clicked", Toast.LENGTH_SHORT).show();
            new NotificationTester();

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

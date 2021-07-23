package wardsmets.remag;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReminderSettingsActivity extends AppCompatActivity {
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_settings);
        name = getIntent().getStringExtra("name");
        try {
            TextView textView = findViewById(R.id.textView_reminderSettigns_reminderName);
            textView.setText(name.toUpperCase());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void returnToPreviousMenu(View view){
        finish();
    }
}

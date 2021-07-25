package wardsmets.remag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wardsmets.remag.Notifications.MyNotificationManager;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterReminders;
import wardsmets.remag.tests.TestActivity;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loadReminders();
        calculateNextReminder();
    }

    public void loadAddReminder(View view){
        Intent intent = new Intent(this,AddReminderActivity.class);
        startActivity(intent);
        finish();
    }

    public void reloadView(){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void calculateNextReminder(){
        try {
            new MyNotificationManager(getApplicationContext());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



    private void loadReminders(){
        try {
            ArrayList<ReminderContainer> arrayList = MainActivity.preferenceManager.getReminderContainerArrayList();
            ArrayList<String> names = new ArrayList<>(arrayList.size());
            for (ReminderContainer reminderContainer : arrayList) {
                names.add(reminderContainer.getReminderName());
            }
            String[] namesArray = names.toArray(new String[0]);

            //RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recycler_view_time_of_day);
            MyRecycleViewAdapterReminders adapter = new MyRecycleViewAdapterReminders(namesArray, getApplicationContext(),this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void settingsClicked(View view){
        Intent intent = new Intent(this, TestActivity.class);
        startActivity(intent);
    }


}

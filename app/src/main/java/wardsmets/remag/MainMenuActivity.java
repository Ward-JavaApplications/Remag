package wardsmets.remag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterAddReminder;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        loadReminders();
    }

    public void loadAddReminder(View view){
        Intent intent = new Intent(this,AddReminderActivity.class);
        startActivity(intent);
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
            MyRecycleViewAdapterAddReminder adapter = new MyRecycleViewAdapterAddReminder(namesArray, getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }


}

package wardsmets.remag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AddReminderActivity extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_reminder);

            //viewFlipper
            MyViewFlipper viewFlipper = new MyViewFlipper(findViewById(R.id.buttonViewFlipper));

            //spinner
            Spinner spinnerSelector = findViewById(R.id.spinner_frequency);
            spinnerSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    viewFlipper.setView(position);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //recycleView
            RecyclerView recyclerView = findViewById(R.id.recycler_view_time_of_day);
            String[] times = {"18:00", "20:00", "06:00", "markel", "j;lkaesf", "akls;fjd", ";afjlkds", "qweoriu"};
            MyRecycleViewAdapter adapter = new MyRecycleViewAdapter(times, getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void returnToMainMenu(View view){
//        Intent intent = new Intent(this,MainMenuActivity.class);
//        startActivity(intent);
        PreferenceManager p = new PreferenceManager(getApplicationContext());
        p.updateReminders(new ReminderContainer(new String[]{"jorik", "gunter"}, "hollanders"));
        p.printTest();
    }
    public void saveReminder(View view){
//        returnToMainMenu(view);
        PreferenceManager p = new PreferenceManager(getApplicationContext());
        p.updateReminders(new ReminderContainer(new String[]{"Warden","stien","bram"},"niffo's"));
        p.printTest();

    }
    public void addReminder(View view){
        new PreferenceManager(getApplicationContext());

    }

}

package wardsmets.remag;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterAddReminder;
import wardsmets.remag.Views.MyViewFlipper;

public class AddReminderActivity extends AppCompatActivity{

    private String reminderName = "niffo's";

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
            String[] times = getTimes();
            MyRecycleViewAdapterAddReminder adapter = new MyRecycleViewAdapterAddReminder(times, getApplicationContext());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private String[] getTimes(String reminderName){
        ArrayList<ReminderContainer> reminders =  MainActivity.preferenceManager.getReminderContainerArrayList();
        for(ReminderContainer container: reminders){
            if(container.getReminderName().equals(reminderName)) return container.getTimes();
        }
        Toast.makeText(getApplicationContext(),"no times set yet",Toast.LENGTH_SHORT).show();
        return new String[0];
    }
    private String[] getTimes(){
        return new String[0];
    }

    public void returnToMainMenu(View view){
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);

    }
    public void saveReminder(View view){

        returnToMainMenu(view);



    }
    public void addReminder(View view){


    }

}

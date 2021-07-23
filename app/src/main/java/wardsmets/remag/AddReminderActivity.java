package wardsmets.remag;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import wardsmets.remag.Exceptions.NotAValidHourException;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterAddReminder;
import wardsmets.remag.Views.MyViewFlipper;

public class AddReminderActivity extends AppCompatActivity{

    private String[] times;
    private TextView reminderNameView;
    private MyRecycleViewAdapterAddReminder adapter;
    private RecyclerView recyclerView;

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
            recyclerView = findViewById(R.id.recycler_view_time_of_day);
            times = getTimes();
            adapter = new MyRecycleViewAdapterAddReminder(times, getApplicationContext(),this);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

            //reminderName
            reminderNameView = (TextView)findViewById(R.id.editText_add_reminder_menu_reminderName);
            setTextViewName(reminderNameView);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this void is meant to be overwritten and set the textView to something appropriate like the name of the reminder
     */
    public void setTextViewName(TextView textView) {
    }

    public String[] getTimes(){
        return new String[0];
    }

    public void returnToMainMenu(View view){
        Intent intent = new Intent(this,MainMenuActivity.class);
        startActivity(intent);
    }

    public void addTime(View view){
        ArrayList<String> newTimes = new ArrayList<>(Arrays.asList(times));
        newTimes.add("00:00");
        times = newTimes.toArray(new String[0]);
        reloadTimes(times);
    }

    private String[] getTextViewsFromAdapter(){
        return adapter.getTextViews().toArray(new String[0]);
    }
    public void reloadTimes(String[] newTimes){
        adapter = new MyRecycleViewAdapterAddReminder(newTimes,getApplicationContext(),this );
        recyclerView.setAdapter(adapter);
    }

    public void saveReminder(View view){
        String[] timesFromAdapter = getTextViewsFromAdapter();
        if(checkIfTimesCorrect(timesFromAdapter)) {
            ReminderContainer container = new ReminderContainer(timesFromAdapter, reminderNameView.getText().toString());
            MainActivity.preferenceManager.updateReminders(container);
            returnToMainMenu(view);
        }
    }


    private boolean checkIfTimesCorrect(String[] timesFromAdapter){
            for (String time : timesFromAdapter) {
                try {
                    if(time.length()!=5) throw new NotAValidHourException();
                    int firstTwo = Integer.parseInt(time.substring(0, 2));
                    int lastTwo = Integer.parseInt(time.substring(3, 5));
                    if (firstTwo > 23 || lastTwo > 59 || firstTwo < 0 || lastTwo < 0) {
                        //too late
                        throw new NotAValidHourException();
                    }
                }
                catch (Exception e){
                    Toast.makeText(this, "Could not save a reminder because " + time + " was not a valid time of day.\nMake sure the format is \"hh:mm\"", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
            return true;
        }
}

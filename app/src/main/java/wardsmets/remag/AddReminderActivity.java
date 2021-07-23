package wardsmets.remag;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import wardsmets.remag.Exceptions.NotAValidHourException;
import wardsmets.remag.ReminderContainers.CustomDayReminderContainer;
import wardsmets.remag.ReminderContainers.ReminderContainer;
import wardsmets.remag.ReminderContainers.XDaysReminderContainer;
import wardsmets.remag.Views.MyRecycleViewAdapterAddReminder;
import wardsmets.remag.Views.MyViewFlipper;

public class AddReminderActivity extends AppCompatActivity{

    private String[] times;
    private TextView reminderNameView;
    private MyRecycleViewAdapterAddReminder adapter;
    private RecyclerView recyclerView;
    private Spinner spinnerSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_reminder);

            //viewFlipper
            MyViewFlipper viewFlipper = new MyViewFlipper(findViewById(R.id.buttonViewFlipper));

            //spinner
            spinnerSelector = findViewById(R.id.spinner_frequency);
            spinnerSelector.setSelection(setSpinnerPosition(),false);
            viewFlipper.setView(setSpinnerPosition());
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

            //set content of the viewFlipper
            setReminderContainerView(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this void is meant to be overwritten and set the textView to something appropriate like the name of the reminder
     */
    public void setTextViewName(TextView textView) {
    }

    /**
     * @return 0 if customdays, 1 if x days in between. Default is 0
     */
    public int setSpinnerPosition(){
        return 0;
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
        return textViewArrayListToStringArray(adapter.getTextViews());
    }
    private String[] textViewArrayListToStringArray(ArrayList<TextView> textViews){
        ArrayList<String> times = new ArrayList<>(textViews.size());
        for(TextView view:textViews){
            times.add(view.getText().toString());
        }
        return times.toArray(new String[0]);
    }
    public void reloadTimes(ArrayList<TextView> textViews){
        String[] times = textViewArrayListToStringArray(textViews);
        adapter = new MyRecycleViewAdapterAddReminder(times,getApplicationContext(),this );
        recyclerView.setAdapter(adapter);
    }
    public void reloadTimes(String[] times){
        adapter = new MyRecycleViewAdapterAddReminder(times,getApplicationContext(),this );
        recyclerView.setAdapter(adapter);
    }
    public void setReminderContainerView(AddReminderActivity parent){

    }

    public void saveReminder(View view){
        try {
            String[] timesFromAdapter = getTextViewsFromAdapter();
            System.out.println("Bruur dees krijgen we van de adapter terug");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Arrays.stream(timesFromAdapter).forEach(System.out::println);
            }
            if (checkIfTimesCorrect(timesFromAdapter)) {
                int positionSpinner = spinnerSelector.getSelectedItemPosition();
                ReminderContainer container;
                if (positionSpinner == 1) {
                    //custom days
                    EditText numberOfDays = getDaysText();
                    try {
                        container = new XDaysReminderContainer(timesFromAdapter, reminderNameView.getText().toString(), Integer.parseInt(numberOfDays.getText().toString()));
                    }
                    catch (NumberFormatException numberFormatException){
                        Toast.makeText(getApplicationContext(),"The given amount of days in between isn't valid.",Toast.LENGTH_SHORT).show();
                        return;
                    }
                } else {
                    //every x days
                    ToggleButton[] buttons = getToggleButtons();
                    ArrayList<String> activeButtons = new ArrayList<>();
                    for (ToggleButton button : buttons) {
                        if (button.isChecked()) {
                            activeButtons.add(button.getText().toString());
                        }
                    }
                    container = new CustomDayReminderContainer(timesFromAdapter, reminderNameView.getText().toString(), activeButtons.toArray(new String[0]));
                }
                MainActivity.preferenceManager.updateReminders(container);
                returnToMainMenu(view);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public ToggleButton[] getToggleButtons(){
        return new ToggleButton[]{findViewById(R.id.toggleButton_mon), findViewById(R.id.toggleButton_tue), findViewById(R.id.toggleButton_wed), findViewById(R.id.toggleButton_thu),
                findViewById(R.id.toggleButton_fri), findViewById(R.id.toggleButton_sat), findViewById(R.id.toggleButton_sun)};
    }

    public EditText getDaysText(){
        return findViewById(R.id.editTextTextPersonName);
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

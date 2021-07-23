package wardsmets.remag.ReminderContainers;

import android.util.Log;
import android.widget.ToggleButton;

import java.util.ArrayList;

import wardsmets.remag.AddReminderActivity;
import wardsmets.remag.ReminderContainers.ReminderContainer;

public class CustomDayReminderContainer extends ReminderContainer {
    private String[] days;
    public CustomDayReminderContainer(String reminderName,String[] days) {
        super(reminderName);
        this.days = days;
    }

    public CustomDayReminderContainer(String[] times, String reminderName,String[] days) {
        super(times, reminderName);
        this.days = days;
    }
    public CustomDayReminderContainer(String[] times,String reminderName,String daysPastedTogether) {
        super(times, reminderName);
        try {
            ArrayList<String> days = new ArrayList<>(daysPastedTogether.length() / 2);
            for (int i = 0; i < daysPastedTogether.length() ; i += 2) {
                days.add(daysPastedTogether.substring(i, i + 2));
                //Log.v("markel", daysPastedTogether.substring(i, i + 2));
            }
            this.days = days.toArray(new String[0]);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public String getDays() {
        StringBuilder daysB = new StringBuilder();
        for(String s: days){
            daysB.append(s);
        }
        return daysB.toString();
    }

    @Override
    public int getTypeOfContainer() {
        return 0;
    }

    @Override
    public void setReminderViewFlipperContent(AddReminderActivity parent) {
        ToggleButton[] buttons = parent.getToggleButtons();
        for(ToggleButton button: buttons){
            for(String day: days){
                if(button.getText().toString().equals(day)){
                    button.setChecked(true);
                }
            }
        }
    }
}

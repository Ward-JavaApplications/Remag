package wardsmets.remag.ReminderContainers;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

import wardsmets.remag.AddReminderActivity;

public class ReminderContainer {
    public String[] times;
    public String reminderName;
    //own sound


    public ReminderContainer(String reminderName) {
        this.reminderName = reminderName;
    }

    public ReminderContainer(String[] times, String reminderName) {
        this.times = times;
        this.reminderName = reminderName;
    }

    public void addTime(String time){
        ArrayList<String> s = new ArrayList<String>(Arrays.asList(times));
        s.add(time);
        times = s.toArray(new String[0]);

    }
    public int getTimesSize(){
        return times.length;
    }

    public String getReminderName() {
        return reminderName;
    }

    public String[] getTimes() {
        return times;
    }

    /**
     * @return 0 if superclass, 1 if XDaysReminders, 0 if CustomDayReminder
     */
    public int getTypeOfContainer(){
        return 0;
    }
    /**
     * @return 0 if custom days, return 1 if xdays in between
     */
    public int setSpinnerPosition(){
        return 0;
    }
    public void setReminderViewFlipperContent(AddReminderActivity parent){

    }
}

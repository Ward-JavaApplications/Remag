package wardsmets.remag;

import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;

public class ReminderContainer {
    private String[] times;
    private String reminderName;
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
}
